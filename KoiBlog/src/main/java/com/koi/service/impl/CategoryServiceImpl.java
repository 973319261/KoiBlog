package com.koi.service.impl;

import com.koi.entity.Category;
import com.koi.exception.CustomException;
import com.koi.mapper.CategoryMapper;
import com.koi.service.CategoryService;
import com.koi.vo.ComboBox;
import com.koi.vo.ResponseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有博客分类
     * @return
     */
    public List<Category> findCategoryAll() {
        List<Category> categories = categoryMapper.findCategoryAll();
        return categories;
    }

    /**
     * 通过分类名称查询总数
     * @param keywords
     * @return
     */
     public int findCategoryCountByName(String keywords){
        return categoryMapper.findCategoryCountByName(keywords);
    }

    /**
     * 通过分类名称分页查询
     * @param keywords
     * @param start
     * @param count
     * @return
     */
    public List<Category> findCategoryPageByName(String keywords,Integer start,Integer count){
        return categoryMapper.findCategoryPageByName(keywords,start,count);
    }

    /**
     * 新增文章分类
     * @param category
     * @return
     * @throws Exception
     */
    public ResponseInfo insertCategory(Category category) throws Exception{
        try{
            //判断是否存在该分类
            int i= categoryMapper.findCategoryByName(category.getCategory_name());
            if(i>0){
               return ResponseInfo.setInfo("warning","该分类已存在");
            }else {//不存在新增分类
                category.setCreateDate(new Date());
                categoryMapper.insertCategory(category);
            }
        }catch (Exception e){
            throw new CustomException("新增失败");
        }
        return ResponseInfo.setInfo("success","新增成功");
    }

    /**
     * 修改文章分类
     * @param category
     * @return
     * @throws Exception
     */
    @Override
    public ResponseInfo updateCategory(Category category) throws Exception {
        try{
            //判断是否存在该分类
            int i= categoryMapper.findCategoryByName(category.getCategory_name());
            if(i>0){
                return ResponseInfo.setInfo("warning","该分类已存在");
            }else {
                category.setCreateDate(new Date());
                categoryMapper.updateCategory(category);
            }
        }catch (Exception e){
            throw new CustomException("修改失败");
        }
        return ResponseInfo.setInfo("success","修改成功");
    }
    /**
     * 通过ID删除文章
     * @param category_id
     * @return
     * @throws Exception
     */
    @Override
    public ResponseInfo deleteCategory(Integer category_id) throws Exception {
       int count= categoryMapper.findArticleByCategoryId(category_id);
        if(count>0){
            return ResponseInfo.setInfo("warning","该分类下有关联文章，不能删除");
        }else {
            try {
                categoryMapper.deleteCategory(category_id);//删除
            }catch (Exception e){
                throw new CustomException("删除失败");
            }
        }
        return ResponseInfo.setInfo("success","删除成功");
    }
    /**
     * 查询文章分类和该文章分类下的文章列表
     */
    public List<Category> findArticleToCategory(){
        return categoryMapper.findArticleToCategory();
    }
}
