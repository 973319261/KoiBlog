package com.koi.mapper;

import com.koi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * 通过用户名称查询管理员信息
     * @param user_name
     * @return
     */
    List<User> findUserAdminByName(@Param("user_name") String user_name);
    /**
     * 通过用户ID修改用户信息
     * @param user
     * @return
     */
    int updateUserById(User user) throws Exception;
    /**
     * 通过user_id查询用户信息
     * @param user_id
     * @return
     */
    User findUserById(Integer user_id);
}
