package com.cato.controller;

import com.cato.service.DepartmentService;
import com.cato.service.MeetingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/23
 * @Description:
 */
@RestController()
@RequestMapping("/record")
public class MeetingRecordController {


    private final MeetingRecordService meetingRecordService;

    private final DepartmentService departmentService;

    @Autowired
    public MeetingRecordController(MeetingRecordService meetingRecordService, DepartmentService departmentService) {
        this.meetingRecordService = meetingRecordService;
        this.departmentService = departmentService;
    }



}
