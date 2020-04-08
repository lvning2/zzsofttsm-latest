package com.zzsoft.zzsofttsm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class HardDisk implements Serializable {         //硬盘设备

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "HdSerialNumber",columnDefinition = "varchar(255) COMMENT '硬盘序列号'")
    private String HdSerialNumber;  //硬盘序列号

    @Column(name = "HdCapacity",columnDefinition = "int COMMENT '硬盘容量,单位G'")
    private Integer HdCapacity;     //硬盘容量,单位G

    @Column(name = "isSolid",columnDefinition = "int COMMENT '是否固态 1 是，0 否'")
    private Boolean isSolid;    //是否固态 1 是，0 否

    @Column(name = "createtime",columnDefinition = "int COMMENT '创建时间'")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createtime;            //创建时间

}


