package com.cato.controller;

import com.cato.model.MeetingRoom;
import com.cato.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/18
 * @Description:
 */
@RestController
@RequestMapping(value = "/room")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @Autowired
    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    /**
     *
     * @return 会议室列表
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/listAll")
    public List<MeetingRoom> listAll(){
       return meetingRoomService.listAll();
    }

    /**
     *
     * @param id 会议室id
     * @return 会议室实体
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/getById")
    public MeetingRoom getById(@RequestParam Integer id){
       return meetingRoomService.getById(id);
    }

    /**
     * 条件查询
     * @param map 查询条件集合
     * @return 会议室实体
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/listByCondition")
    public List<MeetingRoom> listByCondition(@RequestBody Map<String, Object> map){
        return meetingRoomService.listByCondition(map);
    }

    /**
     * 添加会议室
     * @param room 会议室json对象
     * @return 会议室json格式
     */
    @SuppressWarnings("Duplicates")
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/insert")
    public Boolean insert(@RequestBody MeetingRoom room){
        try {
            meetingRoomService.insert(room);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改会议室
     * @param room 会议室实体json字符串
     * @return 执行结果
     */
    @SuppressWarnings("Duplicates")
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/update")
    public Boolean update(@RequestBody MeetingRoom room){
        try {
            meetingRoomService.update(room);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除 未完成！· 需要级联删除
     * @param roomId 会议室id
     * @return 执行结果
     */
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/delete")
    public Boolean delete(Integer roomId){
        try {
            meetingRoomService.deleteById(roomId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
