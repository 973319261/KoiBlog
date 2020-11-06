package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论表
 */
@Data
public class Comment implements Serializable {
    /**
     * 批量ID
     */
    private Integer comment_id;
}
