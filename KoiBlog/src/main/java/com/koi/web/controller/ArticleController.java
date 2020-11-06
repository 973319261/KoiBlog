package com.koi.web.controller;

import cn.hutool.core.io.FileUtil;
import com.koi.entity.Article;
import com.koi.entity.Tag;
import com.koi.exception.CustomException;
import com.koi.service.ArticleService;
import com.koi.utils.CustomUtils;
import com.koi.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文章控制器
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    public final SimpleDateFormat fs = new SimpleDateFormat("yyyyMMdd");
    public final String rootPath = "E:/KoiBlogUpload";

    /**
     * 保存文章
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/saveArticle")
    public ResponseInfo saveArticle(Article article) throws Exception {
        int i = 0;
        try {
            i = articleService.saveArticle(article);
        } catch (Exception e) {
            throw new CustomException(article.getArticle_state() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
        if (i > 0) {
            return ResponseInfo.setInfo("success", article.getArticle_id() + "");
        } else {
            return ResponseInfo.setInfo("error", article.getArticle_state() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
    }

    /**
     * 上传图片
     *
     * @param request
     * @param image
     * @return 返回值为图片的地址
     */
    @RequestMapping(value = "/uploadImg")
    public ResponseInfo uploadImg(HttpServletRequest request, MultipartFile image) {
        String filePath = "/blogImg/" + fs.format(new Date());
        return CustomUtils.uploadFile(request, image, filePath);
    }

    /**
     * 通过关键字或者分类ID查询博客文章
     *
     * @param state
     * @param page
     * @param count
     * @param keywords
     * @param category_id
     * @return
     */
    @RequestMapping(value = "/allBlog", method = RequestMethod.GET)
    public Map<String, Object> getArticleByState(@RequestParam(value = "state") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "count", defaultValue = "10") Integer count, @RequestParam(required = false) String keywords,
                                                 @RequestParam(required = false) Integer category_id) {
        int totalCount = articleService.findArticleCountByState(state, category_id, keywords);
        int start = (page - 1) * count;
        List<Article> articles = articleService.findArticleByState(state, keywords, category_id, start, count);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("articles", articles);
        return map;
    }

    /**
     * 通过文章ID查询文章信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getArticleById", method = RequestMethod.GET)
    public Article getArticleById(Integer id) {
        return articleService.findArticleById(id);
    }

    /**
     * 获取上一篇文章
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPreArticle", method = RequestMethod.GET)
    public Article getPreArticle(Integer id) {
        return articleService.findPreArticle(id);
    }

    /**
     * 获取下一篇文章
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getNextArticle", method = RequestMethod.GET)
    public Article getNextArticle(Integer id) {
        return articleService.findNextArticle(id);
    }

    /**
     * 获取相关文章
     *
     * @param tags
     * @param article_id
     * @return
     */
    @RequestMapping(value = "/getRelationArticle", method = RequestMethod.GET)
    public List<Article> getRelationArticle(String[] tags, Integer article_id) {
        return articleService.findRelationArticle(tags, article_id);
    }

    /**
     * 通过标签获取相关文章
     *
     * @param tag_id
     * @return
     */
    @RequestMapping(value = "/getArticleByTagId", method = RequestMethod.GET)
    public Map<String, Object> getArticleByTagId(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "count", defaultValue = "10") Integer count, Integer tag_id) {
        int totalCount = articleService.findArticleCountByTagId(tag_id);
        int start = (page - 1) * count;
        List<Article> articles = articleService.findArticleByTagId(start,count,tag_id);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("articles", articles);
        return map;
    }

    /**
     * 获取热门文章
     * @return
     */
    @RequestMapping(value = "/getHotArticles",method = RequestMethod.GET)
    public List<Article> getHotArticles(){
        return articleService.findHotArticles();
    }
    /**
     * 删除或者回收箱
     *
     * @param aids
     * @param state
     * @return
     */
    @RequestMapping(value = "/dustbinOrDelete", method = RequestMethod.PUT)
    public ResponseInfo dustbinOrDelete(Integer[] aids, int state) {
        //state==2 永久删除 否则放到回收箱
        int i = articleService.dustbinOrDelete(aids, state);
        if (i > 0) {
            return ResponseInfo.setInfo("success", "成功删除" + i + "条数据");
        } else {
            return ResponseInfo.setInfo("error", "删除失败");
        }
    }

    /**
     * 恢复
     *
     * @param aids
     * @return
     */
    @RequestMapping(value = "/renewArticle", method = RequestMethod.PUT)
    public ResponseInfo renewArticle(Integer[] aids) {
        int i = articleService.renewArticle(aids);
        if (i > 0) {
            return ResponseInfo.setInfo("success", "成功恢复" + i + "条数据");
        } else {
            return ResponseInfo.setInfo("error", "恢复失败");
        }
    }

    /**
     * 点赞文章
     * @param article_id
     * @return
     */
    @RequestMapping(value = "/updateLikeNumber",method = RequestMethod.POST)
    public ResponseInfo updateLikeNumber(Integer article_id)throws Exception{
        return articleService.updateLikeNumber(article_id);
    }

    /**
     * 文章访问量
     * @param article_id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updatePageView",method = RequestMethod.POST)
    public ResponseInfo updatePageView(Integer article_id)throws Exception{
        return articleService.updatePageView(article_id);
    }
}
