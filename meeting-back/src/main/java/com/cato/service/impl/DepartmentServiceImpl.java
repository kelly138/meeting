package com.cato.service.impl;

import com.cato.dao.ApplicationRecordDao;
import com.cato.dao.DepartmentDao;
import com.cato.dao.MeetingRecordDao;
import com.cato.model.Department;
import com.cato.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/19
 * @Description: 部门服务接口实现类
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;

    private final ApplicationRecordDao applicationRecordDao;

    private final MeetingRecordDao meetingRecordDao;
    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao, ApplicationRecordDao applicationRecordDao, MeetingRecordDao meetingRecordDao) {
        this.departmentDao = departmentDao;
        this.applicationRecordDao = applicationRecordDao;
        this.meetingRecordDao = meetingRecordDao;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(Department department) {

        Map<String, Object> map = new HashMap<>(16);
        map.put("depNo", department.getDepNo());
        List<Department> departmentList = departmentDao.listByCondition(map);
        if (departmentList.size() == 0){
            departmentDao.insert(department);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Department department) {
        departmentDao.update(department);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer depId) {
        departmentDao.disableForeignKeyCheck();
        Integer id = applicationRecordDao.getIdByDepId(depId);
        meetingRecordDao.delete(id);
        departmentDao.deleteById(depId);
        applicationRecordDao.deleteById(id);
        departmentDao.enableForeignKeyCheck();
    }

    @Override
    public List<Department> listAll() {
        return departmentDao.listAll();
    }

    @Override
    public List<Department> listByCondition(Map<String, Object> map) {
        return departmentDao.listByCondition(map);
    }

    @Override
    public Department getById(Integer depId) {
        return departmentDao.getById(depId);
    }
}
