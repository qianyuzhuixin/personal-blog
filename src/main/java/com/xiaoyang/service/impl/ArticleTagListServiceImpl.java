package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.ArticleTagList;
import com.xiaoyang.service.ArticleTagListService;
import com.xiaoyang.mapper.ArticleTagListMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomei
* @description 针对表【article_tag_list(文章标签id)】的数据库操作Service实现
* @createDate 2023-11-10 14:37:50
*/
@Service
public class ArticleTagListServiceImpl extends ServiceImpl<ArticleTagListMapper, ArticleTagList>
    implements ArticleTagListService{

}




