package com.koi.web.controller;

import com.koi.entity.Tag;
import com.koi.service.TagService;
import com.koi.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签控制器
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 查询所有标签
     * @return
     */
    @RequestMapping("/findTagAll")
    public List<Tag> findTagAll(){
        return tagService.findTagAll();
    }

    /**
     * 新增标签
     * @param tag_name
     * @return
     */
    @RequestMapping("/insertTag")
    public ResponseInfo insertTag(String tag_name) throws Exception{
        return tagService.insertTag(tag_name);
    }

    /**
     * 删除标签
     * @param tag_id
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteTagByTagId")
    public ResponseInfo deleteTagByTagId(Integer tag_id) throws Exception{
        return tagService.deleteTagByTagId(tag_id);
    }
}
