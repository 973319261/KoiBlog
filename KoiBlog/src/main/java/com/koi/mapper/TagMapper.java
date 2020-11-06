package com.koi.mapper;

import com.koi.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface TagMapper {
    /**
     * 新增标签d到标签表
     * @param dynamicTags
     * @return
     */
    int insertTag(List<String> dynamicTags) throws Exception;
    /**
     * 新增标签明细表
     * @param article_id
     * @param tag_id
     * @return
     */
    int insertArticleAndTag(@Param("article_id")Integer article_id, @Param("tag_id")List<Integer> tag_id) throws Exception;
    /**
     * 通过article_id删除标签
     * @param article_id
     * @return
     */
    int deleteTagByArticleId(Integer article_id) throws Exception;

    /**
     * 通过标签ID删除标签和标签明细
     * @param tag_id
     * @return
     * @throws Exception
     */
    int deleteTagByTagId(Integer tag_id) throws Exception;

    /**
     * 通过标签名字查询标签ID
     * @param dynamicTags
     * @return
     */
    List<Integer> findTagsIdByTagName(List<String> dynamicTags);

    /**
     * 通过标签名查询标签是否存在
     * @param dynamicTags
     * @return
     */
    List<String> findTagsName(List<String>  dynamicTags);

    /**
     * 查询所有标签
     * @return
     */
    List<Tag> findTagAll();

}
