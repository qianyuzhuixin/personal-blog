package com.xiaoyang.vo.article;

import com.xiaoyang.pojo.ArticleType;
import lombok.Data;

import java.util.List;

/**
 * @Description: 首页文章类型Vo
 * @Author: xiaomei
 * @Date: 2024/2/6 006
 */
@Data
public class ArticleTypeHomeTreeVo {
    // 文章类型id
    private String articleTypeId;
    // 文章类型名称
    private String articleTypeName;
    // 下级文章类型集合
    private List<ArticleType> articleChildList;
}
