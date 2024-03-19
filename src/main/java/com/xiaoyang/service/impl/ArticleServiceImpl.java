package com.xiaoyang.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.dto.article.ArticleListByTagPageDTO;
import com.xiaoyang.dto.article.ArticleListDTO;
import com.xiaoyang.dto.article.ArticleTopSearchPageDTO;
import com.xiaoyang.dto.article.PublishArticleActionDTO;
import com.xiaoyang.dto.base.CommonPage;
import com.xiaoyang.mapper.UserCollectMapper;
import com.xiaoyang.pojo.*;
import com.xiaoyang.service.*;
import com.xiaoyang.mapper.ArticleMapper;
import com.xiaoyang.utils.RedisCache;
import com.xiaoyang.utils.Result;
import com.xiaoyang.vo.article.AdminArticlePageVo;
import com.xiaoyang.vo.article.IndexArticleVo;
import com.xiaoyang.vo.article.ShowArticleVo;
import com.xiaoyang.vo.article.UserArticlePageVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaomei
 * @description 针对表【article(文章)】的数据库操作Service实现
 * @createDate 2023-11-11 11:27:56
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleTagListService articleTagListService;

    @Resource
    private CommentService commentService;

    @Resource
    private CommentReplyService commentReplyService;

    @Resource
    private ArticleTypeService articleTypeService;

    @Resource
    private ServletContext servletContext;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private UserCollectMapper userCollectMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public IPage<AdminArticlePageVo> articleList(IPage<AdminArticlePageVo> articlePage, String articleTitle) {
        return articleMapper.articleList(articlePage, articleTitle);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result publishArticle(PublishArticleActionDTO publishArticleActionDTO, User user) {
        // 保存文章
        Article article = new Article();
        article.setArticleId(publishArticleActionDTO.getArticleId());
        article.setArticleTitle(publishArticleActionDTO.getArticleTitle());
        article.setArticleCoverUrl(publishArticleActionDTO.getArticleCoverUrl());
        if (StrUtil.isBlank(article.getArticleId())) {
            article.setArticleAddTime(DateUtil.date());
        }
        article.setArticleContent(publishArticleActionDTO.getArticleContent());
        article.setArticleGoodNums(0);
        article.setArticleLookNums(0);
        article.setArticleCollectionNums(0);
        article.setUserId(user.getUserId());
        article.setArticleTypeId(publishArticleActionDTO.getArticleTypeId());
        if (!saveOrUpdate(article)) {
            return Result.failed("发布文章失败, 请稍后重试");
        }

        ArrayList<ArticleTagList> articleTagLists = new ArrayList<>();
        for (String articleTagId : publishArticleActionDTO.getArticleTagIds()) {
            ArticleTagList articleTagList = new ArticleTagList();
            articleTagList.setArticleId(article.getArticleId());
            articleTagList.setArticleTagId(articleTagId);
            articleTagLists.add(articleTagList);
        }
        if (StrUtil.isNotBlank(publishArticleActionDTO.getArticleId()) && Objects.nonNull(publishArticleActionDTO.getArticleTagIds()) && publishArticleActionDTO.getArticleTagIds().length > 0) {
            articleTagListService.remove(Wrappers.<ArticleTagList>lambdaQuery()
                    .eq(ArticleTagList::getArticleId, publishArticleActionDTO.getArticleId()));
        }
        if (!articleTagListService.saveBatch(articleTagLists, 50)) {
            throw new RuntimeException("发布文章失败，保存标签失败");
        }
        servletContext.removeAttribute("articleIndexList");

        redisCache.deleteObject("articleIndexList");

        return Result.OK("发布成功！");
    }

    @Override
    public IPage<UserArticlePageVo> myArticles(Page<UserArticlePageVo> articlePageVoPage, String userId) {
        return articleMapper.myArticles(articlePageVoPage, userId);
    }

    @Override
    public Result delArticle(String articleId) {
        if (!removeById(articleId)) {
            return Result.failed("删除文章失败,文章可能已经被删除");
        }
        userCollectMapper.delete(Wrappers.<UserCollect>lambdaQuery()
                .eq(UserCollect::getCollectArticleId, articleId));

        servletContext.removeAttribute("articleIndexList");
        List<Comment> commentList = commentService.list(Wrappers.<Comment>lambdaQuery().eq(Comment::getArticleId, articleId)
                .select(Comment::getCommentId));
        if (CollUtil.isNotEmpty(commentList)) {
            List<String> collect = commentList.stream().map(Comment::getCommentId).collect(Collectors.toList());
            commentService.removeByIds(collect);
            commentReplyService.remove(Wrappers.<CommentReply>lambdaQuery().in(CommentReply::getCommentId, collect));
        }
        return Result.OK("删除成功！");
    }

    @Override
    public List<IndexArticleVo> getArticleIndexList() {
        List<Article> articleList = list(Wrappers.<Article>lambdaQuery().orderByAsc(Article::getArticleAddTime).last("limit 9"));
        List<IndexArticleVo> articleIndexList = new ArrayList<>();
        for (Article article : articleList) {
            IndexArticleVo indexArticleVo = new IndexArticleVo();
            indexArticleVo.setArticleId(article.getArticleId());
            indexArticleVo.setArticleTitle(article.getArticleTitle());
            indexArticleVo.setArticleTypeName(articleTypeService.getById(article.getArticleTypeId()).getArticleTypeName());
            indexArticleVo.setArticleCoverUrl(article.getArticleCoverUrl());
            indexArticleVo.setArticleLookNums(article.getArticleLookNums());
            indexArticleVo.setArticleGoodNums(article.getArticleGoodNums());
            indexArticleVo.setArticleCollectionNums(article.getArticleCollectionNums());
            indexArticleVo.setArticleAddTime(article.getArticleAddTime());
            articleIndexList.add(indexArticleVo);
        }

        return articleIndexList;
    }

    @Override
    public IPage<IndexArticleVo> selectPageList(ArticleListDTO articleListDTO) {
        Page<Article> page = new Page<>(articleListDTO.getPageNum(), articleListDTO.getPageSize());
        IPage<Article> articleIPage = articleMapper.selectPage(page, Wrappers.<Article>lambdaQuery()
                .eq(Article::getArticleTypeId, articleListDTO.getArticleTypeId()).orderByAsc(Article::getArticleAddTime));
        String articleTypeName = articleTypeService.getById(articleListDTO.getArticleTypeId()).getArticleTypeName();
        IPage<IndexArticleVo> articleVoIPage = articleIPage.convert(entity -> {
            IndexArticleVo indexArticleVo = new IndexArticleVo();
            // 将 UserEntity 转换为 UserDTO
            BeanUtils.copyProperties(entity, indexArticleVo);
            indexArticleVo.setArticleTypeName(articleTypeName);
            return indexArticleVo;
        });

        return articleVoIPage;
    }

    @Override
    public ShowArticleVo showArticle(String articleId) {
        return articleMapper.showArticle(articleId);
    }

    @Override
    public void updateArticleLookNums(ShowArticleVo article) {
        articleMapper.updateArticleLookNums(article.getArticleId(), article.getArticleLookNums());
    }

    @Override
    public IPage<IndexArticleVo> searchArticleByTypeOrTitle(ArticleTopSearchPageDTO articleTopSearchPageDTO, HttpServletRequest request) {
        Page<Article> page = new Page<>(articleTopSearchPageDTO.getPageNum(), articleTopSearchPageDTO.getPageSize());
        ArticleType articleType = articleTypeService.getOne(Wrappers.<ArticleType>lambdaQuery()
                .eq(ArticleType::getArticleTypeName, articleTopSearchPageDTO.getKeyword()));
        ArticleListDTO articleListDTO = new ArticleListDTO();
        IPage<IndexArticleVo> articleIPageList;
        if (Objects.nonNull(articleType)) {
            articleListDTO.setArticleTypeId(articleType.getArticleTypeId());
            // 获取文章列表
            articleIPageList = selectPageList(articleListDTO);
        } else {
            IPage<Article> articleIPage = articleMapper.selectPage(page, Wrappers.<Article>lambdaQuery()
                    .like(Article::getArticleTitle, articleTopSearchPageDTO.getKeyword())
                    .orderByDesc(Article::getArticleGoodNums));
            articleIPageList = articleIPage.convert(entity -> {
                IndexArticleVo indexArticleVo = new IndexArticleVo();
                // 将 UserEntity 转换为 UserDTO
                BeanUtils.copyProperties(entity, indexArticleVo);
                return indexArticleVo;
            });
        }

        return articleIPageList;
    }

    @Override
    public IPage<IndexArticleVo> articleListByTagId(HttpServletRequest request, ArticleListByTagPageDTO articleListByTagPageDTO) {
        Page<Article> page = new Page<>(articleListByTagPageDTO.getPageNum(), articleListByTagPageDTO.getPageSize());
        // 获取该标签下的文章id
        List<ArticleTagList> articleTagLists = articleTagListService.list(Wrappers.<ArticleTagList>lambdaQuery()
                .eq(ArticleTagList::getArticleTagId, articleListByTagPageDTO.getArticleTagId())
                .select(ArticleTagList::getArticleId));
        List<String> articleIds = articleTagLists.stream().map(ArticleTagList::getArticleId).collect(Collectors.toList());
        if (CollUtil.isEmpty(articleIds)) {
            return new Page<>();
        }
        // 根据文章id获取文章列表
        List<Article> articleList = articleMapper.selectBatchIds(articleIds);
        // 按发布日期降序排序
        Collections.sort(articleList, Comparator.comparing(Article::getArticleAddTime).reversed());
        // 设置总记录数，这通常是从数据库查询中获取的
        long total = articleList.size();
        // 设置当前页的记录列表
        page.setRecords(articleList);
        // 设置总记录数
        page.setTotal(total);
        // 返回IPage对象
        IPage<Article> articleIPageList = page;
        IPage<IndexArticleVo> articleIPageByTagList = articleIPageList.convert(entity -> {
            IndexArticleVo indexArticleVo = new IndexArticleVo();
            // 将 UserEntity 转换为 UserDTO
            BeanUtils.copyProperties(entity, indexArticleVo);
            return indexArticleVo;
        });

        return articleIPageByTagList;
    }

}




