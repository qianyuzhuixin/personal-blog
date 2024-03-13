package com.xiaoyang.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName comment
 */
@TableName(value = "comment")
@Data
public class Comment implements Serializable {
    @TableId(value = "comment_id")
    private String commentId;

    private String articleId;

    private String userId;

    // 评论内容
    private String commentContext;

    private Date commentTime;

    private Integer commentGoodNums;

    private Integer commentStatus;

    private static final long serialVersionUID = 1L;
}