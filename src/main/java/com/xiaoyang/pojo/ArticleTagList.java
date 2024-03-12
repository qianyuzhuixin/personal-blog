package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName article_tag_list
 */
@TableName(value ="article_tag_list")
@Data
public class ArticleTagList implements Serializable {
    @TableId(value = "article_tag_list_id")
    private String articleTagListId;

    private String articleId;

    private String articleTagId;

    private static final long serialVersionUID = 1L;
}