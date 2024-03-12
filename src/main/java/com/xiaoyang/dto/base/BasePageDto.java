package com.xiaoyang.dto.base;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description: 分页基础类
 * @Author: xiaomei
 * @Date: 2023/11/12 012
 */
@Data
public class BasePageDto {

    //第几页
    @NotNull(message = "未获取当前页码")
    private Integer pageNum = 1;

    // 一页有多少条
    private Integer pageSize = 10;

}
