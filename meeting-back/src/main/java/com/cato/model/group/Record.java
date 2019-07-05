package com.cato.model.group;

import com.cato.model.ApplicationRecord;
import com.cato.model.MeetingRecord;
import lombok.Data;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/21
 * @Description: 申请记录和会议记录的组合实体类
 */
@Data
public class Record {

    private ApplicationRecord applicationRecord;

    private MeetingRecord meetingRecord;
}
