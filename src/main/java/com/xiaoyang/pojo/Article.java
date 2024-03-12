package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName article
 */
@TableName(value = "article")
@Data
public class Article implements Serializable {
    // 文章id
    @TableId(value = "article_id")
    private String articleId;

    // 文章封面url
    private String articleCoverUrl;

    // 文章标题
    private String articleTitle;

    // 文章添加时间
    private Date articleAddTime;

    // 文章内容
    private String articleContent;

    // 文章是否为热门文章，0不是，1是
    private Integer articleIsHot;

    // 文章点赞数
    private Integer articleGoodNums;

    // 文章浏览数
    private Integer articleLookNums;

    // 文章收藏数
    private Integer articleCollectionNums;

    // 作者id
    private String userId;

    // 文章类型id
    private String articleTypeId;

    private static final long serialVersionUID = 1L;
}