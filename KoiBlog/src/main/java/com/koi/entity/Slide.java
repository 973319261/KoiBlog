package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 幻灯片表
 */
@Data
public class Slide implements Serializable {
    /**
     * 主键ID
     */
    private Integer slide_id;
    /**
     * 标题
     */
    private String slide_title;
    /**
     * 链接
     */
    private String slide_url;
    /**
     * 图片
     */
    private String slide_picture;
    /**
     * 排序
     */
    private Integer slide_sort;
    /**
     * 打开方式
     */
    private String slide_target;
    /**
     * 类型 0位首页
     */
    private Integer slide_type;
}
