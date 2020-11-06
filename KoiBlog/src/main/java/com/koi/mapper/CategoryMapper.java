package com.koi.mapper;

import com.koi.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
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
    int insertCategory(Category category) throws Exception;

    /**
     * 修改文章分类
     * @param category
     * @return
     * @throws Exception
     */
    int updateCategory(Category category) throws Exception;

    /**
     * 通过分类ID查询是否有关联文章
     * @param category_id
     * @return
     */
    int findArticleByCategoryId(Integer category_id);
    /**
     * 通过ID删除文章
     * @param category_id
     * @return
     * @throws Exception
     */
    int deleteCategory(Integer category_id)throws Exception;

    /**
     * 通过分类名称查询是否存在该分类
     * @param category_name
     * @return
     */
    int findCategoryByName(String category_name);

    /**
     * 查询文章分类和该文章分类下的文章列表
     * @return
     */
    List<Category> findArticleToCategory();
}
