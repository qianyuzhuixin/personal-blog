package com.xiaoyang.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 评论回复
 *
 * @TableName comment_reply
 */
@TableName(value = "comment_reply")
@Data
public class CommentReply implements Serializable {
    /**
     * 评论回复id
     */
    @TableId
    private String commentReplyId;

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 被回复评论id
     */
    private String beRepliedCommentId;

    /**
     * 有可能在评论中回复他人的评论，这个是第一层的评论id
     */
    private String topLevelCommentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}