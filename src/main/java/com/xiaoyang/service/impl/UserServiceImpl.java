package com.xiaoyang.service.impl;

import java.util.Date;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.dto.article.PublishArticleActionDTO;
import com.xiaoyang.pojo.Article;
import com.xiaoyang.pojo.User;
import com.xiaoyang.service.ArticleService;
import com.xiaoyang.service.ArticleTagListService;
import com.xiaoyang.service.UserService;
import com.xiaoyang.mapper.UserMapper;
import com.xiaoyang.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaomei
 * @description 针对表【user(用户数据表)】的数据库操作Service实现
 * @createDate 2023-11-10 14:37:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


}




