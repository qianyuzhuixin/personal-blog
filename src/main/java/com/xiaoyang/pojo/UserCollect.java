package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName user_collect
 */
@TableName(value = "user_collect")
@Data
public class UserCollect implements Serializable {
    private Integer userCollectId;

    private String userId;

    private String collectArticleId;

    private Date collectTime;

    private static final long serialVersionUID = 1L;
}