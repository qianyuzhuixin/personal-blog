package com.xiaoyang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.dto.article.ArticleListByTagPageDTO;
import com.xiaoyang.dto.article.ArticleListDTO;
import com.xiaoyang.dto.article.ArticleTopSearchPageDTO;
import com.xiaoyang.dto.article.PublishArticleActionDTO;
import com.xiaoyang.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyang.pojo.User;
import com.xiaoyang.utils.Result;
import com.xiaoyang.vo.article.AdminArticlePageVo;
import com.xiaoyang.vo.article.IndexArticleVo;
import com.xiaoyang.vo.article.ShowArticleVo;
import com.xiaoyang.vo.article.UserArticlePageVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiaomei
 * @description 针对表【article(文章)】的数据库操作Service
 * @createDate 2023-11-11 11:27:56
 */
public interface ArticleService extends IService<Article> {

    IPage<AdminArticlePageVo> articleList(IPage<AdminArticlePageVo> articlePage, String articleTitle);

    Result publishArticle(PublishArticleActionDTO publishArticleActionDTO, User user);

    IPage<UserArticlePageVo> myArticles(Page<UserArticlePageVo> articlePageVoPage, String userId);

    Result delArticle(String articleId);

    List<IndexArticleVo> getArticleIndexList();

    IPage<IndexArticleVo> selectPageList(ArticleListDTO articleListDTO);

    ShowArticleVo showArticle(String articleId);

    void updateArticleLookNums(ShowArticleVo article);

    IPage<IndexArticleVo> searchArticleByTypeOrTitle(ArticleTopSearchPageDTO articleTopSearchPageDTO, HttpServletRequest request);

    IPage<IndexArticleVo> articleListByTagId(HttpServletRequest request, ArticleListByTagPageDTO articleListByTagPageDTO);
}
