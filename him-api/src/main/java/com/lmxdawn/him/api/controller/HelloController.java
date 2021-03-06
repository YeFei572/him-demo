package com.lmxdawn.him.api.controller;

import com.lmxdawn.him.api.ws.WSServer;
import com.lmxdawn.him.api.utils.WSBaseReqUtils;
import com.lmxdawn.him.api.vo.req.WSBaseReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private WSServer wsServer;

    @GetMapping("/hello")
    public String hello(@RequestParam("uid") Long uid,
                        @RequestParam(value = "msg", defaultValue = "嘿嘿测试", required = false) String msgContent
    ) {

        Integer type = 1;
        Long id = uid;
        Integer msgType = 0;
        Long sUid = 1L;
        String name = "测试";
        String avatar = "https://thirdwx.qlogo.cn/mmopen/vi_32/oT36o8Gib0RfujkyQc6ftibDiabsK4k6t26ryeAIYib5veeKhQRBAbFkicpw5AmUx05PP2tvYsicz6JfBJUUMTicvnMPg/132";
        String remark = "说明";

        WSBaseReqVO wsBaseReqVO = WSBaseReqUtils.create(type, id, msgType, msgContent, sUid, name, avatar, remark);

        Boolean aBoolean = wsServer.sendMsg(uid, wsBaseReqVO);

        return aBoolean ? "success" : "客户端不在线";
    }

}
