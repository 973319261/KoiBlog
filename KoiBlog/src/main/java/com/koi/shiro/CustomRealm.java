package com.koi.shiro;

import com.koi.entity.User;
import com.koi.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
       /* System.out.println("授权中。。。");
        //获取当前登录的用户
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        User user= (User) principalCollection.getPrimaryPrincipal();
        Long userId=user.getId();
        //查询角色
        List<URole> selectRoleByUserId = userRoleService.selectRoleByUserId(userId);
        for (URole uRole : selectRoleByUserId) {
            info.addRole(uRole.getType());
        }
        //获取用户的所有权限
        List<UPermission> selectPermissionByUserId = userPermissonService.selectPermissionByUserId(userId);
        for (UPermission uPermission : selectPermissionByUserId) {
            info.addStringPermission(uPermission.getUrl());
        }
        return info;*/
       return null;
    }
    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证中。。。");
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        String user_name=token.getUsername();
        List<User> list = userService.findUserAdminByName(user_name);
        //验证成功
        if(list.size() > 0){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(list.get(0),list.get(0).getUser_pass(),getName());
            //设置盐，用来核对密码
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(list.get(0).getUser_salt()));
            return simpleAuthenticationInfo;
        }
        return null;
    }
}

