package com.cato.dao;

import com.cato.model.MeetingRoom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/15
 * @Description:
 */
@Repository
public interface MeetingRoomDao {

    /**
     * 查询所有的会议室
     * @return 返回会议室集合
     */
    @Select("SELECT * FROM meeting_room")
    List<MeetingRoom> listAll();

    /**
     * 根据id查询会议室
     * @param roomId 会议室id
     * @return
     */
    @Select("SELECT * FROM meeting_room WHERE room_id = #{roomId}")
    MeetingRoom getById(Integer roomId);

    /**
     * 条件查询会议室 字段和map里面的key一致即可
     * @param map 查询条件
     * @return
     */
    @Select({"<script>" +
                "SELECT * FROM meeting_room " +
                    "WHERE " +
                        "1 = 1" +
                        "<when test = 'roomNo != null'>" +
                            " AND room_no = #{roomNo}" +
                        "</when>" +
                        "<when test = 'roomFloor != null'>" +
                            " AND room_floor = #{roomFloor}" +
                        "</when>" +
                        "<when test = 'roomType != null'>" +
                            " AND room_type = #{roomType}" +
                        "</when>" +
                        "<when test = 'roomSize != null'>" +
                            " AND room_size = #{roomSize}" +
                        "</when>" +
                        "<when test = 'roomStatus != null'>" +
                            " AND room_status = #{roomStatus}" +
                        "</when>" +
            "</script>"})
    List<MeetingRoom> listByCondition(Map<String, Object> map);

    /**
     * 新增会议室
     * @param room
     */
    @Options(useGeneratedKeys = true, keyProperty = "roomId")
    @Insert("insert into meeting_room values (#{roomId}, #{roomNo}, #{roomFloor}, #{roomType}, #{roomSize}, #{roomStatus})")
    void insert(MeetingRoom room);

    /**
     * 修改会议室相关信息
     * @param room
     */
    @Update({"update meeting_room set room_no = #{roomNo}, room_floor = #{roomFloor}, room_type = #{roomType}, room_size = #{roomSize}, room_status = #{roomStatus} where room_id = #{roomId}"})
    void update(MeetingRoom room);

    /**
     * 根据主键删除会议室
     * @param roomId 会议室id
     */
    @Delete("delete from meeting_room where room_id = #{roomId}")
    void deleteById(Integer roomId);
}