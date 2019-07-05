package com.cato.dao;

import com.cato.model.ApplicationRecord;
import com.cato.model.group.AuditUnit;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
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
public interface ApplicationRecordDao {

    /**
     * 新增申请记录
     * @param applicationRecord 申请记录
     */
    @Options(useGeneratedKeys = true, keyProperty = "applyId")
    @Insert("insert into application_record values (#{applyId}, #{depId}, #{roomId}, #{auditStatus}, #{applyTime}, #{auditTime}, #{rejectReason})")
    void insert(ApplicationRecord applicationRecord);

    /**
     * 更新申请记录
     * @param map 参数集合
     */
    @Update("update application_record set audit_status = #{auditStatus}, audit_time = '#{auditTime}', reject_reason = '#{rejectReason}' where apply_id = #{applyId}")
    void update(Map<String, String> map);

    /**
     * 根据Id删除申请记录
     * @param applyId id
     */
    @Delete("delete from application_record where apply_id = #{applyId}")
    void deleteById(Integer applyId);

    /**
     * 根据审核状态查询申请记录列表
     * @param list 审核状态
     * @param depNo 部门编号
     * @param auditTime 审核时间
     * @return 返回申请记录列表
     */
    @Select({"<script>",
            "SELECT ",
            "COUNT(*) as total, ",
            "apply_id, ",
            "audit_status, ",
            "audit_time, ",
            "reject_reason, ",
            "start_time, ",
            "end_time, ",
            "meeting_theme, ",
            "person_count, ",
            "meeting_digest, ",
            "dep_no, ",
            "dep_name, ",
            "room_no, ",
            "room_type, ",
            "room_size ",
            "FROM ",
            "application_record apply, ",
            "( SELECT record_id, start_time, end_time, meeting_theme, person_count, meeting_digest FROM meeting_record m ) record, ",
            "( SELECT dep_id, dep_no, dep_name FROM department ) dep, ",
            "( SELECT room_id, room_no, room_type, room_size FROM meeting_room ) room ",
            "<where> ",
            "apply.apply_id = record.record_id ",
            "AND apply.room_id = room.room_id ",
            "AND apply.dep_id = dep.dep_id ",
            "AND IF( '${depNo}' = NULL, '1=1', dep.dep_no = '${depNo}' )",
            "AND IF( '${auditTime}' = NULL, '1=1', apply.audit_time >= '${auditTime}' )",
            "AND apply.audit_status IN",
            "<foreach item='auditStatus' index='index' collection='list' open='(' separator=',' close=')'> ",
            "#{auditStatus} ",
            "</foreach> ",
            "</where> ",
            "</script>"})
    List<AuditUnit> listPageInCondition(@Param("list") List<Integer> list, @Param("depNo") String depNo, @Param("auditTime") String auditTime);


    /**
     * 根据部门id查询申请记录id
     * @param depId 部门id
     * @return
     */
    @Select("select apply_id from application_record where dep_id = #{depId}")
    Integer getIdByDepId(Integer depId);
}