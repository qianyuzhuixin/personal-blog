package com.xiaoyang.dto.article;

import com.xiaoyang.dto.base.BasePageDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 文章展示列表
 * @Author: xiaomei
 * @Date: 2024/2/8 008
 */
@Data
public class ArticleListDTO extends BasePageDto {
    // 文章类型id
    @NotBlank(message = "页面出错，请刷新重试！")
    private String articleTypeId;
}
