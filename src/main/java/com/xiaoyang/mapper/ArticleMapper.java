package com.xiaoyang.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyang.vo.article.AdminArticlePageVo;
import com.xiaoyang.vo.article.ShowArticleVo;
import com.xiaoyang.vo.article.UserArticlePageVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaomei
 * @description 针对表【article(文章)】的数据库操作Mapper
 * @createDate 2023-11-11 11:27:56
 * @Entity com.xiaoyang.pojo.Article
 */
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<AdminArticlePageVo> articleList(IPage<AdminArticlePageVo> articlePage, String articleTitle);

    IPage<UserArticlePageVo> myArticles(Page<UserArticlePageVo> articlePageVoPage, @Param("userId") String userId);

    ShowArticleVo showArticle(@Param("articleId") String articleId);

    void updateArticleLookNums(String articleId, Integer articleLookNums);
}




