package com.xiaoyang.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.dto.article.PublishArticleActionDTO;
import com.xiaoyang.dto.article.UserCollectDTO;
import com.xiaoyang.dto.base.BasePageDto;
import com.xiaoyang.dto.base.CommonPage;
import com.xiaoyang.mapper.CommentMapper;
import com.xiaoyang.pojo.*;
import com.xiaoyang.service.*;
import com.xiaoyang.utils.CommonUtils;
import com.xiaoyang.utils.Result;
import com.xiaoyang.utils.ResultCodeEnum;
import com.xiaoyang.vo.article.ShowArticleVo;
import com.xiaoyang.vo.article.UserArticlePageVo;
import com.xiaoyang.vo.article.UserCollectArticlePageVo;
import com.xiaoyang.vo.comment.CommentVo;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: 用户控制类
 * @Author: xiaomei
 * @Date: 2023/11/12 012
 */
@Controller
@RequestMapping("user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCollectService userCollectService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTypeService articleTypeService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private ArticleTagListService articleTagListService;

    @Autowired
    private CommentService commentService;

    private Logger log;


    // 注册
    @PostMapping("signIn")
    @ResponseBody
    public Result signIn(@Length(min = 3, max = 25, message = "用户名长度为3-25个字符") @RequestParam("userName") String userName,
                         @Length(min = 6, max = 25, message = "密码长度为6-25个字符") @RequestParam("password") String password, String verifyCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (StrUtil.isBlank(verifyCode) || !verifyCode.equals(session.getAttribute("circleCaptchaCode"))) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("验证码不正确");
        }
        if (userName.equals(password)) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("用户名不能与密码相同");
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(SecureUtil.md5(userName + password));
        user.setUserRegisterTime(DateUtil.date());
        User getUser = userService.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserName, userName));
        if (!Objects.isNull(getUser)) {
            return Result.failed("用户名已存在");
        }
        if (userService.save(user)) {
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }
        return Result.failed("注册失败");
    }

    // 中间跳转
    @GetMapping("logInOrSignIn")
    public String login(HttpServletRequest request, Model model) {
        if (Objects.nonNull(request.getSession().getAttribute("user"))) {
            return "redirect:/";
        }

        model.addAttribute("referer", request.getHeader("referer"));
        return "/user/logInOrSignIn";
    }

    // 退出
    @GetMapping("logOut")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/user/logInOrSignIn";
    }

    // 登录
    @PostMapping("login")
    @ResponseBody
    public Result adminLogin(@Length(min = 3, max = 25, message = "用户名长度为3-25个字符") @RequestParam("userName") String userName,
                             @Length(min = 6, max = 25, message = "密码长度为6-25个字符") @RequestParam("password") String password, String verifyCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (StrUtil.isBlank(verifyCode) || !verifyCode.equals(session.getAttribute("circleCaptchaCode"))) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("验证码不正确");
        }
        if (userName.equals(password)) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("用户名不能与密码相同");
        }
        User user = userService.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserName, userName)
                .eq(User::getUserPassword, SecureUtil.md5(userName + password)), false);
        if (Objects.isNull(user)) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("用户名或密码不正确");
        }
        if (user.getUserFrozen() == 1) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("该用户已被冻结，请联系管理员");
        }
        session.setAttribute("user", user);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }


    // 发布文章
    @GetMapping("publishArticle")
    public String publishArticle(HttpServletRequest request, Model model, String articleId) {
        User user = (User) request.getSession().getAttribute("user");
        // 如果没有写作权限就跳转到首页
        if (Objects.isNull(user) || Objects.isNull(user.getUserWrite()) || user.getUserWrite() != 0) {
            return "redirect:/";
        }

        // 有文章id就是修改
        // 获取修改文章
        Article article = articleService.getOne(Wrappers.<Article>lambdaQuery()
                .eq(Article::getArticleId, articleId)
                .eq(Article::getUserId, user.getUserId()), false);
        if (Objects.nonNull(article)) {
            model.addAttribute("article", article);
            // 获取对应文章的所有标签
            List<ArticleTagList> list = articleTagListService.list(Wrappers.<ArticleTagList>lambdaQuery()
                    .eq(ArticleTagList::getArticleId, articleId)
                    .select(ArticleTagList::getArticleTagId));

            if (CollUtil.isNotEmpty(list)) {
                List<String> articleTagIds = list.stream().map(ArticleTagList::getArticleTagId).collect(Collectors.toList());
                model.addAttribute("articleTagIds", articleTagIds);
            }


            // 获取同一一级类型下的二级类型
            ArticleType articleType = articleTypeService.getOne(
                    Wrappers.<ArticleType>lambdaQuery()
                            .eq(ArticleType::getArticleTypeId, article.getArticleTypeId()));
            List<ArticleType> articleTypes = articleTypeService.list(Wrappers.<ArticleType>lambdaQuery()
                    .eq(ArticleType::getArticleTypeParentId, articleType.getArticleTypeParentId()));
            model.addAttribute("articleTypes", articleTypes);

            // 获取一级文章类型
            model.addAttribute("articleParentType", articleTypeService.getOne(Wrappers.<ArticleType>lambdaQuery().eq(ArticleType::getArticleTypeId, articleType.getArticleTypeParentId())));
        }


        // 获取类型
        List<ArticleType> articleType1List = articleTypeService.list(Wrappers.<ArticleType>lambdaQuery()
                .eq(ArticleType::getArticleTypeLevel, 1));
        model.addAttribute("articleType1List", articleType1List);

        // 获取文章标签
        List<ArticleTag> articleTagList = articleTagService.list();
        model.addAttribute("articleTagList", articleTagList);

        return "/user/publishArticle";
    }

    // 根据一级文章分类获取二级分类
    @PostMapping("getArticleTypeChild")
    @ResponseBody
    public Result getArticleTypeChild(String articleTypeId) {
        if (StrUtil.isBlank(articleTypeId)) {
            return Result.failed("请选择一级分类");
        }
        // 获取类型
        List<ArticleType> articleType2List = articleTypeService.list(Wrappers.<ArticleType>lambdaQuery()
                .eq(ArticleType::getArticleTypeLevel, 2)
                .eq(ArticleType::getArticleTypeParentId, articleTypeId)
                .select(ArticleType::getArticleTypeId, ArticleType::getArticleTypeName)
                .orderByAsc(ArticleType::getArticleTypeSort));
        return Result.OK(articleType2List);
    }


    // 编写文章时上传图片
    @PostMapping("uploadArticle")
    @ResponseBody
    public String uploadArticle(HttpServletRequest request, MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (Objects.isNull(user.getUserWrite()) || user.getUserWrite() != 0) {
            return null;
        }

        return uploadFileService.getUploadUrl(file);
    }

    // 获取编写文章的内容
    @PostMapping("publishArticleAction")
    @ResponseBody
    public Result publishArticleAction(@Valid PublishArticleActionDTO publishArticleActionDTO, HttpServletRequest request, MultipartFile articleCoverFile) {

        User user = (User) request.getSession().getAttribute("user");
        // 如果没有写作权限就跳转到首页
        if (Objects.isNull(user) || Objects.isNull(user.getUserWrite()) || user.getUserWrite() != 0) {
            return Result.failed("无权限发布，请联系管理员！");
        }
        if (Objects.nonNull(articleCoverFile)) {
            publishArticleActionDTO.setArticleCoverUrl(uploadFileService.getUploadUrl(articleCoverFile));
        }

        return articleService.publishArticle(publishArticleActionDTO, (User) request.getSession().getAttribute("user"));
    }

    // 个人中心-》我的资料
    @GetMapping("myInformation")
    public String myInformation(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "/user/myInformation";
    }

    // 个人中心-》我的文章
    @GetMapping("myArticles")
    public String myArticles(Integer pageNumber, Model model, HttpServletRequest request) {
        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1;
        }
        User user = (User) request.getSession().getAttribute("user");
        Page<UserArticlePageVo> articlePageVoPage = new Page<>(pageNumber, 15);
        IPage<UserArticlePageVo> articlePageVoIPage = articleService.myArticles(articlePageVoPage, user.getUserId());
        model.addAttribute("articlePageVoIPage", CommonPage.restPage(articlePageVoIPage));
        return "/user/myArticles";
    }

    // 个人中心-》我的收藏
    @GetMapping("myCollect")
    public String myArticles(Model model, HttpServletRequest request, UserCollectDTO userCollectDTO) {
        User user = (User) request.getSession().getAttribute("user");
        Page<UserCollectArticlePageVo> userCollectPage = new Page<>(userCollectDTO.getPageNum(), userCollectDTO.getPageSize());
        IPage<UserCollectArticlePageVo> userCollectPageLists = userCollectService.getUserCollects(userCollectPage, user.getUserId());
        model.addAttribute("userCollectPageLists", CommonPage.restPage(userCollectPageLists));
        return "/user/myCollect";
    }

    // 取消收藏
    @PostMapping("delCollect")
    @ResponseBody
    public Result delCollect(String articleId) {
        return userCollectService.delCollect(articleId);
    }


    // 展示文章内容
    @GetMapping("showArticle")
    public String showArticle(@RequestParam String articleId, Model model, HttpServletRequest request) {
        ShowArticleVo article = articleService.showArticle(articleId);
        HttpSession session = request.getSession();
        Integer articleLookNums = article.getArticleLookNums();
        if (Objects.isNull(session.getAttribute("lookArticle"))) {
            if (Objects.isNull(articleLookNums) || articleLookNums < 0) {
                articleLookNums = 0;
            }
            article.setArticleLookNums(articleLookNums + 1);
            articleService.updateArticleLookNums(article);
            session.setAttribute("lookArticle", true);
        }
        article.setUserName(CommonUtils.editMiddleStr(article.getUserName()));

        model.addAttribute("article", article);
        return "/user/showArticle";
    }

    // 接收用户评论
    @PostMapping("showComment")
    @ResponseBody
    public Result showComment(String articleId, Integer pageNumber, HttpServletRequest request) {
        if (StrUtil.isBlank(articleId)) {
            return Result.failed("未获取到文章id，请刷新页面重试！");
        }
        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1;
        }
        Page<CommentVo> commentVoPage = new Page<>(pageNumber, 5);
        IPage<CommentVo> commentVoIPage = commentService.showComment(commentVoPage, articleId);
        commentVoIPage.getRecords().stream().forEach(commentVo -> {
            commentVo.setUserName(CommonUtils.editMiddleStr(commentVo.getUserName()));
        });

        HashMap<String, Long> goodCommentMap = (HashMap<String, Long>) request.getSession().getAttribute("goodCommentMap");
        if (Objects.nonNull(goodCommentMap)) {
            List<String> commentIds = new ArrayList<>(goodCommentMap.keySet());
            commentVoIPage.getRecords().stream().forEach(commentVo -> {
                if (commentIds.contains(commentVo.getCommentId())) {
                    commentVo.setIsGood(1);
                }
            });

        }

        return Result.OK(CommonPage.restPage(commentVoIPage));
    }

    // 接收用户评论
    @PostMapping("showRecoverComment")
    @ResponseBody
    public Result showRecoverComment(String articleId, Integer pageNumber, HttpServletRequest request) {
        if (StrUtil.isBlank(articleId)) {
            return Result.failed("未获取到文章id，请刷新页面重试！");
        }
        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1;
        }
        Page<CommentVo> commentVoPage = new Page<>(pageNumber, 5);
        IPage<CommentVo> commentVoIPage = commentService.showComment(commentVoPage, articleId);
        commentVoIPage.getRecords().stream().forEach(commentVo -> {
            commentVo.setUserName(CommonUtils.editMiddleStr(commentVo.getUserName()));
        });

        HashMap<String, Long> goodCommentMap = (HashMap<String, Long>) request.getSession().getAttribute("goodCommentMap");
        if (Objects.nonNull(goodCommentMap)) {
            List<String> commentIds = new ArrayList<>(goodCommentMap.keySet());
            commentVoIPage.getRecords().stream().forEach(commentVo -> {
                if (commentIds.contains(commentVo.getCommentId())) {
                    commentVo.setIsGood(1);
                }
            });

        }

        return Result.OK(CommonPage.restPage(commentVoIPage));
    }

    // 删除文章
    @PostMapping("delArticle")
    @ResponseBody
    public Result delArticle(@RequestParam String articleId) {
        return articleService.delArticle(articleId);
    }


    // 添加评论
    @PostMapping("saveComment")
    @ResponseBody
    public Result saveComment(String articleId, String commentContext, HttpServletRequest request) {
        if (StrUtil.isBlank(articleId) || StrUtil.isBlank(commentContext)) {
            return Result.failed("页面出现错误，请刷新重试！");
        }
        if (commentContext.length() < 1 || commentContext.length() > 800) {
            return Result.failed("评论长度需在1~800之间！");
        }
        User user = (User) request.getSession().getAttribute("user");
        if (Objects.isNull(user)) {
            return Result.failed("登录已过期，请重新登录！");
        }
        Comment comment = commentService.getOne(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getUserId, user.getUserId())
                .select(Comment::getCommentTime)
                .orderByDesc(Comment::getCommentTime), false);
        if (Objects.nonNull(comment) && Objects.nonNull(comment.getCommentTime())) {
            if ((comment.getCommentTime().getTime() + 8000) > System.currentTimeMillis()) {
                return Result.failed("评论太快了，请稍后再试！");
            }

        }

        Comment newComment = new Comment();
        newComment.setArticleId(articleId);
        newComment.setUserId(user.getUserId());
        newComment.setCommentContext(commentContext);
        newComment.setCommentTime(DateUtil.date());
        newComment.setCommentGoodNums(0);

        if (commentService.save(newComment)) {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(newComment, commentVo);
            commentVo.setUserName(user.getUserName());

            return Result.OK(commentVo);
        }

        return Result.failed("评论失败！");
    }

}
