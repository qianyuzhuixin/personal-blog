package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "user_collect_id", type = IdType.AUTO)
    private Integer userCollectId;

    private String userId;

    private String collectArticleId;

    private Date collectTime;

    private static final long serialVersionUID = 1L;
}