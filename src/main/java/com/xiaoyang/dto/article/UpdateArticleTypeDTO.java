package com.xiaoyang.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description: 添加或修改文章类型数据接收类
 * @Author: xiaomei
 * @Date: 2023/11/27 027
 */
@Data
public class UpdateArticleTypeDTO {

    private String articleTypeId;

    private String articleTypeName;

    private Integer articleTypeSort;

    private String articleTypeParentId;

}
