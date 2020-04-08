package com.zzsoft.zzsofttsm.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class HistoryVo {

    private Integer id;     //id

    private String equipmentId;     //设备id

    private String name;        // 使用者姓名

    private String username;    //  使用者

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;     //开始时间

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;       //结束时间

    private String other;       //备注


}

