package com.koi.service;


import com.koi.entity.Article;
import com.koi.entity.Tag;
import com.koi.vo.ResponseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章表Article业务逻辑接口
 */
public interface ArticleService {
    /**
     * @param article
     * @return
     */
    int saveArticle(Article article) throws Exception;

    /**
     * 通过文章ID查询文章信息
     * @param id
     * @return
     */
    Article findArticleById(Integer id);

    /**
     *
     * 获取上一篇文章
     * @param id
     * @return
     */
    Article findPreArticle(Integer id);

    /**
     * 获取下一篇文章
     * @param id
     * @return
     */
    Article findNextArticle(Integer id);

    /**
     * 获取热门文章
     * @return
     */
    List<Article> findHotArticles();
    /**
     * 获取相关文章
     * @param tags
     * @param article_id
     * @return
     */
    List<Article> findRelationArticle(String[] tags,Integer article_id);

    /**
     * 通过标签ID获取文章
     * @param start
     * @param count
     * @param tag_id
     * @return
     */
    List<Article> findArticleByTagId(Integer start,  Integer count,Integer tag_id);
    /**
     * 通过标签ID获取文章总数
     * @param tag_id
     * @return
     */
    int findArticleCountByTagId(Integer tag_id);
    /**
     * 通过文章状态和关键字分页查询文章
     *
     * @param article_state
     * @param start
     * @param keywords
     * @return
     */
    List<Article> findArticleByState(Integer article_state, String keywords, Integer category_id,Integer start, Integer count);
    /**
     * 通过文章状态和关键字分页查询文章总数
     * @param article_state
     * @param keywords
     * @return
     */

    int findArticleCountByState(Integer article_state,Integer category_id,String keywords);

    /**
     * 回收或者永久删除文章
     * @param aids
     * @param state
     * @return
     */
    int dustbinOrDelete(Integer[] aids,int state);

    /**
     * 恢复文章
     * @param aids
     * @return
     */
    int renewArticle(Integer[] aids);

    /**
     * 点赞文章
     * @param article_id
     * @return
     */
    ResponseInfo updateLikeNumber(Integer article_id) throws Exception;

    /**
     * 修改访问量
     * @param article_id
     * @return
     */
    ResponseInfo updatePageView(Integer article_id) throws Exception;
}
