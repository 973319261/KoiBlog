package com.koi.service;

import com.koi.entity.Tag;
import com.koi.vo.ResponseInfo;

import java.util.List;

public interface TagService {
    /**
     * 查询所有标签
     * @return
     */
    List<Tag> findTagAll();

    /**
     * 新增标签
     * @param tag_name
     * @return
     */
    ResponseInfo insertTag(String tag_name) throws Exception;

    /**
     * 删除标签
     * @param tag_id
     * @return
     */
    ResponseInfo deleteTagByTagId(Integer tag_id)throws Exception;
}
