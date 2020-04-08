package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.Task;
import com.zzsoft.zzsofttsm.entity.TaskPlan;
import com.zzsoft.zzsofttsm.repository.TaskPlanRepository;
import com.zzsoft.zzsofttsm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TaskPlanService {

    @Autowired
    private TaskPlanRepository taskPlanRepository;

    @Autowired
    private TaskRepository taskRepository;

    /**
     * 根据时间查询下周的任务计划,根据计划的time字段查询,需要在创建计划是将时间改为下周
     * @param gid
     * @param date
     * @return
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public List<TaskPlan> getTaskPlanByTime(Integer gid,Date date){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("----"+sdf.format(date));

        Date mondayOfNextWeek = DateUtils.getMondayOfNextWeek(date);
        Date sundayOfNextWeek = DateUtils.getSundayOfNextWeek(date);
        return taskPlanRepository.getTaskPlanByTime(gid,sdf.format(mondayOfNextWeek),sdf.format(sundayOfNextWeek));

    }

    /**
     * 获取某组某周的任务计划
     * @param gid
     * @param time
     * @return
     */
    public List<TaskPlan> getPlanByTime(Integer gid,Date time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date mondayOfThisWeek = DateUtils.getMondayOfThisWeek(time);
        Date sundayOfThisWeek = DateUtils.getSundayOfThisWeek(time);
        List<TaskPlan> taskPlanByTime = taskPlanRepository.getTaskPlanByTime(gid, sdf.format(mondayOfThisWeek), sdf.format(sundayOfThisWeek));
        return taskPlanByTime;
    }


    public void createPlan(Long tid,Integer progress){          //创建已有任务的计划

        Task byId = taskRepository.findById(tid).get();//查询出改任务的信息


        TaskPlan taskPlan=new TaskPlan();
        taskPlan.setTaskid(tid);

        Date d=new Date();

        taskPlan.setCreatetime(d);
        taskPlan.setProgress(progress);

        Date mondayOfNextWeek = DateUtils.getMondayOfNextWeek(d);

        taskPlan.setTime(mondayOfNextWeek);
        taskPlan.setIsNew(0);
        taskPlan.setGid(byId.getGid());
        taskPlan.setContent(byId.getContent());

        taskPlanRepository.save(taskPlan);


    }


    public void deletePlanById(Long id) {           //删除一个任务计划
        TaskPlan one = taskPlanRepository.getOne(id);
        taskPlanRepository.delete(one);
    }

    //    public List<TaskPlan> getNextPlanByGid(Integer gid){
//        Date date = new Date();
//
//        Date mondayOfNextWeek = DateUtils.getMondayOfNextWeek(date);
//        Date sundayOfNextWeek = DateUtils.getSundayOfNextWeek(date);
//        return taskPlanRepository.getTaskPlanByTime(gid,mondayOfNextWeek,sundayOfNextWeek);
//    }

}
