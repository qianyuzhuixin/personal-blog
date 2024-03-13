package com.xiaoyang.service.impl;

import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.mapper.CommentMapper;
import com.xiaoyang.pojo.Comment;
import com.xiaoyang.pojo.User;
import com.xiaoyang.service.UserService;
import com.xiaoyang.mapper.UserMapper;
import com.xiaoyang.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaomei
 * @description 针对表【user(用户数据表)】的数据库操作Service实现
 * @createDate 2023-11-10 14:37:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public Result getReplyPeople(String commentId) {
        Comment comment = commentMapper.selectById(commentId);

        if (Objects.isNull(comment)) {
            return Result.failed("评论不存在");
        }

        String userId = comment.getUserId();
        if (StrUtil.isBlank(userId)) {
            return Result.failed("页面异常，请刷新重试！");
        }
        User user = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            return Result.failed("评论用户不存在");
        }
        return Result.OK(user);
    }
}




