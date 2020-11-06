package com.koi.utils;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.io.FileUtil;
import com.koi.vo.ResponseInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;


public class CustomUtils {
    /**
     * 获取request对象
     *
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 上传文件工具类
     * @param request
     * @param image
     * @param filePath
     * @return
     */
    public static ResponseInfo uploadFile(HttpServletRequest request, MultipartFile image, String filePath) {
        StringBuffer url = new StringBuffer();
        String imgFolderPath = request.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {//判断是否存在目录
            FileUtil.mkdir(imgFolderPath);//创建
        }

        //拼接图片名称
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        File file = new File(imgFolderPath + "\\" + imgName);
        try {
            //将图片保存到服务器文件中
            image.transferTo(file);
            //拼接图片链接
            //  http://127.0.0.1:8082/KoiBlog_war/blogImg/20190729/b48ffdf0-72a9-41f4-9348-8d7429b860ab_75156.jpg
            url.append(request.getScheme())
                    .append("://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath())
                    .append(filePath)
                    .append("/")
                    .append(imgName);
            return ResponseInfo.setInfo("success", url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseInfo.setInfo("error", "上传失败");
    }
}
