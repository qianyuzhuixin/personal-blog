package com.xiaoyang.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyang.pojo.User;
import com.xiaoyang.vo.comment.CommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaomei
 * @description 针对表【comment(文章评论)】的数据库操作Mapper
 * @createDate 2023-11-10 14:37:50
 * @Entity com.xiaoyang.pojo.Comment
 */
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<CommentVo> showComment(Page<CommentVo> commentVoPage, @Param("articleId") String articleId);

    User getUserInfoByCommentId(String commentId);
}




