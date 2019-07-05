package com.cato.dao;

import com.cato.model.Department;
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
@Mapper
public interface DepartmentDao {

    /**
     * 添加部门
     *
     * @param department 部门
     */
    @Options(useGeneratedKeys = true, keyProperty = "depId")
    @Insert("insert into department values (#{depId}, #{depNo}, #{depName}, #{depPassword}, #{depPhone}, #{depMobile})")
    void insert(Department department);

    /**
     * 修改部门
     *
     * @param department 部门
     */
    @Update("update department set dep_no = #{depNo}, dep_name = #{depName}, dep_password = #{depPassword}, dep_phone = #{depPhone}, dep_mobile = #{depMobile} where dep_id = #{depId}")
    void update(Department department);

    /**
     * 根据id删除部门
     * @param depId 部门id
     */
    @Delete("delete from department where dep_id = #{depId}")
    void deleteById(Integer depId);

    /**
     * 取消外键校验
     */
    @Select("SET FOREIGN_KEY_CHECKS = 0;")
    void disableForeignKeyCheck();

    /**
     * 开启外键校验
     */
    @Select("SET FOREIGN_KEY_CHECKS = 1")
    void enableForeignKeyCheck();
    /**
     * 查询部门列表
     *
     * @return 返回部门列表
     */
    @Select("SELECT * FROM department")
    List<Department> listAll();

    /**
     * 动态条件查询
     *
     * @param map 条件集合
     * @return 返回条件查询得到的部门列表
     */
    @Select({"<script>" +
            "SELECT * FROM department " +
            "WHERE " +
            "1 = 1" +
            "<when test = 'depNo != null'>" +
            " AND dep_no = #{depNo}" +
            "</when>" +
            "<when test = 'depName != null'>" +
            " AND dep_name = #{depName}" +
            "</when>" +
            "<when test = 'depPassword != null'>" +
            " AND dep_password = #{depPassword}" +
            "</when>" +
            "<when test = 'depPhone != null'>" +
            " AND dep_phone = #{depPhone}" +
            "</when>" +
            "<when test = 'depMobile != null'>" +
            " AND dep_mobile = #{depMobile}" +
            "</when>" +
            "</script>"})
    List<Department> listByCondition(Map<String, Object> map);
    /**
     * 根据id查询部门
     * @param depId 部门id
     * @return 返回部门
     */
    @Select("select * from department where dep_id = #{depId}")
    Department getById(Integer depId);

    /**
     * 根据部门编号查询部门
     * @param depNo
     * @return
     */
    @Select("select * from department where dep_no = #{depNo}")
    Department getByUsername(String depNo);

}