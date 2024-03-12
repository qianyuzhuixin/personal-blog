package com.xiaoyang.vo.article;

import com.xiaoyang.pojo.Article;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 文章收藏
 * @Author: xiaomei
 * @Date: 2024/1/13 013
 */
@Data
public class UserCollectArticlePageVo {
    // 文章id
    private String articleId;

    // 文章标题
    private String articleTitle;

    // 文章类型
    private String articleTypeName;

    // 文章类型id
    private String articleTypeId;

    // 文章封面
    private String articleCoverUrl;

    // 观看数
    private Integer articleLookNums;

    // 文章点赞数
    private Integer articleGoodNums;

    // 文章收藏数
    private Integer articleCollectionNums;

    // 添加时间
    private Date articleAddTime;

}
