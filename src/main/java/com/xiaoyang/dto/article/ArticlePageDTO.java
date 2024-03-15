package com.xiaoyang.dto.article;

import com.xiaoyang.dto.base.BasePageDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 文章分页
 * @Author: xiaomei
 * @Date: 2023/11/28 028
 */
@Data
public class ArticlePageDTO extends BasePageDto {
    /**
     * 文章标题
     */
    private String articleTitle;
}
