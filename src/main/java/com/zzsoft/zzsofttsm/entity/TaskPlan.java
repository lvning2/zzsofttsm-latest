package com.zzsoft.zzsofttsm.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
public class TaskPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taskid")
    private Long taskid;    //任务id

    @Column(name = "content",columnDefinition = "varchar(1024)")
    private String content; //任务计划内容

    @Column(name = "progress")
    private Integer progress;   //计划完成进度

    @Column(name = "time")
    private Date time;          //时间

    @Column(name = "createtime")
    private Date createtime;       //创建时间

    @Column(name = "gid")
    private Integer gid;    //组id

    @Column(name = "isNew")
    private Integer isNew;  //是否新增

    private Integer isDelay; //是否延期

}
