package com.cato.service.impl;

import com.cato.dao.ApplicationRecordDao;
import com.cato.dao.MeetingRecordDao;
import com.cato.model.group.AuditUnit;
import com.cato.model.group.Record;
import com.cato.service.ApplicationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/21
 * @Description: 会议室申请服务接口实现类
 */
@Service
public class ApplicationRecordServiceImpl implements ApplicationRecordService {

    private final ApplicationRecordDao applicationRecordDao;

    private final MeetingRecordDao recordDao;

    @Autowired
    public ApplicationRecordServiceImpl(ApplicationRecordDao applicationRecordDao, MeetingRecordDao recordDao) {
        this.applicationRecordDao = applicationRecordDao;
        this.recordDao = recordDao;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(Record record) {
        // 设置待审核
        record.getApplicationRecord().setAuditStatus(0);
        // 添加申请记录
        applicationRecordDao.insert(record.getApplicationRecord());
        // 设置会议记录id
        record.getMeetingRecord().setRecordId(record.getApplicationRecord().getApplyId());
        // 设置会议室id
        record.getApplicationRecord().setRoomId(record.getApplicationRecord().getRoomId());
        // 添加会议记录
        recordDao.insert(record.getMeetingRecord());
    }

    /**
     * 前台传回的时间是字符串需要转成date
     * @param map 参数集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Map<String, String> map) {
        map.put("auditTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        applicationRecordDao.update(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer depId) {
        applicationRecordDao.deleteById(depId);
    }

    @Override
    public List<Integer> listTimeConflict(String startTime, String endTime) {
        return recordDao.listTimeConflict(startTime, endTime);
    }

    @Override
    public List<AuditUnit> listPageInCondition(List<Integer> auditStatus, String depNo, String auditTime) {

        return applicationRecordDao.listPageInCondition(auditStatus, depNo, auditTime);
    }

    @Override
    public Integer getIdByDepId(Integer depId) {
        return applicationRecordDao.getIdByDepId(depId);
    }

}
