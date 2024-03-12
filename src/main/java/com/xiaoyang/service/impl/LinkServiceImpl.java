package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.Link;
import com.xiaoyang.service.LinkService;
import com.xiaoyang.mapper.LinkMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomei
* @description 针对表【link(友情链接)】的数据库操作Service实现
* @createDate 2023-11-10 14:37:50
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

}




