package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.PastTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PastTaskRepository extends JpaRepository<PastTask,Long> {

    @Query(value = "select * from past_task where gid=?1 and time>=?2 and time<=?3",nativeQuery = true)
    List<PastTask> getPastTaskByGroupAndTime(Integer gid, Date starttime, Date endtime);

}
