package com.zzsoft.zzsofttsm.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@ToString
@Data
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "problemContent",columnDefinition = "varchar(4096)")
    private String problemContent;

    @Column(name = "time")
    private Date time;

    @Column(name = "gid")
    private Integer gid;


}
