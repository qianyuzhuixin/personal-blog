package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName article_type
 */
@TableName(value = "article_type")
@Data
public class ArticleType implements Serializable {
    @TableId(value = "article_type_id")
    private String articleTypeId;

    private String articleTypeName;

    private Date articleTypeAddTime;

    private Integer articleTypeSort;

    private Integer articleTypeLevel;

    private String articleTypeParentId;

    private static final long serialVersionUID = 1L;
}