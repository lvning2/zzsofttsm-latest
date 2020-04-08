package com.zzsoft.zzsofttsm.common;

import com.zzsoft.zzsofttsm.entity.PastTask;
import com.zzsoft.zzsofttsm.entity.Task;


import java.util.Date;

public class TaskUtils {

    /**
     * 判断任务是否为计划外新增,规定:创建时间和所在时间是否在同一周,若是则为新增
     * @param task
     * @return
     */
    public static boolean isNew(Task task){

        Date createTime = task.getCreateTime();
        Date time = task.getTime();
        return DateUtils.isInWeek(createTime,time);

    }

    public static boolean isNew2(PastTask pastTask){

        Date createTime = pastTask.getCreateTime();
        Date time = pastTask.getTime();
        return DateUtils.isInWeek(createTime,time);

    }

}
