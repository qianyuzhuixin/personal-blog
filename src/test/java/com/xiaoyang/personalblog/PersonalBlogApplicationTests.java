package com.xiaoyang.personalblog;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.crypto.SecureUtil;
import com.xiaoyang.pojo.*;
import com.xiaoyang.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
class PersonalBlogApplicationTests {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleTypeService articleTypeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleTagListService articleTagListService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private AdService adService;

    @Autowired
    private AdTypeService adTypeService;


    @Test
    void contextLoads() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.setUserName(i + "uName");
            user.setUserPassword(SecureUtil.md5("123456"));
            user.setUserFrozen(0);
            user.setUserRegisterTime(DateUtil.date());
            users.add(user);
        }
        userService.saveBatch(users);
    }

    @Test
    public void addArticleData() {
        List<ArticleType> articleTypeList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArticleType articleType = new ArticleType();
            articleType.setArticleTypeName("文章分类" + i);
            articleType.setArticleTypeSort(i);
            articleType.setArticleTypeAddTime(DateUtil.date());
            articleTypeList.add(articleType);
        }
        articleTypeService.saveBatch(articleTypeList);

        List<User> userList = userService.list();
        List<Article> articleList = new ArrayList<>();
        for (ArticleType articleType : articleTypeList) {
            for (int i = 0; i < 6; i++) {
                Article article = new Article();
                article.setUserId(userList.get(ThreadLocalRandom.current().nextInt(userList.size())).getUserId());
                article.setArticleTitle("文章标题" + i);
                article.setArticleContent("文章内容" + ThreadLocalRandom.current().nextInt(1000));
                article.setArticleAddTime(DateUtil.date());
                article.setArticleTypeId(articleType.getArticleTypeId());
                article.setArticleGoodNums(0);
                article.setArticleLookNums(0);
                article.setArticleCollectionNums(0);
                articleList.add(article);
            }
        }
        articleService.saveBatch(articleList, 50);

        List<ArticleTag> articleTags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleTagName("标签" + i);
            articleTag.setArticleTagAddTime(DateUtil.date());
            articleTags.add(articleTag);
        }
        articleTagService.saveBatch(articleTags, 50);

        List<ArticleTagList> articleTagLists = new ArrayList<>();

        for (ArticleTag articleTag : articleTags) {
            for (int i = 0; i < 3; i++) {
                ArticleTagList articleTagList = new ArticleTagList();
                articleTagList.setArticleId(articleList.get(ThreadLocalRandom.current().nextInt(articleList.size())).getArticleId());
                articleTagList.setArticleTagId(articleTag.getArticleTagId());
                articleTagLists.add(articleTagList);
            }
        }

        articleTagListService.saveBatch(articleTagLists, 50);
    }


    @Test
    public void addLink() {
        List<Link> links = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Link link = new Link();
            link.setLinkUrl("https://www.baidu.com");
            link.setLinkTitle("百度" + i);
            link.setLinkLogoUrl("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
            link.setLinkAddTime(DateUtil.date());
            link.setLinkSort(10);
            links.add(link);
        }
        linkService.saveBatch(links);
    }

    @Test
    public void addAd() {
        ArrayList<AdType> adTypes = new ArrayList<>();
        AdType adType1 = new AdType();
        adType1.setAdTypeName("首页轮播图广告");
        adType1.setAdTypeTag("homeAd");
        adType1.setAdTypeAddTime(DateUtil.date());
        adType1.setAdTypeSort(1);
        adTypes.add(adType1);
        AdType adType2 = new AdType();
        adType2.setAdTypeName("文章页面广告");
        adType2.setAdTypeTag("articleAd");
        adType2.setAdTypeAddTime(DateUtil.date());
        adType2.setAdTypeSort(1);
        adTypes.add(adType2);
        adTypeService.saveBatch(adTypes);

        //https://picdm.sunbangyan.cn/2023/12/08/e7b8e1d2ccd56b4663a87a68cc5d9486.jpeg
        //https://picss.sunbangyan.cn/2023/12/08/4429aa98280200392be649abfb4912e3.jpeg
        //https://picss.sunbangyan.cn/2023/12/08/b69b3ed9dafa2a4c6d34e057ef77a637.jpeg
        //https://picst.sunbangyan.cn/2023/12/08/faee9e5ecf79f6c384432a776229d825.jpeg
        Ad ad1 = new Ad();
        ad1.setAdAddTime(DateUtil.date());
        ad1.setAdEndTime(DateUtil.parseDateTime("2025-06-06 12:12:12"));
        ad1.setAdTitle("广告1");
        ad1.setAdSort(10);
        ad1.setAdTypeId(adType1.getAdTypeId());
        ad1.setAdImgUrl("https://picdm.sunbangyan.cn/2023/12/08/e7b8e1d2ccd56b4663a87a68cc5d9486.jpeg");
        ad1.setAdBeginTime(DateUtil.date());

        Ad ad2 = new Ad();
        ad2.setAdAddTime(DateUtil.date());
        ad2.setAdEndTime(DateUtil.parseDateTime("2025-06-06 12:12:12"));
        ad2.setAdTitle("广告2");
        ad2.setAdSort(10);
        ad2.setAdTypeId(adType1.getAdTypeId());
        ad2.setAdImgUrl("https://picss.sunbangyan.cn/2023/12/08/4429aa98280200392be649abfb4912e3.jpeg");
        ad2.setAdBeginTime(DateUtil.date());

        Ad ad3 = new Ad();
        ad3.setAdAddTime(DateUtil.date());
        ad3.setAdEndTime(DateUtil.parseDateTime("2025-06-06 12:12:12"));
        ad3.setAdTitle("广告3");
        ad3.setAdSort(10);
        ad3.setAdTypeId(adType2.getAdTypeId());
        ad3.setAdImgUrl("https://picss.sunbangyan.cn/2023/12/08/b69b3ed9dafa2a4c6d34e057ef77a637.jpeg");
        ad3.setAdBeginTime(DateUtil.date());

        Ad ad0 = new Ad();
        ad0.setAdAddTime(DateUtil.date());
        ad0.setAdEndTime(DateUtil.parseDateTime("2025-06-06 12:12:12"));
        ad0.setAdTitle("广告0");
        ad0.setAdSort(10);
        ad0.setAdTypeId(adType2.getAdTypeId());
        ad0.setAdImgUrl("https://picst.sunbangyan.cn/2023/12/08/faee9e5ecf79f6c384432a776229d825.jpeg");
        ad0.setAdBeginTime(DateUtil.date());

        adService.save(ad0);
        adService.save(ad1);
        adService.save(ad2);
        adService.save(ad3);

    }

    @Test
    public void addAdmin() {
        Admin admin = new Admin();
        admin.setAdminName("admin");
        admin.setAdminPassword(SecureUtil.md5(admin.getAdminName() + "admin"));
        adminService.save(admin);
    }
}
