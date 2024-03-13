package com.xiaoyang.vo.comment;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName comment
 */
@Data
public class ReplyCommentVo implements Serializable {

    // 被回复评论id
    private String topLevelCommentId;

    // 被回复评论id
    private String beRepliedCommentId;

    // 回复评论id
    private String replyCommentId;

    // 回复人id
    private String replyUserId;

    // 被回复人id
    //be_replied_user_id
    private String beRepliedUserId;

    // 回复人名称
    private String replyUserName;

    // 被回复人名称
    private String beRepliedUserName;

    // 评论内容
    private String commentContext;

    // 评论时间
    private Date commentTime;

    // 点赞数
    private Integer commentGoodNums;

    // 是否已经点赞过
    private Integer isGood;

    // 是否是二级以上的评论
    private Integer commentStatus;

    private static final long serialVersionUID = 1L;
}