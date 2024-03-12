package com.xiaoyang.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 返回结果
 * @Author: xiaomei
 * @Date: 2023/11/13 013
 */
@Data
@NoArgsConstructor
public class Result<T> {

    //返回码
    private Integer code;
    //返回描述
    private String description;
    //返回信息
    private String message;
    //返回数据
    private T data;


    //
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    //手动构造返回参数
    public static <T> Result<T> build(T body, Integer code, String description, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setDescription(description);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setMessage(resultCodeEnum.getMessage());
        result.setDescription(resultCodeEnum.getDescription());
        result.setCode(resultCodeEnum.getCode());
        return result;
    }

    // 返回成功信息
    public static <T> Result<T> OK(T body) {
        return build(body, ResultCodeEnum.SUCCESS);
    }

    // 返回成功信息
    public static Result OK(String message) {
        return build(null, 200, null, message);
    }


    // 返回未知异常失败信息
    public static Result failed(String message) {
        return build(null, null, null, message);
    }

    //// 返回成功信息
    //public static String success(String message) {
    //
    //    return message;
    //}


}
