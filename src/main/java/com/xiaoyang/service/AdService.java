package com.xiaoyang.service;

import com.xiaoyang.pojo.Ad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyang.vo.ad.AdVo;

import java.util.List;

/**
 * @author xiaomei
 * @description 针对表【ad(广告)】的数据库操作Service
 * @createDate 2023-11-10 14:37:50
 */
public interface AdService extends IService<Ad> {

    List<AdVo> getAdList(String adTypeId);
}
