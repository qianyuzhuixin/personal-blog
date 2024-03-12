package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.Ad;
import com.xiaoyang.service.AdService;
import com.xiaoyang.mapper.AdMapper;
import com.xiaoyang.vo.ad.AdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaomei
 * @description 针对表【ad(广告)】的数据库操作Service实现
 * @createDate 2023-11-10 14:37:50
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad>
        implements AdService {

    @Resource
    private AdMapper adMapper;


    @Override
    public List<AdVo> getAdList(String adTypeId) {
        return adMapper.getAdList(adTypeId);
    }
}




