package com.koi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 日志表
 */
@Data
public class Log implements Serializable {
    /**
     * 日志ID
     */
    private Integer log_id;
    /**
     * 操作人
     */
    private String log_operateor;
    /**
     * 操作类型
     */
    private String log_operateType;
    /**
     * 操作时间
     */
    private String log_operateDate;
    /**
     * 操作状态
     */
    private String log_operateResule;
    /**
     * ip地址
     */
    private String log_ip;
}

