package com.lmxdawn.him.api.ss;

import com.lmxdawn.him.common.protobuf.WSBaseReqProtoOuterClass;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @BelongsProject: him-netty-master
 * @BelongsPackage: com.lmxdawn.him.api.ss
 * @Author: yefei
 * @CreateTime: 2021-10-21 17:41
 * @Description: socket服务器初始化
 */
public class SocketServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        //15 秒没有向客户端发送消息就发生心跳
        ch.pipeline().addLast(new IdleStateHandler(15, 0, 0))
                // google Protobuf 编解码
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(WSBaseReqProtoOuterClass.WSBaseReqProto.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new SocketServerHandler());
    }
}
