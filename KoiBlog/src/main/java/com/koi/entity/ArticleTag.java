package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 标签明细表
 */
@Data
public class ArticleTag implements Serializable {
    /**
     * 文章ID
     */
    private Integer a_id;
    /**
     *标签ID
     */
    private Integer t_id;
}
