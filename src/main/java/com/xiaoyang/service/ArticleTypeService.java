package com.xiaoyang.service;

import com.xiaoyang.pojo.ArticleType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyang.vo.article.ArticleTypeHomeTreeVo;
import com.xiaoyang.vo.article.ArticleTypeVo;

import java.util.List;

/**
 * @author xiaomei
 * @description 针对表【article_type(文章类型)】的数据库操作Service
 * @createDate 2023-11-11 11:27:56
 */
public interface ArticleTypeService extends IService<ArticleType> {

    List<ArticleTypeVo> articleTypeList();

    List<ArticleTypeVo> getSonArticleType(String articleTypeParentId);

    List<ArticleTypeHomeTreeVo> getArticleTypeHomeTree();
}
