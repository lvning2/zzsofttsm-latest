package com.zzsoft.zzsofttsm.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
public class EquipmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "equipmentId",columnDefinition = "int COMMENT '设备(电脑)id'")
    private String equipmentId;         //设备id

    @Column(name = "userId",columnDefinition = "int COMMENT '使用者id'")
    private Integer userId;             //使用者id

    @Column(name = "startTime",columnDefinition = "date COMMENT '开始时间'")
    private Date startTime;             //开始时间

    @Column(name = "endTime",columnDefinition = "date COMMENT '结束时间'")
    private Date endTime;               //结束时间

    @Column(name = "other",columnDefinition = "varchar(1024) COMMENT '其他'")
    private String other;               //其他

}


