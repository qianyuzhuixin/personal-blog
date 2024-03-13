package com.xiaoyang.vo.comment;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @TableName comment
 */
@Data
public class CommentVo implements Serializable {

    // 评论id
    private String commentId;

    // 文章id
    private String articleId;

    // 评论人id
    private String userId;

    // 评论人名称
    private String userName;

    // 评论内容
    private String commentContext;

    // 评论时间
    private Date commentTime;

    // 点赞数
    private Integer commentGoodNums;

    // 是否已经点赞过
    private Integer isGood;

    // 回复评论
    private List<ReplyCommentVo> replyCommentVos;

    private static final long serialVersionUID = 1L;
}