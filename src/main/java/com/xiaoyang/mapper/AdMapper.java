package com.xiaoyang.mapper;

import com.xiaoyang.pojo.Ad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyang.vo.ad.AdVo;

import java.util.List;

/**
 * @author xiaomei
 * @description 针对表【ad(广告)】的数据库操作Mapper
 * @createDate 2023-11-10 14:37:50
 * @Entity com.xiaoyang.pojo.Ad
 */
public interface AdMapper extends BaseMapper<Ad> {

    List<AdVo> getAdList(String adTypeId);
}




