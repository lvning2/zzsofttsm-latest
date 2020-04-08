package com.zzsoft.zzsofttsm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserLog {

    private Long id;     //日志id

    private Integer uid;    //用户id

    private String name;    //用户姓名

    private String todayLog;    //今天内容

    private String yesterdayLog; //昨天内容

    private String gname;   //组名

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date time;    //时间


}
