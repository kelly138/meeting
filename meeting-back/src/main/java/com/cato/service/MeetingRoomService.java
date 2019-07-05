package com.cato.service;

import com.cato.model.MeetingRoom;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/16
 * @Description:
 */
public interface MeetingRoomService {
    /**
     * 获取会议室列表
     * @return 会议室列表
     */
    List<MeetingRoom> listAll();

    /**
     * 根据id查询会议室
     * @param id 会议室id
     * @return
     */
    MeetingRoom getById(Integer id);

    /**
     * 动态条件查询
     * @param map 查询条件集合
     * @return
     */
    List<MeetingRoom> listByCondition(Map<String, Object> map);

    /**
     * 新增会议室
     * @param room 会议室 实体
     */
    void insert(MeetingRoom room);

    /**
     * 修改会议室相关信息
     * @param room
     */
    void update(MeetingRoom room);

    /**
     * 根据主键删除会议室
     * @param roomId
     */
    void deleteById(Integer roomId);

}
