package com.xiaoyang.error;

import com.xiaoyang.utils.Result;
import com.xiaoyang.utils.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 异常处理
 * @Author: xiaomei
 * @Date: 2023/11/18 018
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /*
     *一般参数绑定异常
     * @param null
     * @return
     */
    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseBody
    public Result handlerBindException(BindException ex) {
        List<String> defaultMsg = ex.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return Result.failed(defaultMsg.get(0));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.build(null, ResultCodeEnum.CHECK_PARAMETER_ERROR);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return Result.failed(message);
    }
}
