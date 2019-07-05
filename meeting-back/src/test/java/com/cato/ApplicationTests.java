package com.cato;

import com.cato.dao.ApplicationRecordDao;
import com.cato.dao.MeetingRecordDao;
import com.cato.model.ApplicationRecord;
import com.cato.model.MeetingRecord;
import com.cato.model.MeetingRoom;
import com.cato.service.ApplicationRecordService;
import com.cato.service.DepartmentService;
import com.cato.service.MeetingRoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ApplicationRecordService applicationRecordService;

    @Autowired
    private ApplicationRecordDao applicationRecordDao;

    @Autowired
    private MeetingRecordDao meetingRecordDao;

    @Test
    public void contextLoads() {

    }

    /**
     * 测试查询会议室列表
     */
    @Test
    public void testSelectMeetingRoom(){
        meetingRoomService.listAll();
    }

    /**
     * 测试插入部门
     */
    @Test
    public void testInsert(){
        MeetingRoom room = new MeetingRoom();
        room.setRoomNo("1001");
        room.setRoomFloor("一层");
        room.setRoomType("大型会议室");
        room.setRoomSize(2000);
        room.setRoomStatus(0);
        meetingRoomService.insert(room);
    }

    /**
     * 测试查询所有部门
     */
    @Test
    public void testSelectDepartment(){
        departmentService.listAll();
    }

    /**
     * 测试插入申请记录与会议记录
     */
    @Test
    public void testApply(){
        ApplicationRecord applicationRecord = new ApplicationRecord();
        applicationRecord.setApplyTime(new Date());
        applicationRecord.setAuditStatus(0);
        applicationRecord.setDepId(1);
        applicationRecord.setRoomId(1);
        applicationRecordDao.insert(applicationRecord);
        MeetingRecord meetingRecord = new MeetingRecord();
        meetingRecord.setRecordId(applicationRecord.getApplyId());
        //meetingRecord.setRoomId(1);
        meetingRecord.setMeetingTheme("开学典礼");
        meetingRecord.setPersonCount(150);
        meetingRecord.setStartTime(new Date());
        meetingRecord.setEndTime(new Date());
        meetingRecord.setMeetingDigest("的好时机卡拉胶大括号接口 点就开始款经典款撒娇快乐到家奥斯卡了的接口了");
        meetingRecordDao.insert(meetingRecord);
    }


    /**
     *
     */
    @Test
    public void testDate(){
    }

    @Test
    public void testMeetingRecordService(){

    }
}
