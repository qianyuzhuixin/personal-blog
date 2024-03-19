package com.xiaoyang.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 验证码工具类
 * @Author: xiaomei
 * @Date: 2024/1/1 001
 */
public class Captcha {

    public static CircleCaptcha getCaptcha(HttpServletRequest request) {
        // 定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 30);
        request.getSession().setAttribute("circleCaptchaCode", captcha.getCode());
        return captcha;
    }

}
