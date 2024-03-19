package com.xiaoyang.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 用户校验
 * @Author: xiaomei
 * @Date: 2023/11/13 013
 */
@Data
public class UserDto {
    @NotBlank(message = "id不能为空")
    private String userId;

    private String userName;

    private String userPassword;

    //是否冻结，0正常，1冻结，冻结无法登陆
    private Integer userFrozen;

    // 是否允许发布文章，0正常，1不允许
    private Integer userWrite;
}
