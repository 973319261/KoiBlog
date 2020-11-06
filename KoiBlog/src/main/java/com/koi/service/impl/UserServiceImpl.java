package com.koi.service.impl;

import com.koi.entity.User;
import com.koi.exception.CustomException;
import com.koi.mapper.UserMapper;
import com.koi.service.UserService;
import com.koi.vo.ResponseInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 通过用户名称查询管理员信息
     * @param user_name
     * @return
     */
    public List<User> findUserAdminByName(String user_name){
        return userMapper.findUserAdminByName(user_name);
    }
    /**
     * 通过用户ID修改用户登录信息
     * @param user
     * @return
     */
    public int updateUserLogin(User user)throws Exception{
        return userMapper.updateUserById(user);
    }
    /**
     * 通过user_id查询用户信息
     * @param user_id
     * @return
     */
    public User findUserById(Integer user_id){
        return userMapper.findUserById(user_id);
    }
    /**
     *  修改个人信息
     * @param user
     * @return
     * @throws Exception
     */
    public int updateUser(User user) throws Exception{
        return userMapper.updateUserById(user);
    }

    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @return
     * @throws Exception
     */
    public ResponseInfo updatePass(String oldPass, String newPass)throws Exception{
      try{
          // 判断旧密码是否跟登录密码一致
          // 先获取用户的登陆身份通过shiro认证管理工具获得
          User user=(User)SecurityUtils.getSubject().getPrincipal();
          user=userMapper.findUserById(user.getUser_id());
          //解密
          oldPass=new SimpleHash("MD5",oldPass,user.getUser_salt(),3).toHex();
          if (!user.getUser_pass().equals(oldPass)){
              return ResponseInfo.setInfo("error","旧密码错误");
          }
          // 生成盐（部分，需要存入数据库中）
          String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
          // 将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
          newPass=new Md5Hash(newPass,salt,3).toString();
          User user1=new User();
          user1.setUser_id(user.getUser_id());
          user1.setUser_salt(salt);
          user1.setUser_pass(newPass);
          int i= userMapper.updateUserById(user1);
          if(i>0){
              return ResponseInfo.setInfo("success","密码修改成功");
          }
          return ResponseInfo.setInfo("error","密码修改失败");
      }catch (Exception e){
          throw new CustomException("密码修改失败");
      }
    }
}
