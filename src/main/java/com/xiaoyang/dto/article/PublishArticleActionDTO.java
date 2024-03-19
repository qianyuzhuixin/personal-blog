package com.xiaoyang.dto.article;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 接收前端输入的文章内容
 * @Author: xiaomei
 * @Date: 2024/2/1 001
 */
@Data
public class PublishArticleActionDTO {
    // 文章id
    private String articleId;

    // 文章标题
    @NotBlank(message = "文章标题不能为空")
    @Length(max = 480, message = "文章标题长度不能超过480个字符")
    private String articleTitle;

    // 文章类型id
    @NotBlank(message = "文章类型不能为空")
    private String articleTypeId;

    // 文章封面url
    private String articleCoverUrl;

    // 文章标签id列表
    private String[] articleTagIds;


    // 文章内容
    @NotBlank(message = "文章内容不能为空")
    @Length(min = 5, max = 15000, message = "文章内容需在5~15000个字符之间")
    private String articleContent;
}
