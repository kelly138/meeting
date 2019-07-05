package com.cato.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/15
 * @Description:
 */
@Alias(value = "room")
@Data
public class MeetingRoom {

    private Integer roomId;

    private String roomNo;

    private String roomFloor;

    private String roomType;

    private Integer roomSize;

    private Integer roomStatus;

}