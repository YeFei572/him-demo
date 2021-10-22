package com.lmxdawn.him.api.ss;

import com.lmxdawn.him.api.constant.WSReqTypeConstant;
import com.lmxdawn.him.api.ws.WSServerHandler;
import com.lmxdawn.him.common.protobuf.WSBaseReqProtoOuterClass;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @BelongsProject: him-netty-master
 * @BelongsPackage: com.lmxdawn.him.api.ss
 * @Author: yefei
 * @CreateTime: 2021-10-21 17:45
 * @Description: socket server 的处理器
 */
@Slf4j
@ChannelHandler.Sharable
public class SocketServerHandler extends SimpleChannelInboundHandler<WSBaseReqProtoOuterClass.WSBaseReqProto> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WSBaseReqProtoOuterClass.WSBaseReqProto msg) throws Exception {
        String sid = msg.getSid();
        long uid = msg.getUid();
        int type = msg.getType();
        switch (type) {
            case WSReqTypeConstant.LOGIN: // 登录类型
                log.info("用户登录");
                WSServerHandler.userLogin(ctx, uid, sid);
                break;
            case WSReqTypeConstant.PING: // 心跳
                log.info("客户端心跳");
                break;
            default:
                log.info("未知类型");
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 可能出现业务判断离线后再次触发 channelInactive
        log.warn("触发 channelInactive 掉线![{}]", ctx.channel().id());
        WSServerHandler.userOffLine(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 读空闲
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                // 关闭用户的连接
                WSServerHandler.userOffLine(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if ("Connection reset by peer".equals(cause.getMessage())) {
            log.error("连接出现问题");
            return;
        }

        log.error(cause.getMessage(), cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道被激活！");
        super.channelActive(ctx);
    }
}
