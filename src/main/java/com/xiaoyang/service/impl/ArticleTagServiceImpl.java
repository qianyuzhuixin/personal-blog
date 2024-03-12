package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.ArticleTag;
import com.xiaoyang.service.ArticleTagService;
import com.xiaoyang.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomei
* @description 针对表【article_tag(标签)】的数据库操作Service实现
* @createDate 2023-11-10 14:37:50
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




