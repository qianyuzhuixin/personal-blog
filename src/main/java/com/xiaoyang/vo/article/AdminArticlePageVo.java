package com.xiaoyang.vo.article;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 管理员文章分页Vo
 * @Author: xiaomei
 * @Date: 2023/11/28 028
 */
@Data
public class AdminArticlePageVo {

    // 文章id
    private String articleId;

    // 文章标题
    private String articleTitle;

    // 添加时间
    private Date articleAddTime;

    // 点赞数
    private Integer articleGoodNums;

    // 观看数
    private Integer articleLookNums;

    // 收藏数
    private Integer articleCollectionNums;

    // 用户id
    private String userId;

    // 用户名
    private String userName;

    // 文章类型id
    private String articleTypeId;

    // 文章类型名称
    private String articleTypeName;
}
