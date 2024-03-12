package com.xiaoyang.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @Description:
 * @Author: xiaomei
 * @Date: 2024/1/1 001
 */
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (Objects.isNull(session.getAttribute("admin"))) {
            response.sendRedirect("/xyadmin/login");
            return false;
        }
        return true;
    }
}
