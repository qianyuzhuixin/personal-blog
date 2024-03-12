package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName ad
 */
@TableName(value = "ad")
@Data
public class Ad implements Serializable {
    @TableId(value = "ad_id")
    private String adId;

    private String adTypeId;

    private String adTitle;

    private String adImgUrl;

    private String adLinkUrl;

    private Integer adSort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adBeginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adEndTime;

    private Date adAddTime;

    private static final long serialVersionUID = 1L;
}