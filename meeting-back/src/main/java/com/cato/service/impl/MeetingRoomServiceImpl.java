package com.cato.service.impl;

import com.cato.dao.MeetingRoomDao;
import com.cato.model.MeetingRoom;
import com.cato.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/17
 * @Description: 会议室服务实现类
 */
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomDao meetingRoomDao;

    @Autowired
    public MeetingRoomServiceImpl(MeetingRoomDao meetingRoomDao) {
        this.meetingRoomDao = meetingRoomDao;
    }

    @Override
    public List<MeetingRoom> listAll() {
        return meetingRoomDao.listAll();
    }

    @Override
    public MeetingRoom getById(Integer roomId) {
        return meetingRoomDao.getById(roomId);
    }

    @Override
    public List<MeetingRoom> listByCondition(Map<String, Object> map) {
        return meetingRoomDao.listByCondition(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(MeetingRoom room) {
        meetingRoomDao.insert(room);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(MeetingRoom room) {
        meetingRoomDao.update(room);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer roomId) {
        meetingRoomDao.deleteById(roomId);
    }
}
