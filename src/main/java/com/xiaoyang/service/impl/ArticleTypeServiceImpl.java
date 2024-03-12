package com.xiaoyang.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.ArticleType;
import com.xiaoyang.service.ArticleTypeService;
import com.xiaoyang.mapper.ArticleTypeMapper;
import com.xiaoyang.vo.article.ArticleTypeHomeTreeVo;
import com.xiaoyang.vo.article.ArticleTypeVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaomei
 * @description 针对表【article_type(文章类型)】的数据库操作Service实现
 * @createDate 2023-11-11 11:27:56
 */
@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType>
        implements ArticleTypeService {

    @Resource
    private ArticleTypeMapper articleTypeMapper;

    @Override
    public List<ArticleTypeVo> articleTypeList() {
        return articleTypeMapper.getArticleTypeList();
    }

    @Override
    public List<ArticleTypeVo> getSonArticleType(String articleTypeParentId) {
        return articleTypeMapper.getSonArticleType(articleTypeParentId);
    }

    @Override
    public List<ArticleTypeHomeTreeVo> getArticleTypeHomeTree() {
        // 获取所有文章类型
        List<ArticleType> articleFistTypeList = list(Wrappers.<ArticleType>lambdaQuery()
                .eq(ArticleType::getArticleTypeLevel, 1)
                .orderByAsc(ArticleType::getArticleTypeSort)
                .select(ArticleType::getArticleTypeId, ArticleType::getArticleTypeName));
        List<ArticleTypeHomeTreeVo> articleTypeHomeTreeVoList = new ArrayList<>();

        if (CollUtil.isNotEmpty(articleFistTypeList)) {
            // 获取文章子类型
            for (ArticleType articleType : articleFistTypeList) {
                List<ArticleType> articleSecondTypeList = list(Wrappers.<ArticleType>lambdaQuery()
                        .eq(ArticleType::getArticleTypeLevel, 2)
                        .eq(ArticleType::getArticleTypeParentId, articleType.getArticleTypeId())
                        .orderByAsc(ArticleType::getArticleTypeSort)
                        .select(ArticleType::getArticleTypeId, ArticleType::getArticleTypeName));
                ArticleTypeHomeTreeVo articleTypeHomeTreeVo = new ArticleTypeHomeTreeVo();
                articleTypeHomeTreeVo.setArticleTypeId(articleType.getArticleTypeId());
                articleTypeHomeTreeVo.setArticleTypeName(articleType.getArticleTypeName());
                articleTypeHomeTreeVo.setArticleChildList(articleSecondTypeList);
                articleTypeHomeTreeVoList.add(articleTypeHomeTreeVo);
            }
        }
        return articleTypeHomeTreeVoList;
    }
}




