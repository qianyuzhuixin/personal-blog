package com.xiaoyang.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyang.pojo.UserCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyang.vo.article.UserCollectArticlePageVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaomei
 * @description 针对表【user_collect(用户收藏文章表)】的数据库操作Mapper
 * @createDate 2024-01-13 19:52:16
 * @Entity com.xiaoyang.pojo.UserCollect
 */
public interface UserCollectMapper extends BaseMapper<UserCollect> {

    IPage<UserCollectArticlePageVo> getUserCollects(Page<UserCollectArticlePageVo> userCollectPage, @Param("userId") String userId);
}




