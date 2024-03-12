package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName link
 */
@TableName(value = "link")
@Data
public class Link implements Serializable {
    @TableId(value = "link_id")
    private String linkId;

    private String linkTitle;

    private String linkUrl;

    private String linkLogoUrl;

    private Date linkAddTime;

    private Integer linkSort;

    private static final long serialVersionUID = 1L;
}