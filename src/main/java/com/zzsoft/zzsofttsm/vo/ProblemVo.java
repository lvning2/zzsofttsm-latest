package com.zzsoft.zzsofttsm.vo;

import com.zzsoft.zzsofttsm.entity.Problem;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProblemVo {

    private Integer gid;

    private String gname;

    private List<Problem> problems;

}
