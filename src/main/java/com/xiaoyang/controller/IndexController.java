package com.xiaoyang.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.annotation.LogAnnotation;
import com.xiaoyang.constant.HomeConstant;
import com.xiaoyang.dto.article.ArticleListByTagPageDTO;
import com.xiaoyang.dto.article.ArticleListDTO;
import com.xiaoyang.dto.article.ArticleTopSearchPageDTO;
import com.xiaoyang.dto.base.CommonPage;
import com.xiaoyang.pojo.*;
import com.xiaoyang.service.*;
import com.xiaoyang.utils.CommonUtils;
import com.xiaoyang.utils.RedisCache;
import com.xiaoyang.utils.Result;
import com.xiaoyang.vo.article.ArticleTypeHomeTreeVo;
import com.xiaoyang.vo.article.IndexArticleVo;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: index控制类
 * @Author: xiaoyang
 * @Date: 2023/11/10 010
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleTagListService articleTagListService;

    @Autowired
    private AdService adService;

    @Autowired
    private AdTypeService adTypeService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserCollectService userCollectService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisCache redisCache;


    // 联系页面跳转
    @GetMapping("contact")
    @LogAnnotation(module = "首页", operator = "联系页面跳转")
    public String contact() {
        return "/home/contact";
    }

    // 捐赠页面跳转
    @GetMapping("donate")
    @LogAnnotation(module = "首页", operator = "捐赠页面跳转")
    public String donate() {
        return "/home/donate";
    }


    // 首页数据加载
    @GetMapping("")
    @LogAnnotation(module = "首页", operator = "首页数据加载")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 获取文章类型
        List<ArticleTypeHomeTreeVo> articleTypeHomeTreeVoList = redisCache.getCacheList("articleTypeHomeTreeVoList");
        if (CollUtil.isEmpty(articleTypeHomeTreeVoList)) {
            articleTypeHomeTreeVoList = articleTypeService.getArticleTypeHomeTree();
            if (CollUtil.isNotEmpty(articleTypeHomeTreeVoList)) {
                redisCache.setCacheList("articleTypeHomeTreeVoList", articleTypeHomeTreeVoList);
            }
        }
        session.setAttribute("articleTypeHomeTreeVoList", articleTypeHomeTreeVoList);

        // 获取热门文章(定时任务中根据访问量更新,其数量为常量HOT_ARTICLE_LIST_NUMBER)
        List<String> hotArticleIdList = redisCache.getCacheList("hotArticleIdList");
        List<Article> articleHotVoList = new ArrayList<>();
        if (CollUtil.isEmpty(hotArticleIdList) || hotArticleIdList.size() < 0) {
            String sql = "limit " + HomeConstant.HOT_ARTICLE_LIST_NUMBER;
            articleHotVoList = articleService.list(Wrappers.<Article>lambdaQuery()
                    .select(Article::getArticleId, Article::getArticleTitle)
                    .orderByDesc(Article::getArticleLookNums)
                    .last(sql));
            if (CollUtil.isNotEmpty(articleHotVoList)) {
                redisCache.setCacheList("articleHotVoList", articleHotVoList);
            }
        } else {
            for (String id : hotArticleIdList) {
                Article article = articleService.getOne(Wrappers.<Article>lambdaQuery()
                        .eq(Article::getArticleId, id)
                        .select(Article::getArticleId, Article::getArticleTitle));
                articleHotVoList.add(article);
            }
        }
        redisCache.setCacheList("articleHotVoList", articleHotVoList);
        session.setAttribute("articleHotVoList", articleHotVoList);

        // 获取热门标签
        List<ArticleTag> articleTagList = redisCache.getCacheList("articleTagList");
        if (CollUtil.isEmpty(articleTagList)) {
            articleTagList = articleTagService.list(Wrappers.<ArticleTag>lambdaQuery()
                    .select(ArticleTag::getArticleTagId, ArticleTag::getArticleTagName));
            // 设置到Redis缓存中
            if (CollUtil.isNotEmpty(articleTagList)) {
                redisCache.setCacheList("articleTagList", articleTagList);
            }
        }
        session.setAttribute("articleTagList", articleTagList);

        // 获取首页广告
        List<Ad> adHomeList = redisCache.getCacheList("adHomeList");
        if (CollUtil.isEmpty(adHomeList)) {
            AdType homeAd = adTypeService.getOne(Wrappers.<AdType>lambdaQuery()
                    .eq(AdType::getAdTypeTag, "homeAd")
                    .select(AdType::getAdTypeId));
            if (Objects.nonNull(homeAd)) {
                Date date = DateUtil.date();
                adHomeList = adService.list(Wrappers.<Ad>lambdaQuery()
                        .eq(Ad::getAdTypeId, homeAd.getAdTypeId())
                        .lt(Ad::getAdBeginTime, date)
                        .gt(Ad::getAdEndTime, date)
                        .select(Ad::getAdId, Ad::getAdImgUrl, Ad::getAdLinkUrl, Ad::getAdTitle)
                        .orderByAsc(Ad::getAdSort));
                // 设置到Redis缓存中
                if (CollUtil.isNotEmpty(adHomeList)) {
                    redisCache.setCacheList("adHomeList", adHomeList);
                }
            }
        }
        session.setAttribute("adHomeList", adHomeList);

        // 获取最新文章
        List<IndexArticleVo> articleIndexList = redisCache.getCacheList("articleIndexList");
        if (CollUtil.isEmpty(articleIndexList)) {
            articleIndexList = articleService.getArticleIndexList();
            // 设置到Redis缓存中
            if (CollUtil.isNotEmpty(articleIndexList)) {
                redisCache.setCacheList("articleIndexList", articleIndexList);
            }
        }
        session.setAttribute("articleIndexList", articleIndexList);


        // 获取友联
        List<Link> linkList = redisCache.getCacheList("linkList");
        if (CollUtil.isEmpty(linkList)) {
            linkList = linkService.list(Wrappers.<Link>lambdaQuery()
                    .orderByAsc(Link::getLinkSort));
            // 设置到Redis缓存中
            if (CollUtil.isNotEmpty(linkList)) {
                redisCache.setCacheList("linkList", linkList);
            }
        }
        session.setAttribute("linkList", linkList);

        return "/index";
    }


    // 根据文章类型获取文章列表
    @GetMapping("articleList")
    @LogAnnotation(module = "首页", operator = "根据文章类型获取文章列表")
    public String articleList(HttpServletRequest request, @Valid ArticleListDTO articleListDTO, Model model) {
        // 获取文章列表
        IPage<IndexArticleVo> articleIPage = articleService.selectPageList(articleListDTO);
        request.getServletContext().setAttribute("articleIPageList", CommonPage.restPage(articleIPage));
        ArticleType articleType = articleTypeService.getById(articleListDTO.getArticleTypeId());
        // 获取文章一级类型
        request.getSession().setAttribute("articleTypeName", articleType.getArticleTypeName());
        model.addAttribute("articleTypeId", articleListDTO.getArticleTypeId());
        return "/home/articleList";

    }

    // 根据文章标签id获取文章列表
    @GetMapping("articleListByTagId")
    @LogAnnotation(module = "首页", operator = "根据文章标签id获取文章列表")
    public String articleListByTagId(HttpServletRequest request, @Valid ArticleListByTagPageDTO articleListByTagPageDTO, Model model) {
        // 获取文章列表
        IPage<IndexArticleVo> articleIPage = articleService.articleListByTagId(request, articleListByTagPageDTO);
        request.getServletContext().setAttribute("articleIPageList", CommonPage.restPage(articleIPage));
        ArticleTag articleTag = articleTagService.getById(articleListByTagPageDTO.getArticleTagId());
        // 获取文章一级类型
        request.getSession().setAttribute("articleTypeName", articleTag.getArticleTagName());
        model.addAttribute("articleTagId", articleListByTagPageDTO.getArticleTagId());
        return "/home/articleList";

    }

    // 文章点赞
    @PostMapping("articleAddGood")
    @ResponseBody
    @LogAnnotation(module = "文章", operator = "文章点赞")
    public Result articleAddGood(HttpServletRequest request, String articleId) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            return Result.failed("您还未登录！");
        }
        if (Objects.nonNull(session.getAttribute(articleId))) {
            return Result.failed("您已经点过赞了！");
        }
        // 更新文章点赞数
        Article article = articleService.getById(articleId);
        article.setArticleGoodNums(article.getArticleGoodNums() + 1);
        boolean update = articleService.updateById(article);
        if (update) {
            // 更新session
            session.setAttribute(articleId, true);
            // 统计同一个文章在一段时间内点赞数量（用于判断是否为热门文章）
            Map<String, Integer> articleGoodNumsInScheduledTime = redisCache.getCacheMap("articleGoodNumsInScheduledTime");
            Integer num = 0;
            if (CollUtil.isNotEmpty(articleGoodNumsInScheduledTime)) {
                num = articleGoodNumsInScheduledTime.get(articleId);
                if (Objects.isNull(num)) {
                    num = 0;
                }
            }
            articleGoodNumsInScheduledTime = new HashMap<>();
            articleGoodNumsInScheduledTime.put(articleId, ++num);
            redisCache.setCacheMap("articleGoodNumsInScheduledTime", articleGoodNumsInScheduledTime);
            redisCache.deleteObject("articleIndexList");
            return Result.OK("点赞成功！");
        }
        // 更新失败
        return Result.failed("点赞失败！");
    }

    // 文章收藏
    @PostMapping("articleAddCollection")
    @ResponseBody
    @LogAnnotation(module = "文章", operator = "文章收藏")
    public Result articleAddCollect(HttpServletRequest request, String articleId) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            return Result.failed("您还未登录！");
        }
        // 已登录
        // 更新文章收藏数
        int count = userCollectService.count(Wrappers.<UserCollect>lambdaQuery()
                .eq(UserCollect::getCollectArticleId, articleId)
                .eq(UserCollect::getUserId, user.getUserId()));
        if (count > 0) {
            return Result.failed("您已经收藏过了！");
        }
        UserCollect userCollect = new UserCollect();
        userCollect.setUserId(user.getUserId());
        userCollect.setCollectArticleId(articleId);
        userCollect.setCollectTime(new Date());
        if (!userCollectService.save(userCollect)) {
            return Result.failed("收藏失败！");
        }
        Article article = articleService.getById(articleId);
        article.setArticleCollectionNums(article.getArticleCollectionNums() + 1);
        if (!articleService.updateById(article)) {
            return Result.failed("收藏失败！");
        }
        // 统计同一个文章在一段时间内收藏数量（用于判断是否为热门文章）
        Map<String, Integer> articleCollectNumsInScheduledTime = redisCache.getCacheMap("articleCollectNumsInScheduledTime");
        Integer num = 0;
        if (CollUtil.isNotEmpty(articleCollectNumsInScheduledTime)) {
            num = articleCollectNumsInScheduledTime.get(articleId);
            if (Objects.isNull(num)) {
                num = 0;
            }
        }
        articleCollectNumsInScheduledTime = new HashMap<>();
        articleCollectNumsInScheduledTime.put(articleId, ++num);
        redisCache.setCacheMap("articleCollectNumsInScheduledTime", articleCollectNumsInScheduledTime);
        redisCache.deleteObject("articleIndexList");
        return Result.OK("收藏成功！");
    }

    // 文章搜索
    @GetMapping("searchArticleByTypeOrTitle")
    @LogAnnotation(module = "首页", operator = "文章搜索")
    public String searchArticleByTypeOrTitle(ArticleTopSearchPageDTO articleTopSearchPageDTO, HttpServletRequest request, Model model) {
        String keyword = articleTopSearchPageDTO.getKeyword();
        if (StrUtil.isBlank(keyword)) {
            return "/";
        }
        articleTopSearchPageDTO.setKeyword(StrUtil.trim(keyword));
        model.addAttribute("keyword", keyword);
        String ip = CommonUtils.getClientIpAddress(request);
        HashMap<String, Long> topSearchMap = (HashMap<String, Long>) request.getServletContext().getAttribute("topSearchMap");
        if (Objects.nonNull(topSearchMap)) {
            Long seconds = topSearchMap.get(ip);
            if (Objects.nonNull(seconds) && (seconds + 1) > DateUtil.currentSeconds()) {
                return "/home/searchError";
            }
        } else {
            topSearchMap = new HashMap<>();
            topSearchMap.put(ip, DateUtil.currentSeconds());
        }

        // 拆分搜索词
        List<Word> words = WordSegmenter.seg(keyword);
        List<String> titleList = words.stream().map(Word::getText).collect(Collectors.toList());
        titleList.add(keyword);
        List<String> articleTagIdList = articleTagService.list(Wrappers.<ArticleTag>lambdaQuery()
                .in(ArticleTag::getArticleTagName, titleList)
                .select(ArticleTag::getArticleTagId)).stream().map(ArticleTag::getArticleTagId).collect(Collectors.toList());
        List<String> articleIdList = new ArrayList<>();
        if (CollUtil.isNotEmpty(articleTagIdList)) {
            articleIdList = articleTagListService.list(Wrappers.<ArticleTagList>lambdaQuery()
                    .in(ArticleTagList::getArticleTagId, articleTagIdList)
                    .select(ArticleTagList::getArticleId)).stream().map(ArticleTagList::getArticleId).collect(Collectors.toList());
        }

        //分页查询
        Page<Article> articlePage = new Page<>(articleTopSearchPageDTO.getPageNum(), articleTopSearchPageDTO.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.<Article>lambdaQuery()
                .like(Article::getArticleTitle, keyword)
                .select(Article::getArticleId,
                        Article::getArticleTitle,
                        Article::getArticleCoverUrl,
                        Article::getArticleCollectionNums,
                        Article::getArticleGoodNums,
                        Article::getArticleLookNums,
                        Article::getArticleAddTime);

        if (CollUtil.isNotEmpty(articleIdList)) {
            queryWrapper.or().in(Article::getArticleId, articleIdList);
        }

        IPage<Article> articleIPage = articleService.page(articlePage, queryWrapper);
        model.addAttribute("articleIPageList", CommonPage.restPage(articleIPage));

        // 保持搜索时间
        topSearchMap.put(ip, DateUtil.currentSeconds());
        request.getServletContext().setAttribute("topSearchMap", topSearchMap);

        // 获取面包屑二级
        request.getSession().setAttribute("articleTypeName", keyword);
        return "/home/articleList";
    }


    // 文章评论点赞
    @PostMapping("goodComment")
    @ResponseBody
    @LogAnnotation(module = "文章", operator = "文章评论点赞")
    public Result goodComment(String commentId, HttpServletRequest request) {
        if (StrUtil.isBlank(commentId)) {
            return Result.failed("页面数据出现错误，请刷新重试！");
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            return Result.failed("请先登录！");
        }
        HashMap<String, Long> goodCommentMap = (HashMap<String, Long>) session.getAttribute("goodCommentMap");
        if (Objects.nonNull(goodCommentMap)) {
            Long goodTime = goodCommentMap.get(commentId);
            if (Objects.nonNull(goodTime)) {
                if ((goodTime + 3600) > DateUtil.currentSeconds()) {
                    return Result.failed("你已经点赞了！");
                }
            }
        } else {
            goodCommentMap = new HashMap<>();
        }
        Comment comment = commentService.getById(commentId);
        if (Objects.isNull(comment)) {
            return Result.failed("页面数据出现错误，请刷新重试！");
        }
        comment.setCommentGoodNums(comment.getCommentGoodNums() + 1);
        if (commentService.updateById(comment)) {
            goodCommentMap.put(commentId, DateUtil.currentSeconds());
            session.setAttribute("goodCommentMap", goodCommentMap);
            return Result.OK("点赞成功！");
        }
        return Result.failed("点赞失败！");
    }
}
