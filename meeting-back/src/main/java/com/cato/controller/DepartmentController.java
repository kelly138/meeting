package com.cato.controller;

import com.cato.model.Department;
import com.cato.service.ApplicationRecordService;
import com.cato.service.DepartmentService;
import com.cato.service.MeetingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/19
 * @Description:
 */

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    private final ApplicationRecordService applicationRecordService;

    private final MeetingRecordService meetingRecordService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, ApplicationRecordService applicationRecordService, MeetingRecordService meetingRecordService) {
        this.departmentService = departmentService;
        this.applicationRecordService = applicationRecordService;
        this.meetingRecordService = meetingRecordService;
    }

    /**
     * 查询部门列表
     * @return 查询结果
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/listAll")
    public List<Department> listAll(){
        return departmentService.listAll();
    }

    /**
     * 条件查询
     * @return
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/listByCondition")
    public List<Department> listByCondition(@RequestBody Map<String, Object> map){
        return departmentService.listByCondition(map);
    }

    /**
     * 根据id查询
     * @param depId 部门id
     * @return 查询结果
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/getById")
    public Department getById(Integer depId){
        return departmentService.getById(depId);
    }

    /**
     * 添加部门
     * @param department 部门
     * @return 插入结果
     */
    @SuppressWarnings("Duplicates")
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/insert")
    public Boolean insert(@RequestBody Department department){
        try {
            departmentService.insert(department);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改部门
     * @param department 部门
     * @return 修改结果
     */
    @SuppressWarnings("Duplicates")
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/update")
    public Boolean update(@RequestBody Department department){
        try {
            departmentService.update(department);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/delete")
    public Boolean delete(Integer depId){
        try {
           departmentService.delete(depId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
