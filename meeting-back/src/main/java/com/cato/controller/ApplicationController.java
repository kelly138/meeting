package com.cato.controller;

import com.cato.model.group.Record;
import com.cato.service.ApplicationRecordService;
import com.cato.service.DepartmentService;
import com.cato.service.MeetingRecordService;
import com.cato.service.MeetingRoomService;
import com.cato.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/23
 * @Description: 会议申请控制层
 */
@RestController
@RequestMapping("/apply")
public class ApplicationController {

    private final ApplicationRecordService applicationRecordService;

    private final MeetingRecordService meetingRecordService;


    @Autowired
    public ApplicationController(ApplicationRecordService applicationRecordService, MeetingRoomService meetingRoomService, MeetingRecordService meetingRecordService, DepartmentService departmentService) {
        this.applicationRecordService = applicationRecordService;
        this.meetingRecordService = meetingRecordService;
    }


    /**
     * 新增会议申请记录及会议记录
     * @param record 会议申请记录组合实体类
     * @return
     *
     * {
     * 	"applicationRecord" : {
     * 		"depId" : 2,
     * 		"roomId" : 6,
     * 		"applyTime" : "2019-04-23 13:44:00"
     * 	},
     * 	"meetingRecord" : {
     * 		"meetingTheme" : "大扫除",
     * 		"personCount" : 250,
     * 		"startTime" : "2019-04-23 15:30:00",
     * 		"endTime" : "2019-04-23 15:40:00",
     * 		"meetingDigest" : "测试哈哈哈哈哈哈哈哈哈哈哈哈"
     * 	}
     * }
     */
    @Secured("ROLE_ADMIN")
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Boolean insert(@RequestBody Record record){
        try {
            /* 集成security后获取登录名 根据登录名获取depid  已解决*/
            record.getApplicationRecord().setDepId(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));
            applicationRecordService.insert(record);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新申请记录
     * @param map 参数集合
     * @return
     */
    @SuppressWarnings("Duplicates")
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Boolean update(@RequestBody Map<String, String> map){
        try {
            applicationRecordService.update(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询时间冲突
     * @return 与其时间冲突的会议记录的id集合 前台对其标红
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/listTimeConflict")
    public List<Integer> listTimeConflict(@RequestBody Map<String, String> map){
        return applicationRecordService.listTimeConflict(map.get("startTime"), map.get("endTime"));
    }


    /**
     * 根据会议室id查询从此时刻开始已经被申请的记录
     * @param roomId 会议室id
     * @return
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/listRecordFromNow")
    public List<RecordVO> listRecordFromNow(Integer roomId) throws ParseException {
        List<RecordVO> recordVOList = new ArrayList<>();
        List<Map<String, Object>> meetingRecords = meetingRecordService.listRecordAfterNowById(roomId, "2019-04-16 00:00:00");
        // 如果有则遍历进行数据组装
        if (meetingRecords.size() > 0){
            for (Map<String, Object> record : meetingRecords) {
                RecordVO recordVO = new RecordVO();

                recordVO.setMeetingDate(new SimpleDateFormat("yyyy-MM-dd").format(record.get("start_time")));
                recordVO.setStartTime(new SimpleDateFormat("HH:mm:ss").format(record.get("start_time")));
                recordVO.setEndTime(new SimpleDateFormat("HH:mm:ss").format(record.get("end_time")));
                recordVO.setDepName((String) record.get("dep_name"));
                recordVOList.add(recordVO);
            }
            return recordVOList;
        }else {
            return null;
        }
    }
}
