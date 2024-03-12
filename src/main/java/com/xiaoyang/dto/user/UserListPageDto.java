package com.xiaoyang.dto.user;

import com.xiaoyang.dto.base.BasePageDto;
import lombok.Data;

/**
 * @Description: 用户分页类
 * @Author: xiaomei
 * @Date: 2023/11/12 012
 */
@Data
public class UserListPageDto extends BasePageDto {

    private String userName;
}
