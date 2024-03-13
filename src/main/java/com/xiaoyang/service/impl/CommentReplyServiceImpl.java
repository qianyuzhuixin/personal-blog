package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.CommentReply;
import com.xiaoyang.service.CommentReplyService;
import com.xiaoyang.mapper.CommentReplyMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomei
* @description 针对表【comment_reply(评论回复)】的数据库操作Service实现
* @createDate 2024-03-12 15:13:27
*/
@Service
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply>
    implements CommentReplyService{

}




