package com.zzsoft.zzsofttsm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;


import java.util.Date;

@Data
@ToString
public class TaskPlanVo {

    private Long id;

    private Long taskid;    //任务id

    private String content; //任务计划内容

    private Integer progress;   //计划完成进度

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date time;          //时间

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createtime;       //创建时间

    private Integer gid;    //组id

    private String gname;   //组名

    private Integer isNew;  //是否新增

    private Integer isDelay;        //是否延期      1是, 0否



}
