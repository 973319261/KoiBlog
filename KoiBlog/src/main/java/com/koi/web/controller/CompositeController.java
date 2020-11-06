package com.koi.web.controller;

import cn.hutool.core.date.DateUtil;
import com.koi.entity.Article;
import com.koi.service.ArticleService;
import com.koi.service.CategoryService;
import com.koi.service.TagService;
import com.koi.service.UserService;
import org.omg.CORBA.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合控制器
 */
@RestController()
@RequestMapping("/composite")
public class CompositeController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/getSiteProfiles",method = RequestMethod.GET)
    public Map<String, Object> getSiteProfiles(){
        //文章总量
        int articleCount=articleService.findArticleCountByState(0,0,"");
        //留言总量
        int commentCount=100;
        //分类总量
        int categoryCount=categoryService.findCategoryCountByName("");
        //标签总量
        int tagCount=tagService.findTagAll().size();
        //链接总量
        int linkCount=19;
        //浏览总量
        int browseCount=82614;
        //最后更新时间
        List<Article> articleList = articleService.findArticleByState(0,"",0,1,1);
        Date date = articleList.get(0).getArticle_publishDate();
        String lastUpdateDate= DateUtil.formatDate(date);
        //返回值
        Map<String, Object> map = new HashMap<>();
        map.put("articleCount", articleCount);
        map.put("commentCount", commentCount);
        map.put("categoryCount", categoryCount);
        map.put("tagCount", tagCount);
        map.put("linkCount", linkCount);
        map.put("browseCount", browseCount);
        map.put("lastUpdateDate", lastUpdateDate);
        return map;
    }
}
