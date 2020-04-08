package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog,Long>, JpaSpecificationExecutor {


    @Query(value = "select * from work_log w where w.uid=?1 and w.time=?2",nativeQuery = true)
    List<WorkLog> findByUidAndTimeOrderByTimeAsc(Integer uid, String time);

    List<WorkLog> findByTimeBetweenAndUidOrderByTimeAsc(Date monday,Date sunday,Integer uid);

    @Modifying
    @Query(value = "update WorkLog w set w.todayLog=?2,w.yesterdayLog=?3 where w.id=?1")
    void updateContentById(Long id,String today,String yesterday);


    List<WorkLog> findByUidAndTimeBetweenOrderByTimeAsc(Integer uid,Date startTime,Date endTime);

    WorkLog findByUidAndAndTime(Integer uid,Date time);

}




