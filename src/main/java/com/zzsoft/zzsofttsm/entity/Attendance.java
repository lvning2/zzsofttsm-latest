package com.zzsoft.zzsofttsm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Attendance {               // 考勤

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendanceId",columnDefinition = "VARCHAR(255) COMMENT '考勤id'")
    private String attendanceId;      // 考勤id，

    //@Column(name = "name",columnDefinition = "VARCHAR(255) COMMENT '姓名'")
    //private String name;            // 姓名

    @Column(name = "day",columnDefinition = "date COMMENT '日期'")
    private Date day;          // 日期

    @Column(name = "morningArrive",columnDefinition = "VARCHAR(255) COMMENT '上午到'")
    private String morningArrive;        // 上午到

    @Column(name = "morningRetreat",columnDefinition =  "VARCHAR(255) COMMENT '上午退'")
    private String morningRetreat;       // 上午退

    @Column(name = "afternoonArrive",columnDefinition = "VARCHAR(255) COMMENT '下午到'")
    private String afternoonArrive;     // 下午到

    @Column(name = "afternoonRetreat",columnDefinition = "VARCHAR(255) COMMENT '下午退'")
    private String afternoonRetreat;     // 下午退

    @Column(name = "nightRetreat",columnDefinition = "VARCHAR(255) COMMENT '晚上退'")
    private String nightRetreat;         // 晚上退


}

