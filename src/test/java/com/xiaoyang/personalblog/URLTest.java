package com.xiaoyang.personalblog;

import com.xiaoyang.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Description: url编码测试
 * @Author: xiaomei
 * @Date: 2024/3/18 018
 */
@SpringBootTest
public class URLTest {
    @Test
    public void encode() {
        // 假设你有一个含有中文的URL
        String chineseUrl = "https://cjr-sport-system.oss-cn-guangzhou.aliyuncs.com/cbyblog/2024-03-18/1710731632465更改centos7 的tomcat部署端口_2.png";

        CommonUtils.encodePath(chineseUrl);
    }

    @Test
    public void decode() {
        // 假设你有一个编码后的URL
        String encodedUrl = "http://example.com/%E7%BE%8E%E4%B8%BD%E7%9A%84%E5%9B%BE%E7%89%87.jpg";

        try {
            // 对URL路径部分进行解码
            String decodedUrlPath = URLDecoder.decode(encodedUrl.substring(encodedUrl.indexOf("//") + 2), "UTF-8");
            // 重新构建完整的URL
            String decodedUrl = encodedUrl.substring(0, encodedUrl.indexOf("//") + 2) + decodedUrlPath;

            System.out.println("Decoded URL: " + decodedUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
