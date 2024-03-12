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
public class AddArticleTypeDTO {

    private String articleTypeId;

    @NotBlank(message = "文章分类名称 不能为空")
    private String articleTypeName;

    @NotNull(message = "文章分类排序 不能为空")
    private Integer articleTypeSort;

    private String articleTypeParentId;

    private Integer articleTypeLevel;

}
