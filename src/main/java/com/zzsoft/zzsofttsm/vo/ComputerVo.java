package com.zzsoft.zzsofttsm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ComputerVo {

    private String id;

    private String equipmentNumber;   //设备编号

    private String equipmentType;   //设备类型

    private String  hdCapacity; // 硬盘容量

    private String  hdCapacity1; // 硬盘容量  机械
    private String  hdCapacity2; // 硬盘容量  固态


    private String hdSerialNumber;  // 硬盘序列号

    private String ipAddress;       //ip地址

    private String macAddress;      //mac地址

    private String osVersion;       //操作系统版本

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date osInstallTime;              //操作系统安装时间

    private String computerUse;             //用途

    private String setPosition;     //放置位置

    private String useDepartment;   //使用部门

    private Integer personLiableId;    //责任人id，使用人

    private String personName;      // 责任人姓名

    private String computerUsage;           //使用情况 在用，


    private String remark;         //备注

    private Integer isNetwork; // 是否联网  1 是，0 否

    private String brandModel;  // 品牌型号


    private String cpuName;     //cpu

    private String graphicsCardName;    //显卡

    private String memory;         //内存

    private String lockNumber;    // 锁号

    private String monitor;     //显示器


    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createtime;        //设备创建（添加）时间

}



