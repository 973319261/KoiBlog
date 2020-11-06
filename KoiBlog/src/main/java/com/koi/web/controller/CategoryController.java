package com.koi.web.controller;
import com.koi.entity.Category;
import com.koi.service.CategoryService;
import com.koi.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.util.resources.cldr.es.CalendarData_es_GT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *博客分类控制器
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 查询所有博客分类
     * @return
     */
    @RequestMapping(value = "/findCategoryAll")
    public List<Category> findCategoryAll(){
        return categoryService.findCategoryAll();
    }

    /**
     * 通过分类名称模糊查询
     * @param keywords
     * @return
     */
    @RequestMapping("/findCategoryPageByName")
    public Map<String, Object> findCategoryPageByName(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "10") Integer count, String keywords){
        int totalCount = categoryService.findCategoryCountByName(keywords);
        int start = (page - 1) * count;
        List<Category> category = categoryService.findCategoryPageByName(keywords, start, count);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("category", category);
        return map;
    }
    /**
     * 新增文章分类
     * @param category
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertCategory")
    public ResponseInfo insertCategory(Category category) throws Exception{
        return categoryService.insertCategory(category);
    }

    /**
     * 修改文章分类
     * @param category
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateCategory")
    public ResponseInfo updateCategory(Category category) throws Exception{
        return categoryService.updateCategory(category);
    }

    /**
     * 删除文章分类
     * @param category_id
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteCategory")
    public ResponseInfo deleteCategory(Integer category_id) throws Exception{
        return categoryService.deleteCategory(category_id);
    }

    /**
     * 查询文章分类和该文章分类下的文章列表
     * @return
     */
    @RequestMapping("/findArticleToCategory")
    public List<Category> findArticleToCategory(){
        return categoryService.findArticleToCategory();
    }
}
