package com.lmxdawn.him.api.vo.res;

import lombok.Data;

/**
 * 用户登录后返回的
 */
@Data
public class UserLoginResVO {
    
    private Long uid;
    
    private String sid;
    /**
     * 昵称
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    
}
