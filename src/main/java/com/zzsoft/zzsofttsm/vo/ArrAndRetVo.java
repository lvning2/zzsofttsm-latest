package com.zzsoft.zzsofttsm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ArrAndRetVo {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date day;

    private String arrive;

    private String retreat;

    private Integer dayOfWook;      // 周几


}
