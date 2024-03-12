package com.xiaoyang.utils;

import lombok.Data;

/**
 * @Description:
 * @Author: xiaomei
 * @Date: 2023/11/13 013
 */

public enum ResultCodeEnum {
    SUCCESS(200, "success", "操作成功"),
    ARGUMENT_ERROR(460, "argument_error", "参数错误"),
    CHECK_PARAMETER_ERROR(461, "check_parameter_error", "参数不符合"),

    DEL_CASCADE_ERROR(520, "del_cascade_error", "需要删除级联的内容"),

    DEL_ERROR(521, "del_error", "删除失败"),

    USER_NOT_EXIST(522, "user_not_exist", "用户不存在"),

    UPDATE_ERROR(523, "update_error", "修改失败"),

    INSERT_ERROR(524, "insert_error", "新增失败"),

    ID_IS_EXIST(525, "id_is_exist", "id已存在");


    private Integer code;

    private String message;

    //返回描述
    private String description;


    private ResultCodeEnum(Integer code, String description, String message) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
