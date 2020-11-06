package com.koi.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章表
 */
@Data
public class Article implements Serializable {
    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Integer article_id;
    /**
     * 文章分类id
     */
    private Integer category_id;
    /**
     * 用户id
     */
    private Integer user_id;
    /**
     * 文章标题
     */
    private String article_title;
    /**
     * 富文本源码
     */
    private String article_mdContent;
    /**
     * html源码
     */
    private String article_htmlContent;
    /**
     * 摘要内容
     */
    private String article_summary;
    /**
     * 发表时间
     */
    private Date article_publishDate;
    /**
     * 更新时间
     */
    private Date article_editTime;
    /**
     * 0表示已发表，1表示草稿箱，2表示已删除
     */
    private Integer article_state;
    /**
     * 访问量
     */
    private Long article_pageView;
    /**
     * 点赞数
     */
    private Long article_likeNumber;
    /**
     * 是否开启评论 0表示是 1表示否
     */
    private Integer article_isComment;
    /**
     * 文章类型:0表示原创，1表示转载，2表示翻译
     */
    private Integer article_type;
    /**
     * 标签数组
     */
    private List<String> dynamicTags;

    /**
     * 博客分类
     */
    private Category category;
    /**
     *用户类型
     */
    private User user;
    /**
     * 标签
     */
    private List<Tag> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getArticle_publishDate() {
        return article_publishDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getArticle_editTime() {
        return article_editTime;
    }
}
