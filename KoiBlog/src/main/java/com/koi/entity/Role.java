package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 */
@Data
public class Role implements Serializable {
    /**
     * 角色ID
     */
    private Integer role_id;
    /**
     * 角色名称
     */
    private String role_name;
    /**
     * 角色描述
     */
    private String role_description;
}
