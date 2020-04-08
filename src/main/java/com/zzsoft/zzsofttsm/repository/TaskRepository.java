package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query(value = "select * from task where gid=?1 and time>=?2 and time<=?3",nativeQuery = true)
    List<Task> getTaskByGroupAndTime(Integer gid, String starttime,Date endtime);



    @Modifying
    @Query(value = "update task set content=?2,progress=?3 where id=?1",nativeQuery = true)
    void updateTask(Long id,String content,Integer progress);

    @Modifying
    @Query(value = "update task set progress=?2 where id=?1",nativeQuery = true)
    void updateTaskProgress(Long id,Integer progress);

    @Modifying
    @Query(value = "update task set content=?2 where id=?1",nativeQuery = true)
    void updateTaskContent(Long id,String content);


    @Modifying
    @Query(value = "update task set time=?2 where id=?1" ,nativeQuery = true)
    void updateTaskTime(Long id,Date date);      //更新任务的时间,用于系统定时任务,检查任务是否延期,作废



    @Modifying
    @Query(value = "update task set is_delay=?2 where id=?1" ,nativeQuery = true)
    void updateTaskIsDelay(Long id, Integer state);

}


