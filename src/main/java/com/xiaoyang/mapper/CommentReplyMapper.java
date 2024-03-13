package com.xiaoyang.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.pojo.CommentReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyang.vo.comment.ReplyCommentVo;

/**
 * @author xiaomei
 * @description 针对表【comment_reply(评论回复)】的数据库操作Mapper
 * @createDate 2024-03-12 15:13:27
 * @Entity com.xiaoyang.pojo.CommentReply
 */
public interface CommentReplyMapper extends BaseMapper<CommentReply> {

    IPage<ReplyCommentVo> showReplyComment(Page<ReplyCommentVo> replyCommentVo, String commentId);
}




