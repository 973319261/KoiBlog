package com.koi.mapper;

import com.koi.entity.Article;
import com.koi.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    /**
     * 新增文章
     * @param article
     * @return
     */
    int insertArticle(Article article) throws Exception;

    /**
     * 修改文章
     * @param article
     * @return
     */
    int updateArticle(Article article) throws Exception;

    /**
     * 修改访问量
     * @param article_id
     * @return
     */
    int updatePageView(Integer article_id);

    /**
     * 修改点赞数
     * @param article_id
     * @return
     */
    int updateLikeNumber(Integer article_id) throws Exception;

    /**
     * 通过文章ID查询文章信息
     * @param article_id
     * @return
     */
    Article findArticleById(Integer article_id);

    /**
     * 获取上一篇文章
     * @param article_id
     * @return
     */
    Article findPreArticle(Integer article_id);

    /**
     * 获取下一篇文章
     * @param article_id
     * @return
     */
    Article findNextArticle(Integer article_id);

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
    List<Article> findRelationArticle(@Param("tags") String[] tags,Integer article_id);

    /**
     * 通过标签ID获取文章
     * @param tag_id
     * @return
     */
    List<Article> findArticleByTagId(@Param("start") Integer start, @Param("count") Integer count,@Param("tag_id") Integer tag_id);

    /**
     * 通过标签ID获取文章总数
     * @param tag_id
     * @return
     */
    int findArticleCountByTagId(Integer tag_id);
    /**
     * 通过文章状态和关键字分页查询文章
     * @param article_state
     * @param start
     * @param keywords
     * @return
     */
    List<Article> findArticleByState(@Param("article_state") Integer article_state,@Param("category_id")Integer category_id, @Param("keywords") String keywords, @Param("start") Integer start, @Param("count") Integer count);

    /**
     * 通过文章状态和关键字分页查询文章总数
     * @param article_state
     * @param keywords
     * @return
     */
    int findArticleCountByState(@Param("article_state") Integer article_state,@Param("category_id")Integer category_id, @Param("keywords") String keywords);

    /**
     * 通过文章ID修改文章状态
     * @param aids
     * @return
     */
    int updateStateById(@Param("aids") Integer[] aids,@Param("state") int state);

    /**
     * 通过文章ID删除文章（数据库永久删除）
     * @param aids
     * @return
     */
    int deleteArticleById(Integer[] aids);
}
