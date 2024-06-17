package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName admin
 */
@TableName(value = "admin")
@Data
public class Admin implements Serializable {
    @TableId(value = "admin_id")
    private String adminId;

    private String adminName;

    private String adminPassword;

    private static final long serialVersionUID = 1L;
}