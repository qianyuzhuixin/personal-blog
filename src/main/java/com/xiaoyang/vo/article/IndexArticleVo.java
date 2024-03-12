package com.xiaoyang.vo.article;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 首页最新文章Vo
 * @Author: xiaomei
 * @Date: 2024/2/6 006
 */
@Data
public class IndexArticleVo {

    // 文章id
    private String articleId;

    // 文章标题
    private String articleTitle;

    // 文章类型
    private String articleTypeName;

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
