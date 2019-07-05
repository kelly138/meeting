package com.cato.service;

import com.cato.model.MeetingRecord;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/23
 * @Description: 会议记录服务接口
 */
public interface MeetingRecordService {

    /**
     * 根据会议室id获取该会议室从此刻开始被申请过的记录列表
     * @param roomId 会议室id
     * @param startTime 开始时间
     * @return 会议记录列表
     */
    List<Map<String, Object>> listRecordAfterNowById(Integer roomId, String startTime);

    /**
     * 根据申请记录id查询会议记录列表
     * @param applyIds 申请记录id
     * @return 返回会议记录列表
     */
    List<MeetingRecord> listRecordByIds(List<Integer> applyIds);

    /**
     * 根据id查询会议记录
     * @param recordId 会议记录id
     * @return 返回会议记录实体
     */
    MeetingRecord getById(Integer recordId);

    /**
     * 根据申请记录id删除会议记录
     * @param applyId 申请记录id
     */
    void delete(Integer applyId);
}
