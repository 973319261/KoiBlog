package com.koi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.koi.entity.Article;
import com.koi.exception.CustomException;
import com.koi.mapper.ArticleMapper;
import com.koi.mapper.TagMapper;
import com.koi.service.ArticleService;
import com.koi.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 文章表Article业务逻辑实现类
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    /**
     * 保存文章
     * @param article
     * @return
     */
    @Override
    public int saveArticle(Article article) throws Exception{
        int j=0;
        //处理文章摘要
        if (article.getArticle_summary() == null || "".equals(article.getArticle_summary())) {
            //直接截取
            String stripHtml = this.stripHtml(article.getArticle_htmlContent());
            article.setArticle_summary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if(article.getArticle_state()==0){//对要发表的文章更新操作
            article.setArticle_publishDate(new Date());//发表日期
        }else {
            article.setArticle_publishDate(DateUtil.parse("0000-00-00"));
        }
        article.setArticle_editTime(new Date());//修改时间
        if (article.getArticle_id() < 0) { //新增文章
            article.setUser_id(1);//当前用户
            j=articleMapper.insertArticle(article);//新增数据
        } else {//修改文章(草稿或者已发表的文章)
            j=articleMapper.updateArticle(article);//修改数据
        }
        List<String> dynamicTags = article.getDynamicTags();//获取页面的标签
        if (dynamicTags != null && dynamicTags.size() > 0) {
            j = this.saveArticleTag(article.getArticle_id(), dynamicTags);//保存标签
        }
        return j;
    }
    /**
     * 保存标签明细表
     *
     * @param article_id
     * @param dynamicTags
     * @return
     */
    private int saveArticleTag(Integer article_id, List<String> dynamicTags) throws Exception{
      try {
          //1.删除该文章目前所有的标签
          tagMapper.deleteTagByArticleId(article_id);
          //2.查询原来数据库的标签和新传来的标签对比，没有则增加数据库
          List<String> oldTagsNames = tagMapper.findTagsName(dynamicTags);
          //取数据库没有的标签来新增s
          dynamicTags.removeAll(oldTagsNames);
          if (dynamicTags != null && dynamicTags.size() > 0) {
              tagMapper.insertTag(dynamicTags);
          }
          //3.查询这些标签(该文章)的id
          dynamicTags.addAll(oldTagsNames);
          List<Integer> t_ids = tagMapper.findTagsIdByTagName(dynamicTags);
          //4.为该文章添加标签
          int i = tagMapper.insertArticleAndTag(article_id, t_ids);
          return i;
      }catch (Exception e){
          throw new CustomException("保存标签失败");
      }
    }

    /**
     * 截取摘要文本
     * @param content
     * @return
     */
    private String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    /**
     * 通过文章ID查询文章信息
     * @param id
     * @return
     */
    public Article findArticleById(Integer id){
        return articleMapper.findArticleById(id);
    }

    /**
     * 获取上一篇文章
     * @param id
     * @return
     */
    public Article findNextArticle(Integer id){
        return articleMapper.findNextArticle(id);
    }

    /**
     * 获取下一篇文章
     * @param id
     * @return
     */
    public Article findPreArticle(Integer id){
        return articleMapper.findPreArticle(id);
    }

    /**
     * 获取热门文章
     * @return
     */
    public List findHotArticles(){
        return articleMapper.findHotArticles();
    }
    /**
     * 获取相关文章
     * @param tags
     * @param article_id
     * @return
     */
    public List<Article> findRelationArticle(String[] tags,Integer article_id){
        return articleMapper.findRelationArticle(tags,article_id);
    }

    /**
     * 通过标签ID获取文章
     * @param start
     * @param count
     * @param tag_id
     * @return
     */
    public List<Article> findArticleByTagId(Integer start,  Integer count,Integer tag_id){
        return articleMapper.findArticleByTagId(start,count,tag_id);
    }
    /**
     * 通过标签ID获取文章总数
     * @param tag_id
     * @return
     */
    public int findArticleCountByTagId(Integer tag_id){
        return articleMapper.findArticleCountByTagId(tag_id);
    }
    /**
     * 通过文章状态和关键字分页查询文章
     * @param article_state
     * @param start
     * @param keywords
     * @return
     */
    @Override
    public List<Article> findArticleByState(Integer article_state, String keywords, Integer category_id,Integer start, Integer count){
        return articleMapper.findArticleByState(article_state,category_id,keywords,start,count);
    }
    /**
     * 通过文章状态和关键字分页查询文章总数
     * @param article_state
     * @param keywords
     * @return
     */
    public int findArticleCountByState(Integer article_state,Integer category_id,String keywords){
        return articleMapper.findArticleCountByState(article_state,category_id,keywords);
    }
    /**
     * 回收或者永久删除文章
     * @param aids
     * @param state
     * @return
     */
    public int dustbinOrDelete(Integer[] aids,int state){
        //state==2 永久删除 否则放到回收箱
        int i=0;
        if (state!=2){
            i=articleMapper.updateStateById(aids,2);
        }else {
            i=articleMapper.deleteArticleById(aids);
        }
        return i;
    }

    /**
     * 恢复文章
     * @param aids
     * @return
     */
    public int renewArticle(Integer[] aids){
        return articleMapper.updateStateById(aids,0);
    }

    /**
     * 点赞文章
     * @param article_id
     * @return
     */
    public ResponseInfo updateLikeNumber(Integer article_id) throws Exception{
        try{
           int i = articleMapper.updateLikeNumber(article_id);
           if(i>0){
               return ResponseInfo.setInfo("success","点赞成功");
           }else {
               return ResponseInfo.setInfo("error","点赞失败");
           }
        }catch (Exception e){
            throw new CustomException("点赞失败");
        }
    }

    /**
     * 修改访问量
     * @param article_id
     * @return
     * @throws Exception
     */
    public ResponseInfo updatePageView(Integer article_id) throws Exception{
        try{
            int i = articleMapper.updatePageView(article_id);
            if(i>0){
                return ResponseInfo.setInfo("success","访问量增加成功");
            }else {
                return ResponseInfo.setInfo("error","访问量增加失败");
            }
        }catch (Exception e){
            throw new CustomException("访问量增加失败");
        }
    }
}
