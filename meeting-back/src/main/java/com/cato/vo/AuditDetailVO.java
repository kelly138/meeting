package com.cato.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/24
 * @Description:
 */
@Data
public class AuditDetailVO {

    private Integer depId;

    private String depNo;

    private String depName;

    /** 申请记录id */
    private Integer applyId;

    /** 会议室id */
    private Integer roomId;

    private String roomNo;

    private String roomType;

    private Integer roomSize;

    /** 审核状态 */
    private Integer auditStatus;

    /** 申请时间 yyyy-MM-dd:HH:ss */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 拒绝理由 */
    private String rejectReason;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String meetingTheme;

    private Integer personCount;

    private String meetingDigest;

}
