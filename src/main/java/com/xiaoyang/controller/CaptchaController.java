package com.xiaoyang.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import com.xiaoyang.utils.Captcha;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 验证码生成控制器
 * @Author: xiaomei
 * @Date: 2024/1/1 001
 */
@RestController
public class CaptchaController {

    @GetMapping("getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 定义图形验证码的长和宽
        CircleCaptcha captcha = Captcha.getCaptcha(request);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write(response.getOutputStream());
    }
}
