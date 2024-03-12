package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName article_tag
 */
@TableName(value ="article_tag")
@Data
public class ArticleTag implements Serializable {
    @TableId(value = "article_tag_id")
    private String articleTagId;

    private String articleTagName;

    private Date articleTagAddTime;

    private static final long serialVersionUID = 1L;
}