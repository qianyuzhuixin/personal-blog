package com.xiaoyang.aop;

import com.alibaba.fastjson.JSON;
import com.xiaoyang.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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

    @Pointcut("@annotation(com.xiaoyang.aop.LogAnnotation)")
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
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            String params = JSON.toJSONString(args[i]);
            log.info("params: {}", params);
        }
        //获取request 设置IP地址
        HttpServletRequest request = CommonUtils.getRequest();
        log.info("ip: {}", CommonUtils.getClientIpAddress(request));

        log.info("execution time: {} ms", time);
        log.info("===========================log end============================");
    }
}















