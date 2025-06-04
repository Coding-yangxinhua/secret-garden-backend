package com.pwc.sdc.sg.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pwc.sdc.sg.model.UserSubscribe;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户套餐表
 * @author Xinhua X Yang
 * @TableName sg_user_subscribe
 */
@TableName(value ="sg_user_subscribe")
@Data
public class UserSubscribeDto extends UserSubscribe {
    private String subscribeName;

    private Integer ratio;
}