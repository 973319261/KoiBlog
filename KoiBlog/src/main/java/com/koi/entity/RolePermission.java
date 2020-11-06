package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限明细表
 */
@Data
public class RolePermission implements Serializable {
    /**
     * 角色ID
     */
    private Integer r_id;
    /**
     * 权限ID
     */
    private Integer p_id;
}
