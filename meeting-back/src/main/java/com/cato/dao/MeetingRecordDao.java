package com.cato.dao;

import com.cato.model.MeetingRecord;
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
public interface MeetingRecordDao {

    /**
     * 新增会议记录
     * @param record 会议记录
     */
    @Insert("insert into meeting_record values (#{recordId}, #{startTime}, #{endTime}, #{meetingTheme}, #{personCount}, #{meetingDigest})")
    void insert(MeetingRecord record);

    /**
     * 根据申请记录id删除会议记录
     * @param applyId 申请记录id
     */
    @Delete("delete from meeting_record where record_id = #{applyId}")
    void delete(Integer applyId);

    /**
     * 查重
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 会议记录id列表
     */
    @Select("select record_id from meeting_record where start_time between '${startTime}' and DATE_ADD('${endTime}', INTERVAL 30 MINUTE) or end_time between '${startTime}' and DATE_ADD('${endTime}', INTERVAL 30 MINUTE)")
    List<Integer> listTimeConflict(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据会议室id获取该会议室从此刻开始被申请过的记录列表  用于申请时下方展示列表
     * @param roomId 会议室id
     * @param startTime 开始时间
     * @return 会议记录列表
     */
    @Select("SELECT " +
            "start_time, " +
            "end_time, " +
            "dep_name " +
            "FROM " +
            "meeting_record m, " +
            "( SELECT dep_id, dep_name FROM department ) d, " +
            "( SELECT apply_id, room_id, dep_id FROM application_record ) a  " +
            "WHERE " +
            "start_time >= '${startTime}'  " +
            "AND m.record_id = a.apply_id  " +
            "AND a.dep_id = d.dep_id  " +
            "AND a.room_id = #{roomId}")
    List<Map<String, Object>> listRecordAfterNowById(@Param("roomId") Integer roomId, @Param("startTime") String startTime);

    /**
     * 根据applyId获取会议记录列表  用于管理员根据审核状态查看
     * @param applyIds id集合
     * @return 返回会议记录集合
     */
    @Select({"<script>",
                "select * from meeting_record",
                " <where> ",
                    "record_id in ",
                        "<foreach item='applyId' index='index' collection='applyIds' open='(' separator=',' close=')'> ",
                            "#{applyId} ",
                        "</foreach> ",
                "</where> ",
            "</script>"})
    List<MeetingRecord> listRecordByIds(List<Integer> applyIds);

    /**
     * 根据id查询会议记录
     * @param recordId 会议记录id
     * @return 返回会议记录实体
     */
    @Select("select * from meeting_record where record_id = #{recordId}")
    MeetingRecord getById(Integer recordId);

}