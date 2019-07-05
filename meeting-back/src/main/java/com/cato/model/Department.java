package com.cato.model;

import lombok.Data;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/15
 * @Description:
 */
@Data
public class Department {

    /** 部门id */
    private Integer depId;

    /** 部门编号 */
    private String depNo;

    /** 部门名称 */
    private String depName;

    /** 登录密码 */
    private String depPassword;

    /** 部门人数 */
    private Integer depPersonCount;

    /** 部门电话 */
    private String depPhone;

    /** 部门联系人手机号 */
    private String depMobile;

}