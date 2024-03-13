package com.xiaoyang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyang.pojo.User;
import com.xiaoyang.vo.comment.CommentVo;
import com.xiaoyang.vo.comment.ReplyCommentVo;

/**
 * @author xiaomei
 * @description 针对表【comment(文章评论)】的数据库操作Service
 * @createDate 2023-11-10 14:37:50
 */
public interface CommentService extends IService<Comment> {

    IPage<CommentVo> showComment(Page<CommentVo> commentVoPage, String articleId);

    IPage<ReplyCommentVo> showReplyComment(Page<ReplyCommentVo> replyCommentVo, String commentId);

    User getUserInfoByCommentId(String commentId);
}
