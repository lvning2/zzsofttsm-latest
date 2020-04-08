package com.zzsoft.zzsofttsm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@ApiModel(value = "Computer")
public class Computer implements Serializable {     //计算机设备

    @Id
    @GenericGenerator(name="idGenerator", strategy="guid")
    @GeneratedValue(generator="idGenerator")
    @Column(name = "id", length = 64)
    private String id;

    @Column(name ="equipmentNumber" ,columnDefinition = "varchar(255) COMMENT '设备编号'")
    @ApiModelProperty("设备编号-D0491")
    private String equipmentNumber;   //设备编号

    @Column(name ="equipmentType" ,columnDefinition = "varchar(255) COMMENT '设备类型，台式电脑，笔记本电脑'")
    @ApiModelProperty("设备类型 台式电脑，笔记本")
    private String equipmentType;   //设备类型



    @Column(name = "hdCapacity",columnDefinition = "varchar(255) COMMENT '硬盘容量'")
    private String  hdCapacity; // 硬盘容量

    @Column(name = "hdSerialNumber",columnDefinition = "varchar(255) COMMENT '硬盘序列号'")
    private String hdSerialNumber;  // 硬盘序列号

    @Column(name ="ipAddress" ,columnDefinition = "varchar(255) COMMENT 'ip地址'")
    @ApiModelProperty("ip地址")
    private String ipAddress;       //ip地址

    @Column(name ="macAddress" ,columnDefinition = "varchar(255) COMMENT 'mac地址'")
    //@ApiModelProperty("mac地址")
    private String macAddress;      //mac地址

    @Column(name ="osVersion" ,columnDefinition = "varchar(255) COMMENT '操作系统版本'")
    @ApiModelProperty("操作系统版本")
    private String osVersion;       //操作系统版本

    @Column(name ="osInstallTime" ,columnDefinition = "date COMMENT '操作系统安装时间'")
    private Date osInstallTime;              //操作系统安装时间

    @Column(name ="computerUse" ,columnDefinition = "varchar(255) default '办公' COMMENT '用途' ")
    @ApiModelProperty("用途：办公")
    private String computerUse;             //用途

    @Column(name ="setPosition" ,columnDefinition = "varchar(255) COMMENT '放置位置'")
    private String setPosition;     //放置位置

    @Column(name ="useDepartment" ,columnDefinition = "varchar(255) COMMENT '使用部门'")
    private String useDepartment;   //使用部门

    @Column(name ="personLiableId" ,columnDefinition = "int(11) COMMENT '责任人，使用人id'")
    @ApiModelProperty("责任人：id")
    private Integer personLiableId;    //责任人，使用人


    @Column(name ="computerUsage" ,columnDefinition = "varchar(255) default '在用' COMMENT '使用情况 在用，'")
    @ApiModelProperty("使用情况")
    private String computerUsage;           //使用情况 在用，


    @Column(name ="remark" ,columnDefinition = "varchar(255) COMMENT '备注'")
    @ApiModelProperty("备注")
    private String remark;         //备注

    @Column(name = "isNetwork",columnDefinition = "int COMMENT '是否联网  1 是，0 否'")
    private Integer isNetwork; // 是否联网  1 是，0 否

    @Column(name = "brandModel",columnDefinition = "varchar(255) COMMENT '品牌型号'")
    private String brandModel;  // 品牌型号


    @Column(name ="cpuName" ,columnDefinition = "varchar(255) COMMENT 'cpu名称'")
    @ApiModelProperty("cpuName")
    private String cpuName;     //cpu

    @Column(name ="graphicsCardName" ,columnDefinition = "varchar(255) COMMENT '显卡名称'")
    @ApiModelProperty("显卡")
    private String graphicsCardName;    //显卡

    @Column(name ="memory" ,columnDefinition = "varchar(255) COMMENT '内存,单位G'")
    @ApiModelProperty("内存大小，8,16,32,单位G")
    private String memory;         //内存

    @Column(name = "lockNumber",columnDefinition = "varchar(255) COMMENT '锁号'")
    private String lockNumber;    // 锁号

    @Column(name ="monitor" ,columnDefinition = "varchar(255) COMMENT '显示器'")
    @ApiModelProperty("显示器，21寸")
    private String monitor;     //显示器




    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name ="createtime" ,columnDefinition = "date COMMENT '设备创建（添加）时间'")
    private Date createtime;        //设备创建（添加）时间

    @Column(name ="state" ,columnDefinition = "int(11) COMMENT '状态，0 正常使用 ，1删除'")
    private Integer state;          //状态，0 正常使用 ，1删除

}




