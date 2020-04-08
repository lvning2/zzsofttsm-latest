package com.zzsoft.zzsofttsm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@ToString
public class WorkLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private Integer uid;  //用户id

    @Column(name = "time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date time;      //日志时间

//    @Column(name = "logContent")
//    private String logContent;  //日志内容

    @Column(name = "todayLog",columnDefinition = "varchar(4096)")
    private String todayLog;    //今天内容

    @Column(name = "yesterdayLog",columnDefinition = "varchar(4096)")
    private String yesterdayLog; //昨天内容


}

