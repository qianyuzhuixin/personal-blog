package com.xiaoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.Admin;
import com.xiaoyang.service.AdminService;
import com.xiaoyang.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomei
* @description 针对表【admin(管理员)】的数据库操作Service实现
* @createDate 2023-12-10 15:43:28
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




