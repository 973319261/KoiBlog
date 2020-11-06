package com.koi.vo;

import lombok.Data;

/**
 * 响应信息
 */
@Data
public class ResponseInfo {
    /**
     * 状态
     */
    private String status;
    /**
     * 提示
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public ResponseInfo() {
    }

    public ResponseInfo(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    public ResponseInfo(String status, String msg,Object data) {
        this.status = status;
        this.msg = msg;
        this.data=data;
    }

    public static ResponseInfo setInfo(String status, String msg) {
       return new ResponseInfo(status, msg);
    }

    public static ResponseInfo setInfo(String status, String msg,Object data) {
        return new ResponseInfo(status, msg,data);
    }
}
