package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 友情链接表
 */
@Data
public class Link implements Serializable {
    /**
     * 链接ID
     */
    private Integer link_id;
    /**
     * 链接名称
     */
    private String link_name;
    /**
     * 链接路径
     */
    private String link_url;
    /**
     * 头像
     */
    private String link_avatar;
    /**
     * 描述
     */
    private String link_describe;
}
