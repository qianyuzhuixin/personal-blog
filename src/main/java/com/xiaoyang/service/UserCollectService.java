package com.xiaoyang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.pojo.UserCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyang.utils.Result;
import com.xiaoyang.vo.article.UserCollectArticlePageVo;

/**
 * @author xiaomei
 * @description 针对表【user_collect(用户收藏文章表)】的数据库操作Service
 * @createDate 2024-01-13 19:52:16
 */
public interface UserCollectService extends IService<UserCollect> {

    IPage<UserCollectArticlePageVo> getUserCollects(Page<UserCollectArticlePageVo> userCollectPage, String userId);

    Result delCollect(String articleId);
}
