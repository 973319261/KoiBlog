package com.koi.service.impl;

import com.koi.entity.Tag;
import com.koi.exception.CustomException;
import com.koi.mapper.TagMapper;
import com.koi.service.TagService;
import com.koi.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    public List<Tag> findTagAll(){
        return tagMapper.findTagAll();
    }
    /**
     * 新增标签
     * @param tag_name
     * @return
     */
    public ResponseInfo insertTag(String tag_name) throws Exception{
        List<String> list=new ArrayList<>();
        list.add(tag_name);
        List<String > tags=tagMapper.findTagsName(list);
        if(tags.size()>0){
            return ResponseInfo.setInfo("warning","该标签已存在");
        }else {
           try {
              int i= tagMapper.insertTag(list);//新增标签
               System.out.println(i);
           }catch (Exception e){
               throw new CustomException("新增标签失败");
           }
        }
        return ResponseInfo.setInfo("success","新增标签成功");
    }

    /**
     * 删除标签
     * @param tag_id
     * @return
     */
    public ResponseInfo deleteTagByTagId(Integer tag_id) throws Exception{
        try{
            tagMapper.deleteTagByTagId(tag_id);
        }catch (Exception e){
            throw new CustomException("标签删除失败");
        }
        return ResponseInfo.setInfo("success","标签删除成功");
    }
}
