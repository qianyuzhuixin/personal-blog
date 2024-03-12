package com.xiaoyang.vo.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * @Description: 文章类型vo
 * @Author: xiaomei
 * @Date: 2023/11/18 018
 */
@TableName(value = "article_type")
@Data
public class ArticleTypeVo {
    @TableId(value = "article_type_id")
    private String articleTypeId;

    private String articleTypeName;

    private Date articleTypeAddTime;

    private Integer articleTypeSort;

    @TableField(exist = false)
    private Integer articleNum;

    private Integer articleTypeLevel;

    private String articleTypeParentId;

    private static final long serialVersionUID = 1L;
}