package com.zzsoft.zzsofttsm.mapper;

import com.zzsoft.zzsofttsm.entity.WorkLog;
import com.zzsoft.zzsofttsm.vo.WorkLogVo;

import java.util.ArrayList;
import java.util.List;

public class WorklogMapper {

    public static WorkLogVo toVo(WorkLog workLog){
        WorkLogVo workLogVo = CGlibMapper.mapper(workLog, WorkLogVo.class);
        return workLogVo;
    }

    public static List<WorkLogVo> toVoList(List<WorkLog> workLogs){
        List<WorkLogVo> workLogVos = new ArrayList<>();
        for (WorkLog workLog : workLogs){
            WorkLogVo workLogVo = toVo(workLog);
            workLogVos.add(workLogVo);
        }
        return workLogVos;
    }



}
