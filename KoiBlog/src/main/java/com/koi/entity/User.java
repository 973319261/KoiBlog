package com.koi.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户 表
 */
@Data
public class User implements Serializable {
    /**
     * 用户Id
     */
    @TableId(type = IdType.AUTO)
    private Integer user_id;
    /**
     * 用户账号
     */
    private String user_name;
    /**
     * 用户密码
     */
    private String user_pass;
    /**
     * 用户昵称
     */
    private String user_nickname;
    /**
     * 用户性别 0表示男 1表示女
     */
    private Integer user_sex;
    /**
     * 用户邮箱
     */
    private String user_email;
    /**
     * 用户头像
     */
    private String user_avatar;
    /**
     * 注册时间
     */
    private Date user_registerTime;
    /**
     * 最后登录时间
     */
    private Date user_lastTime;
    /**
     * 最后登录Ip
     */
    private String user_lastIp;
    /**
     * 用户状态
     */
    private Integer user_status;
    /**
     * 用户备注
     */
    private String user_comment;
    /**
     * 盐
     */
    private String user_salt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUser_registerTime() {
        return user_registerTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUser_lastTime() {
        return user_lastTime;
    }
}
