package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色明细表
 */
@Data
public class UserRole implements Serializable {
    /**
     * 用户ID
     */
    private Long u_id;
    /**
     * 角色ID
     */
    private Long r_id;
}
