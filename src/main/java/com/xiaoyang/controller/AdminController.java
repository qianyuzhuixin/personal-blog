package com.xiaoyang.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.system.HostInfo;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.dto.article.AddArticleTypeDTO;
import com.xiaoyang.dto.article.ArticlePageDTO;
import com.xiaoyang.dto.article.UpdateArticleTypeDTO;
import com.xiaoyang.dto.user.UserDto;
import com.xiaoyang.dto.user.UserListPageDto;
import com.xiaoyang.pojo.*;
import com.xiaoyang.service.*;
import com.xiaoyang.dto.base.CommonPage;
import com.xiaoyang.utils.RedisCache;
import com.xiaoyang.utils.Result;
import com.xiaoyang.utils.ResultCodeEnum;
import com.xiaoyang.vo.ad.AdVo;
import com.xiaoyang.vo.article.AdminArticlePageVo;
import com.xiaoyang.vo.article.ArticleTypeVo;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 管理员控制类
 * @Author: xiaomei
 * @Date: 2023/11/11 011
 */
@Controller
@RequestMapping("xyadmin")
@Validated
public class AdminController {

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleTypeService articleTypeService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private UserService userService;

    @Resource
    private ArticleTagListService articleTagListService;

    @Resource
    private AdService adService;

    @Resource
    private LinkService linkService;

    @Resource
    private AdTypeService adTypeService;


    @Resource
    private AdminService adminService;


    @Autowired
    private RedisCache redisCache;

    // 中间跳转
    @GetMapping("login")
    public String login(HttpServletRequest request) {
        if (Objects.nonNull(request.getSession().getAttribute("admin"))) {
            return "redirect:/xyadmin/";
        }
        return "/admin/adminLogin";
    }

    // 管理员退出
    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return "redirect:/xyadmin/login";
    }

    // 管理员登录
    @PostMapping("/adminLogin")
    @ResponseBody
    public Result adminLogin(@Length(min = 3, max = 25, message = "用户名长度为3-25个字符") @RequestParam("adminName") String adminName,
                             @Length(min = 6, max = 25, message = "密码长度为6-25个字符") @RequestParam("adminPassword") String adminPassword, String verifyCode, HttpServletRequest request) {


        HttpSession session = request.getSession();
        if (StrUtil.isBlank(verifyCode) || !verifyCode.equals(session.getAttribute("circleCaptchaCode"))) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("验证码不正确");
        }
        Admin admin = adminService.getOne(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getAdminName, adminName)
                .eq(Admin::getAdminPassword, SecureUtil.md5(adminName + adminPassword)), false);
        if (Objects.isNull(admin)) {
            session.removeAttribute("circleCaptchaCode");
            return Result.failed("用户名或密码不正确");
        }
        session.setAttribute("admin", admin);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }

    // 修改密码
    @PostMapping("/changePassword")
    @ResponseBody
    public Result changePassword(@Length(min = 6, max = 25, message = "密码长度为6-25个字符") @RequestParam String newPassword, HttpServletRequest request) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        admin.setAdminPassword(SecureUtil.md5(admin.getAdminName() + newPassword));
        if (adminService.updateById(admin)) {
            request.getSession().removeAttribute("admin");
            return Result.OK("修改成功！");
        }

        return Result.failed("修改密码失败");

    }

    // 查看当前系统基本信息
    @GetMapping("")
    public String index(Model model) {
        //当前系统信息
        OsInfo osInfo = SystemUtil.getOsInfo();
        HostInfo hostInfo = SystemUtil.getHostInfo();
        model.addAttribute("osName", osInfo.getName());
        model.addAttribute("hostIP", hostInfo.getAddress());

        //当前文章数量
        model.addAttribute("articleNums", articleService.count());
        model.addAttribute("articleTagNums", articleTagService.count());
        model.addAttribute("articleTypeNums", articleTypeService.count());


        //当前用户数量
        model.addAttribute("userNums", userService.count());

        return "/admin/index";
    }


    // 查看所有用户信息
    @GetMapping("userList")
    public String userList(@Valid UserListPageDto userListPageDto, Model model) {
        Integer pageNum = userListPageDto.getPageNum();
        Integer pageSize = userListPageDto.getPageSize();

        String userName = userListPageDto.getUserName();

        //分页查询（默认第一页，每页10条）
        IPage userIPage = new Page<>(pageNum, pageSize, true);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(User::getUserRegisterTime);
        //根据用户名模糊查询
        if (StrUtil.isNotBlank(userName)) {
            lambdaQueryWrapper.like(User::getUserName, userName);
            model.addAttribute("userName", userName);
        }
        userService.page(userIPage, lambdaQueryWrapper);

        model.addAttribute("userList", CommonPage.restPage(userIPage));
        return "admin/userList";

    }

    // 删除用户
    @PostMapping("delUser")
    @ResponseBody
    public Result userDel(String userId) {
        if (StrUtil.isBlank(userId)) {
            return Result.build(null, ResultCodeEnum.ARGUMENT_ERROR);
        }
        if (articleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getUserId, userId)) > 0) {
            return Result.build(null, ResultCodeEnum.DEL_CASCADE_ERROR);
        }
        if (userService.removeById(userId)) {
            return Result.OK("删除成功！");
        }
        return Result.build(null, ResultCodeEnum.DEL_ERROR);
    }

    // 修改用户
    @PostMapping("updateUser")
    @ResponseBody
    public Result userUpdate(@Valid UserDto userDto) {
        User user = userService.getById(userDto.getUserId());
        if (user == null) {
            return Result.build(null, ResultCodeEnum.USER_NOT_EXIST);
        }
        Date userRegisterTime = user.getUserRegisterTime();
        String userPassword = userDto.getUserPassword();
        // 使用MD5将注册时间与用户名进行加密
        if (StrUtil.isNotBlank(userPassword)) {
            userDto.setUserPassword(SecureUtil.md5(userRegisterTime + userPassword));
        } else {
            userDto.setUserPassword(null);
        }
        BeanUtils.copyProperties(userDto, user);

        if (userService.updateById(user)) {
            return Result.OK("更新成功！");
        }
        return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
    }

    // 查看所有文章类型
    @GetMapping("articleTypeList")
    public String articleTypeList(Model model, String articleTypeParentId) {
        List<ArticleTypeVo> articleTypeVoList = articleTypeService.articleTypeList();
        model.addAttribute("articleTypeVoList", articleTypeVoList);
        List<ArticleTypeVo> sonArticleTypeVoList = articleTypeService.getSonArticleType(articleTypeParentId);
        model.addAttribute("sonArticleTypeVoList", sonArticleTypeVoList);
        return "admin/articleTypeList";
    }


    // 添加文章类型
    @PostMapping("addArticleType")
    @ResponseBody
    public Result addArticleType(@Valid AddArticleTypeDTO addArticleTypeDTO) {
        ArticleType articleType = new ArticleType();
        BeanUtils.copyProperties(addArticleTypeDTO, articleType);
        articleType.setArticleTypeAddTime(DateUtil.date());
        if (StrUtil.isNotBlank(articleType.getArticleTypeParentId())) {
            articleType.setArticleTypeLevel(2);
        }
        boolean save = articleTypeService.save(articleType);
        if (!save) {
            return Result.build(null, ResultCodeEnum.INSERT_ERROR);
        }
        redisCache.deleteObject("articleTypeHomeTreeVoList");
        return Result.OK("添加成功!");
    }

    // 修改文章类型
    @PostMapping("updateArticleType")
    @ResponseBody
    public Result updateArticleType(UpdateArticleTypeDTO updateArticleTypeDTO) {
        ArticleType articleType = new ArticleType();
        BeanUtils.copyProperties(updateArticleTypeDTO, articleType);
        if (!StrUtil.isNotBlank(updateArticleTypeDTO.getArticleTypeName())) {
            articleType.setArticleTypeName(null);
        }
        if (Objects.isNull(updateArticleTypeDTO.getArticleTypeSort())) {
            articleType.setArticleTypeSort(null);
        }
        boolean updateById = articleTypeService.updateById(articleType);
        if (!updateById) {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
        redisCache.deleteObject("articleTypeHomeTreeVoList");
        return Result.OK("更新成功！");
    }

    // 删除文章类型
    @PostMapping("delArticleType")
    @ResponseBody
    public Result delArticleType(@NotBlank(message = "文章类型id 不能为空") String articleTypeId) {
        int count = articleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getArticleTypeId, articleTypeId));
        if (count > 0) {
            return Result.build(null, ResultCodeEnum.DEL_CASCADE_ERROR);
        }
        ArticleType articleType = articleTypeService.getOne(Wrappers.<ArticleType>lambdaQuery().eq(ArticleType::getArticleTypeId, articleTypeId));
        boolean removeById = articleTypeService.removeById(articleTypeId);
        if (!removeById) {
            return Result.build(null, ResultCodeEnum.DEL_ERROR);
        }
        redisCache.deleteObject("articleTypeHomeTreeVoList");
        if (articleType.getArticleTypeLevel() == 1) {
            LambdaUpdateWrapper<ArticleType> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(ArticleType::getArticleTypeParentId, null);
            wrapper.eq(ArticleType::getArticleTypeParentId, articleTypeId);
            articleTypeService.update(wrapper);
        }

        return Result.OK("删除成功！");
    }


    // 查看所有文章标签
    @GetMapping("articleTagList")
    public String articleTageList(Model model) {
        List<ArticleTag> articleTagList = articleTagService.list(Wrappers.<ArticleTag>lambdaQuery().orderByDesc(ArticleTag::getArticleTagAddTime));
        model.addAttribute("articleTagList", articleTagList);
        return "admin/articleTagList";
    }

    // 添加文章标签
    @PostMapping("addOrUpdateArticleTag")
    @ResponseBody
    public Result addOrUpdateArticleTag(ArticleTag articleTag) {
        String articleTagId = articleTag.getArticleTagId();
        if (StrUtil.isNotBlank(articleTagId)) {
            if (articleTagService.updateById(articleTag)) {
                redisCache.deleteObject("articleTagList");
                return Result.OK("更新成功！");
            }
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }

        articleTag.setArticleTagAddTime(DateUtil.date());
        if (articleTagService.save(articleTag)) {
            redisCache.deleteObject("articleTagList");
            return Result.OK("添加成功！");
        }
        return Result.build(null, ResultCodeEnum.INSERT_ERROR);
    }

    // 删除文章标签
    @PostMapping("delArticleTag")
    @ResponseBody
    public Result delArticleTag(@NotBlank(message = "文章标签id 不能为空") String articleTagId) {
        if (!StrUtil.isNotBlank(articleTagId)) {
            return Result.build(null, ResultCodeEnum.ARGUMENT_ERROR);
        }
        if (articleTagListService.count(Wrappers.<ArticleTagList>lambdaQuery()
                .eq(ArticleTagList::getArticleTagId, articleTagId)) > 0) {
            return Result.build(null, ResultCodeEnum.DEL_CASCADE_ERROR);
        }
        if (articleTagService.removeById(articleTagId)) {
            redisCache.deleteObject("articleTagList");
            return Result.OK("删除成功！");
        }
        return Result.build(null, ResultCodeEnum.DEL_ERROR);
    }

    // 查询所有文章
    @GetMapping("articleList")
    public String articleList(@Valid ArticlePageDTO articlePageDTO, Model model) {
        IPage<AdminArticlePageVo> articlePage = new Page<>(articlePageDTO.getPageNum(), articlePageDTO.getPageSize());
        IPage<AdminArticlePageVo> articlePageVoList = articleService.articleList(articlePage, articlePageDTO.getArticleTitle());
        if (StrUtil.isNotBlank(articlePageDTO.getArticleTitle())) {
            model.addAttribute("articleTitle", articlePageDTO.getArticleTitle());
        }
        model.addAttribute("articlePageVoList", CommonPage.restPage(articlePageVoList));
        return "/admin/articleList";
    }

    // 删除文章
    @PostMapping("delArticle")
    @ResponseBody
    public Result delArticle(String articleId) {
        if (articleService.delArticle(articleId).getCode() == 200) {
            return Result.OK("删除成功！");
        }
        return Result.build(null, ResultCodeEnum.DEL_ERROR);
    }


    // 查询所有友情链接
    @GetMapping("linkList")
    public String linkList(Model model) {
        List<Link> linkList = linkService.list(Wrappers.<Link>lambdaQuery().orderByAsc(Link::getLinkSort));
        model.addAttribute("linkList", linkList);
        return "/admin/linkList";
    }

    // 添加或修改友情链接
    @PostMapping("addOrUpdateLink")
    @ResponseBody
    public Result addOrUpdateLink(Link link) {
        String linkId = link.getLinkId();
        if (!StrUtil.isNotBlank(linkId)) {
            // 添加友情链接
            link.setLinkAddTime(DateUtil.date());
            if (linkService.save(link)) {
                redisCache.deleteObject("linkList");
                return Result.OK("添加成功！");
            }
            return Result.build(null, ResultCodeEnum.INSERT_ERROR);
        }

        if (linkService.updateById(link)) {
            redisCache.deleteObject("linkList");
            return Result.OK("更新成功！");
        }
        return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
    }

    // 删除友链
    @PostMapping("delLink")
    @ResponseBody
    public Result delLink(String linkId) {
        if (linkService.removeById(linkId)) {
            redisCache.deleteObject("linkList");
            return Result.OK("删除成功！");
        }
        return Result.build(null, ResultCodeEnum.DEL_ERROR);
    }


    // 广告管理
    // 获取广告列表
    @GetMapping("adList")
    public String adList(String adTypeId, Model model) {
        List<AdType> adTypeList = adTypeService.list(Wrappers.<AdType>lambdaQuery()
                .orderByAsc(AdType::getAdTypeSort));
        model.addAttribute("adTypeList", adTypeList);
        List<AdVo> adVoList = adService.getAdList(adTypeId);
        model.addAttribute("adVoList", adVoList);
        return "/admin/adList";
    }

    // 添加或更新广告类型
    @PostMapping("addOrUpdateAdType")
    @ResponseBody
    public Result addOrUpdateAdType(AdType adType) {
        if (StrUtil.isBlank(adType.getAdTypeId())) {
            adType.setAdTypeAddTime(DateUtil.date());
            if (adTypeService.save(adType)) {
                return Result.OK("添加成功！");
            }
            return Result.build(null, ResultCodeEnum.INSERT_ERROR);
        }

        // 修改广告类型
        if (adTypeService.updateById(adType)) {
            return Result.OK("修改成功！");
        }
        return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
    }


    // 添加或更新广告
    @PostMapping("addOrUpdateAd")
    @ResponseBody
    public Result addOrUpdateAd(HttpServletRequest request, Ad ad) {
        redisCache.deleteObject("adHomeList");
        if (StrUtil.isBlank(ad.getAdId())) {
            ad.setAdAddTime(DateUtil.date());
            if (adService.save(ad)) {
                return Result.OK("添加成功！");
            }
            return Result.build(null, ResultCodeEnum.INSERT_ERROR);
        }


        // 修改广告类型
        if (adService.updateById(ad)) {
            return Result.OK("更新成功！");
        }
        return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
    }

    // 删除广告
    @PostMapping("delAd")
    @ResponseBody
    public Result delAd(String adId) {
        if (adService.removeById(adId)) {
            return Result.OK("删除成功！");
        }
        return Result.build(null, ResultCodeEnum.DEL_ERROR);
    }
}
