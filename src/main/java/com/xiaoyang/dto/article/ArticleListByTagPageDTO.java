package com.xiaoyang.dto.article;

import com.xiaoyang.dto.base.BasePageDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 文章标签id获取文章列表分页
 * @Author: xiaomei
 * @Date: 2024/2/9 009
 */
@Data
public class ArticleListByTagPageDTO extends BasePageDto {
    @NotBlank(message = "页面出错，请刷新重试！")
    private String articleTagId;
}
