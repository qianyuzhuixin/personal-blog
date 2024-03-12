package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.AdType;
import com.xiaoyang.service.AdTypeService;
import com.xiaoyang.mapper.AdTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomei
* @description 针对表【ad_type(广告类型)】的数据库操作Service实现
* @createDate 2023-11-10 14:37:50
*/
@Service
public class AdTypeServiceImpl extends ServiceImpl<AdTypeMapper, AdType>
    implements AdTypeService{

}




