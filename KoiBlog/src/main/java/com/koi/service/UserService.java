package com.koi.service;

import com.koi.entity.User;
import com.koi.vo.ResponseInfo;

import java.util.List;

public interface UserService{
    /**
     * 通过用户名称查询管理员信息
     * @param user_name
     * @return
     */
    List<User> findUserAdminByName(String user_name);

    /**
     * 通过用户ID修改用户登录信息
     * @param user
     * @return
     */
    int updateUserLogin(User user) throws Exception;

    /**
     * 通过user_id查询用户信息
     * @param user_id
     * @return
     */
    User findUserById(Integer user_id);
    /**
     *  修改个人信息
     * @param user
     * @return
     * @throws Exception
     */
    int updateUser(User user) throws Exception;

    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @return
     */
    ResponseInfo updatePass(String oldPass, String newPass) throws Exception;
}
