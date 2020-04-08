package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.TaskPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskPlanRepository extends JpaRepository<TaskPlan,Long> {

    @Query(value = "select * from task_plan where gid=?1 and time>=?2 and time<=?3",nativeQuery = true)
    List<TaskPlan> getTaskPlanByTime(Integer gid, String monday, String sunday);

}

