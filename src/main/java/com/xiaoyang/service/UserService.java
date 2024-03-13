package com.xiaoyang.service;

import com.xiaoyang.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyang.utils.Result;

/**
 * @author xiaomei
 * @description 针对表【user(用户数据表)】的数据库操作Service
 * @createDate 2023-11-10 14:37:50
 */
public interface UserService extends IService<User> {

    Result getReplyPeople(String commentId);
}
