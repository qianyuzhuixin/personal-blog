package com.xiaoyang.dto.article;

import com.xiaoyang.dto.base.BasePageDto;
import lombok.Data;

/**
 * @Description: 顶部搜索栏搜索文章
 * @Author: xiaomei
 * @Date: 2024/2/9 009
 */
@Data
public class ArticleTopSearchPageDTO extends BasePageDto {
    private String keyword;
}
