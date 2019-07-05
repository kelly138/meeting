package com.cato.model.group;

import lombok.Data;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/25
 * @Description:
 */
@Data
public class AuditUnit {

    private Integer applyId;

    private Integer auditStatus;

    private String auditTime;

    private String rejectReason;

    private String startTime;

    private String endTime;

    private String meetingTheme;

    private Integer personCount;

    private String meetingDigest;

    private String depNo;

    private String depName;

    private String roomNo;

    private String roomType;

    private Integer roomSize;

    private Integer total;
}
