package com.cato.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/24
 * @Description:
 */
@Data
@AllArgsConstructor
public class PageVO {

    private Long total;

    private List<?> rows;

}
