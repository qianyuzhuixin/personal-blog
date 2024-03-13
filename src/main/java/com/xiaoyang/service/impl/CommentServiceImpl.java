package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.mapper.CommentReplyMapper;
import com.xiaoyang.pojo.Comment;
import com.xiaoyang.pojo.User;
import com.xiaoyang.service.CommentService;
import com.xiaoyang.mapper.CommentMapper;
import com.xiaoyang.vo.comment.CommentVo;
import com.xiaoyang.vo.comment.ReplyCommentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaomei
 * @description 针对表【comment(文章评论)】的数据库操作Service实现
 * @createDate 2023-11-10 14:37:50
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CommentReplyMapper commentReplyMapper;

    @Override
    public IPage<CommentVo> showComment(Page<CommentVo> commentVoPage, String articleId) {
        return commentMapper.showComment(commentVoPage, articleId);
    }

    @Override
    public IPage<ReplyCommentVo> showReplyComment(Page<ReplyCommentVo> replyCommentVo, String commentId) {
        return commentReplyMapper.showReplyComment(replyCommentVo, commentId);
    }

    @Override
    public User getUserInfoByCommentId(String commentId) {
        return commentMapper.getUserInfoByCommentId(commentId);
    }
}




