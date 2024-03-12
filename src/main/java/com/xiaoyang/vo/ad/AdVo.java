package com.xiaoyang.vo.ad;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 广告vo
 * @Author: xiaomei
 * @Date: 2023/12/8 008
 */
@Data
public class AdVo {

    private String adId;

    private String adTypeId;

    private String adTitle;

    private String adImgUrl;

    private String adLinkUrl;

    private Integer adSort;

    private Date adBeginTime;

    private Date adEndTime;

    private Date adAddTime;

    // 广告类型名称
    private String adTypeName;
}
