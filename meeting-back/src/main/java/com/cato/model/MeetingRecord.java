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
public class MeetingRecord {

    private Integer recordId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String meetingTheme;

    private Integer personCount;

    private String meetingDigest;

}