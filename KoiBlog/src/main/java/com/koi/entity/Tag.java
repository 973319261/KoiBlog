package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *标签表
 */
@Data
public class Tag implements Serializable {
    /**
     * 标签ID
     */
    private Integer tag_id;
    /**
     * 标签名称
     */
    private String tag_name;
}
