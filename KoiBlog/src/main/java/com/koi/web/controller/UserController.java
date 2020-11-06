package com.koi.web.controller;

import cn.hutool.http.HttpRequest;
import com.koi.entity.User;
import com.koi.exception.CustomException;
import com.koi.service.UserService;
import com.koi.utils.CustomUtils;
import com.koi.vo.ResponseInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 管理员后台登录
     *
     * @param user_name
     * @param user_pass
     * @param session
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/adminLogin")
    public ResponseInfo adminLogin(String user_name, String user_pass, HttpSession session, HttpServletRequest request) throws Exception {
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user_name, user_pass);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
            // 获取到返回的用户信息
            User user = (User) subject.getPrincipal();
            session.setAttribute("USER_SESSION", user);
            // 修改最后登录时间
            User user1=new User();
            user1.setUser_id(user.getUser_id());
            user1.setUser_lastTime(new Date());
            user1.setUser_lastIp(CustomUtils.getIpAddr(request));
            userService.updateUserLogin(user1);
        } catch (AuthenticationException e) {
            return ResponseInfo.setInfo("error", "账号或密码错误");
        } catch (Exception e) {
            throw new CustomException("用户信息修改失败");
        }
        return ResponseInfo.setInfo("success", "登录成功");
    }

    /**
     * 获取用户session
     *
     * @return
     */
    @RequestMapping("/userSession")
    public User userSession() {
        User user = null;
        HttpSession session = CustomUtils.getHttpServletRequest().getSession();
        if (session != null) {
            user = (User) session.getAttribute("USER_SESSION");
        }
        return user;
    }

    /**
     * 清空session
     */
    @RequestMapping("/cleanSession")
    public void cleanSession() {
        HttpSession session = CustomUtils.getHttpServletRequest().getSession();
        if (session != null) {
            session.removeAttribute("USER_SESSION");
        }
    }

    /**
     * 通过user_id查询用户信息
     *
     * @return
     */
    @RequestMapping("/findUserById")
    public User findUserById() {
        HttpSession session = CustomUtils.getHttpServletRequest().getSession();
        User user = (User) session.getAttribute("USER_SESSION");
        if(user!=null){
            Integer user_id = user.getUser_id();
            return userService.findUserById(user_id);
        }
       return null;
    }

    /**
     * 用户上传头像
     *
     * @param image
     * @return
     */
    @RequestMapping("/uploadAvatar")
    public ResponseInfo uploadAvatar(HttpServletRequest request, MultipartFile image) {
        String filePath = "/blogImg/Avatar";
        return CustomUtils.uploadFile(request, image, filePath);
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     * @throws CustomException
     */
    @RequestMapping("/updateUser")
    public ResponseInfo updateUser(User user) throws CustomException {
        try {
            int i = userService.updateUser(user);
            if (i > 0) {
                return ResponseInfo.setInfo("success", "修改用户信息成功");
            } else {
                return ResponseInfo.setInfo("error", "修改用户信息失败");
            }
        } catch (Exception e) {
            throw new CustomException("修改用户信息失败");
        }
    }

    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePass")
    public ResponseInfo updatePass(String oldPass,String newPass)throws Exception{
       return userService.updatePass(oldPass,newPass);
    }
}
