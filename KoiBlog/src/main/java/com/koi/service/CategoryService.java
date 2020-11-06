package com.koi.service;

import com.koi.entity.Category;
import com.koi.vo.ComboBox;
import com.koi.vo.ResponseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有博客分类
     * @return
     */
    List<Category> findCategoryAll();
    /**
     * 通过分类名称查询总数
     * @param keywords
     * @return
     */
    int findCategoryCountByName(String keywords);

    /**
     * 通过分类名称分页查询
     * @param keywords
     * @param start
     * @param count
     * @return
     */
    List<Category> findCategoryPageByName(@Param("keywords") String keywords, @Param("start") Integer start, @Param("count") Integer count);
    /**
     * 新增文章分类
     * @param category
     * @return
     */
    ResponseInfo insertCategory(Category category) throws Exception;
    /**
     * 修改文章分类
     * @param category
     * @return
     * @throws Exception
     */
    ResponseInfo updateCategory(Category category) throws Exception;
    /**
     * 通过ID删除文章
     * @param category_id
     * @return
     * @throws Exception
     */
    ResponseInfo deleteCategory(Integer category_id)throws Exception;

    /**
     * 查询文章分类和该文章分类下的文章列表
     * @return
     */
    List<Category> findArticleToCategory();
}
