package com.cato.service;

import com.cato.model.group.AuditUnit;
import com.cato.model.group.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/21
 * @Description: 申请记录服务接口
 */
public interface ApplicationRecordService {
    /**
     * 添加会议室申请记录
     * @param record 申请记录和会议记录组合实体类
     */
    void insert(Record record);

    /**
     * 更新申请记录
     * @param map 参数集合
     */
    void update(Map<String, String> map);

    /**
     * 根据depId删除申请记录
     * @param depId 部门id
     */
    void deleteById(Integer depId);

    /**
     * 查询时间冲突的记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 条件下的申请记录
     */
    List<Integer> listTimeConflict(String startTime, String endTime);

    /**
     * 根据审核状态查询申请记录
     * @param auditStatus 审核状态
     * @param depNo 部门编号
     * @param auditTime 审核时间
     * @return 申请记录列表
     */
    List<AuditUnit> listPageInCondition(List<Integer> auditStatus, String depNo, String auditTime);

    /**
     * 根据部门id查询申请记录id
     * @param depId 部门id
     * @return 申请记录id
     */
    @Select("select apply_id from application_record where dep_id = #{depId}")
    Integer getIdByDepId(Integer depId);
}
