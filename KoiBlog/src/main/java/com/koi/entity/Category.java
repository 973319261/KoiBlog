package com.koi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章分类表
 */
@Data
public class Category implements Serializable {
    /**
     * 文章分类ID
     */
    private Integer category_id;
    /**
     * 文章分类名称
     */
    private String category_name;
    /**
     * 创建时间
     */
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCreateDate(){
        return createDate;
    }

    /**
     * 文章类别
     */
    private List<Article> article;
}
