package com.cato.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/15
 * @Description:
 */
@Data
public class ApplicationRecord {

    /** 申请记录id */
    private Integer applyId;

    /** 部门id */
    private Integer depId;

    /** 会议室id */
    private Integer roomId;

    /** 审核状态 */
    private Integer auditStatus;

    /** 申请时间 yyyy-MM-dd:HH:ss */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 审核时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 拒绝理由 */
    private String rejectReason;

}