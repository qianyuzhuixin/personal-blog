package com.xiaoyang.aop;

import com.alibaba.fastjson.JSON;
import com.xiaoyang.annotation.LogAnnotation;
import com.xiaoyang.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @Description: 日志aop
 * @Author: xiaomei
 * @Date: 2024/3/19 019
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.xiaoyang.annotation.LogAnnotation)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 执行时长
        long time = System.currentTimeMillis() - startTime;

        // 保存日志
        recordLog(joinPoint, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("===========================log start============================");
        log.info("module: {}", logAnnotation.module());
        log.info("operator: {}", logAnnotation.operator());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method: {}", className + "." + methodName + "()");

        //请求的参数
        //序列化时过滤掉request和response
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            // 如果参数类型是请求和响应的http，则不需要拼接【这两个参数，使用JSON.toJSONString()转换会抛异常】
            if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse
                    || args[i] instanceof MultipartFile || args[i] instanceof Model
                    || args[i] instanceof HttpSession) {
                continue;
            }
            String result = JSON.toJSONString(args[i]);
            log.info("params: {}", result);
        }

        //获取request 设置IP地址
        HttpServletRequest request = CommonUtils.getRequest();
        log.info("ip: {}", CommonUtils.getClientIpAddress(request));

        log.info("execution time: {} ms", time);
        log.info("===========================log end============================");
    }
}















