package com.cato.service;

import com.cato.model.Department;

import java.util.List;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/19
 * @Description:
 */
public interface DepartmentService {

    /**
     * 新增部门
     * @param department 部门
     */
    void insert(Department department);

    /**
     * 修改部门
     * @param department 部门
     */
    void update(Department department);

    /**
     * 根据id删除部门
     * @param depId 部门id
     */
    void delete(Integer depId);

    /**
     * 查询部门列表
     * @return 部门列表
     */
    List<Department> listAll();

    /**
     * 条件查询
     * @param map 条件集合
     * @return
     */
    List<Department> listByCondition(Map<String, Object> map);

    /**
     * 根据id查询
     * @param depId
     * @return
     */
    Department getById(Integer depId);
}
