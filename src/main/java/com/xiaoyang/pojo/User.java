package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableId(value = "user_id")
    private String userId;

    private String userName;

    private String userPassword;

    //注册时间
    private Date userRegisterTime;

    //是否冻结，0正常，1冻结，冻结无法登陆
    private Integer userFrozen;

    // 是否允许发布文章，0正常，1不允许
    private Integer userWrite;

    private static final long serialVersionUID = 1L;
}