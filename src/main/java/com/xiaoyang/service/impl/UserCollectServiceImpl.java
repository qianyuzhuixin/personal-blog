package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.Article;
import com.xiaoyang.pojo.UserCollect;
import com.xiaoyang.service.ArticleService;
import com.xiaoyang.service.UserCollectService;
import com.xiaoyang.mapper.UserCollectMapper;
import com.xiaoyang.utils.Result;
import com.xiaoyang.vo.article.UserCollectArticlePageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * @author xiaomei
 * @description 针对表【user_collect(用户收藏文章表)】的数据库操作Service实现
 * @createDate 2024-01-13 19:52:16
 */
@Service
public class UserCollectServiceImpl extends ServiceImpl<UserCollectMapper, UserCollect>
        implements UserCollectService {

    @Resource
    private UserCollectMapper userCollectMapper;

    @Resource
    private ArticleService articleService;

    @Resource
    private ServletContext servletContext;


    @Override
    public IPage<UserCollectArticlePageVo> getUserCollects(Page<UserCollectArticlePageVo> userCollectPage, String userId) {

        return userCollectMapper.getUserCollects(userCollectPage, userId);
    }

    @Override
    @Transactional
    public Result delCollect(String articleId) {
        if (!remove(Wrappers.<UserCollect>lambdaQuery().eq(UserCollect::getCollectArticleId, articleId))) {
            return Result.failed("取消收藏失败");
        }
        servletContext.removeAttribute("articleIndexList");
        Article article = articleService.getById(articleId);
        article.setArticleCollectionNums(article.getArticleCollectionNums() - 1);
        articleService.updateById(article);
        return Result.OK("已取消！");
    }
}




