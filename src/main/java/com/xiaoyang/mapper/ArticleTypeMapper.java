package com.xiaoyang.mapper;

import com.xiaoyang.pojo.ArticleType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyang.vo.article.ArticleTypeVo;

import java.util.List;

/**
 * @author xiaomei
 * @description 针对表【article_type(文章类型)】的数据库操作Mapper
 * @createDate 2023-11-11 11:27:56
 * @Entity com.xiaoyang.pojo.ArticleType
 */
public interface ArticleTypeMapper extends BaseMapper<ArticleType> {


    List<ArticleTypeVo> getSonArticleType(String articleTypeParentId);

    List<ArticleTypeVo> getArticleTypeList();
}




