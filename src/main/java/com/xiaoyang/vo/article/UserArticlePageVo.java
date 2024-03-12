package com.xiaoyang.vo.article;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 用户页面文章展示
 * @Author: xiaomei
 * @Date: 2024/2/3 003
 */
@Data
public class UserArticlePageVo {
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

    // 添加时间
    private Date articleAddTime;

}
