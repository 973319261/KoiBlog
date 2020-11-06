package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限表
 */
@Data
public class Permission implements Serializable {
    /**
     * 权限ID
     */
    private Integer permission_id;
    /**
     * 权限名称
     */
    private String permission_name;
    /**
     * 权限地址
     */
    private String permission_url;
}
