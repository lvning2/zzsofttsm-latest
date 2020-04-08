package com.zzsoft.zzsofttsm.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@Entity
public class PastTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //id

    @Column(name = "content",columnDefinition = "varchar(1024)")
    private String content;     //任务内容

    @Column(name = "progress")
    private Integer progress;      //任务完成进度

    @Column(name = "time")
    private Date time;          //当前任务时间

    @Column(name = "gid")
    private Integer gid;        //任务所属组id

    @Column(name = "createtime")
    private Date createTime;       //任务创建时间

    @Column(name = "isDelay")
    private Integer isDelay;        //是否延期      1是, 0否

    @Column(name = "isNew")
    private Integer isNew;        //是否新增    1是, 0否

}
