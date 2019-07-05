package com.cato.service.impl;

import com.cato.dao.MeetingRecordDao;
import com.cato.model.MeetingRecord;
import com.cato.service.MeetingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/23
 * @Description:
 */
@Service
public class MeetingRecordServiceImpl implements MeetingRecordService {

    private final MeetingRecordDao meetingRecordDao;

    @Autowired
    public MeetingRecordServiceImpl(MeetingRecordDao meetingRecordDao) {
        this.meetingRecordDao = meetingRecordDao;
    }

    @Override
    public List<Map<String, Object>> listRecordAfterNowById(Integer roomId, String startTime) {
        return meetingRecordDao.listRecordAfterNowById(roomId, startTime);
    }

    @Override
    public List<MeetingRecord> listRecordByIds(List<Integer> applyIds) {
        return meetingRecordDao.listRecordByIds(applyIds);
    }

    @Override
    public MeetingRecord getById(Integer recordId) {
        return meetingRecordDao.getById(recordId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer applyId) {
        meetingRecordDao.delete(applyId);
    }
}
