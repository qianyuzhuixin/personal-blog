package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName ad_type
 */
@TableName(value ="ad_type")
@Data
public class AdType implements Serializable {
    @TableId(value = "ad_type_id")
    private String adTypeId;

    private String adTypeName;

    private String adTypeTag;

    private Integer adTypeSort;

    private Date adTypeAddTime;

    private static final long serialVersionUID = 1L;
}