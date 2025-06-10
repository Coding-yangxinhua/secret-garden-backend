package com.pwc.sdc.sg.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 卡密表
 * @TableName sg_card_code
 */
@TableName(value ="sg_card_code")
@Data
public class CardCode implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 卡密
     */
    private String code;

    /**
     * 套餐id
     */
    private Long subscribeId;

    /**
     * 有效次数 -1为无限次
     */
    private Integer validUses;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}