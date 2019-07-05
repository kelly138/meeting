package com.cato.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cato.model.group.AuditUnit;
import com.cato.service.ApplicationRecordService;
import com.cato.vo.PageVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/24
 * @Description:
 */
@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/audit")
public class AuditController {

    private final ApplicationRecordService applicationRecordService;

    @Autowired
    public AuditController(ApplicationRecordService applicationRecordService) {
        this.applicationRecordService = applicationRecordService;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/listRecordByAuditStatus")
    public PageVO listRecordByAuditStatus(@RequestBody Map<String, Object> map){
        // 处理审核状态表
        String auditStatusString = JSONArray.toJSONString(map.get("auditStatus"));
        List auditStatus = JSON.parseObject(auditStatusString, List.class);
        // 获取部门编号
        String depNo = (String) map.get("depNo");
        // 获取审核时间
        String auditTime = (String) map.get("auditTime");

        PageHelper.startPage((Integer) map.get("pageNo"), (Integer) map.get("pageSize"), false, true, true);
        Page<AuditUnit> page = (Page<AuditUnit>) applicationRecordService.listPageInCondition(auditStatus, depNo, auditTime);
        page.setTotal(page.getResult().size());
        return new PageVO(page.getTotal(), page.getResult());
    }



    /**
     *
     * @param
     * @return
     */
    /*@SuppressWarnings("unchecked")
    @RequestMapping("/listRecordByAuditStatus")
    public PageVO listRecordByAuditStatus1(@RequestBody Map<String, Object> map){
        // jsonArray转list
        String auditStatusString = JSONArray.toJSONString(map.get("auditStatus"));
        List auditStatus = JSON.parseObject(auditStatusString, List.class);
        List<AuditDetailVO> list = new ArrayList<>();
        Page<Object> page = PageHelper.startPage((Integer) map.get("pageNo"), (Integer) map.get("pageSize"));
        List<ApplicationRecord> applicationRecordList = applicationRecordService.listPageInCondition(auditStatus);
        if (applicationRecordList.size() > 0) {
            for (ApplicationRecord applicationRecord : applicationRecordList) {
                Department department = departmentService.getById(applicationRecord.getDepId());
                MeetingRoom meetingRoom = roomService.getById(applicationRecord.getRoomId());
                MeetingRecord meetingRecord = meetingRecordService.getById(applicationRecord.getApplyId());
                list.add(assembleData(applicationRecord, department, meetingRoom, meetingRecord));
            }
        }
        return new PageVO(page.getTotal(), page.getResult());
    }

    private AuditDetailVO assembleData(ApplicationRecord applicationRecord, Department department, MeetingRoom meetingRoom, MeetingRecord meetingRecord){
        AuditDetailVO auditDetailVO = new AuditDetailVO();

        auditDetailVO.setApplyId(applicationRecord.getApplyId());
        auditDetailVO.setApplyTime(applicationRecord.getApplyTime());
        auditDetailVO.setAuditStatus(applicationRecord.getAuditStatus());
        auditDetailVO.setRejectReason(applicationRecord.getRejectReason());

        auditDetailVO.setDepId(department.getDepId());
        auditDetailVO.setDepNo(department.getDepNo());
        auditDetailVO.setDepName(department.getDepName());

        auditDetailVO.setRoomId(meetingRoom.getRoomId());
        auditDetailVO.setRoomNo(meetingRoom.getRoomType());
        auditDetailVO.setRoomType(meetingRoom.getRoomType());
        auditDetailVO.setRoomSize(meetingRoom.getRoomSize());

        auditDetailVO.setStartTime(meetingRecord.getStartTime());
        auditDetailVO.setEndTime(meetingRecord.getEndTime());
        auditDetailVO.setMeetingTheme(meetingRecord.getMeetingTheme());
        auditDetailVO.setMeetingDigest(meetingRecord.getMeetingDigest());
        auditDetailVO.setPersonCount(meetingRecord.getPersonCount());
        return auditDetailVO;
    }*/
}
