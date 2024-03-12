package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName comment_reply
 */
@TableName(value ="comment_reply")
@Data
public class CommentReply implements Serializable {
    @TableId(value = "comment_reply_id")
    private String commentReplyId;

    private String commentId;

    private String replyUserId;

    private String secondlyUserId;

    private Date commentReplyAddTime;

    private static final long serialVersionUID = 1L;
}