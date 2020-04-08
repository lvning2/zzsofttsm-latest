package com.zzsoft.zzsofttsm.schedule;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.common.TaskUtils;
import com.zzsoft.zzsofttsm.entity.Group;
import com.zzsoft.zzsofttsm.entity.PastTask;
import com.zzsoft.zzsofttsm.entity.Task;
import com.zzsoft.zzsofttsm.entity.TaskPlan;
import com.zzsoft.zzsofttsm.repository.GroupRepository;
import com.zzsoft.zzsofttsm.repository.PastTaskRepository;
import com.zzsoft.zzsofttsm.repository.TaskPlanRepository;
import com.zzsoft.zzsofttsm.repository.TaskRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 定时任务
 */
@Service
public class TaskSchedule {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PastTaskRepository pastTaskRepository;

    @Autowired
    private TaskPlanRepository taskPlanRepository;

    Logger logger=LoggerFactory.getLogger(TaskSchedule.class);

    /**
     * 每周日晚上22点执行
     * 统计本周的任务进行详情,用于查询过往日期的任务进度详情
     */
    @Scheduled(cron="0 0 22 ? * 7")     //每周日晚上10点执行一次
    public void Statistics(){

        //System.out.println("开始统计本周任务完成情况...");
        Date d=new Date();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        //System.out.println("当前时间:"+sdf.format(d));

        Date monday = DateUtils.getMondayOfThisWeek(d);
        Date sunday = DateUtils.getSundayOfThisWeek(d);
        String loggerStr="统计本周任务完成情况,时间区间:"+sdf.format(monday)+"--"+sdf.format(sunday);
        //System.out.println();

        List<Group> groups = groupRepository.findAll();


        for(Group x:groups){
            List<Task> tasks = taskRepository.getTaskByGroupAndTime(x.getId(), sdf.format(monday), sunday);     //查出每组截止目前为止的所有任务
            for(Task y:tasks){
                PastTask pastTask=new PastTask();

                pastTask.setContent(y.getContent());
                pastTask.setCreateTime(y.getCreateTime());
                pastTask.setGid(y.getGid());
                pastTask.setProgress(y.getProgress());
                pastTask.setTime(y.getTime());
                pastTask.setIsDelay(y.getIsDelay());
                if(TaskUtils.isNew(y)){
                    pastTask.setIsNew(1);
                }else {
                    pastTask.setIsNew(0);
                }
                pastTaskRepository.save(pastTask);
            }
        }
        //System.out.println("统计结束...");
        logger.info(loggerStr);
    }



    /**
     * 每周一晚上1点执行
     * 进行延期处理
     *
     */
    @Scheduled(cron="0 0 2 ? * MON")          //每周一凌晨2点执行一次
    @Transactional
    public void update1(){
        //System.out.println("开始进行延期处理...");

        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH,-1);    //减一天
        Date d = c.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(c.getTime()));

        Date monday = DateUtils.getMondayOfThisWeek(d);
        Date sunday = DateUtils.getSundayOfThisWeek(d);

        System.out.println(sdf.format(monday)+" "+sdf.format(sunday));

        List<Group> groups = groupRepository.findAll();
        for(Group x:groups){
            List<Task> tasks = taskRepository.getTaskByGroupAndTime(x.getId(), sdf.format(monday), sunday);     //查出每组截止目前为止的所有任务
            List<TaskPlan> taskPlans = taskPlanRepository.getTaskPlanByTime(x.getId(), sdf.format(monday), sdf.format(sunday));//查出每组截止目前为止的所有计划任务
            for(Task y:tasks){
                System.out.println(y.getId());
                for(TaskPlan z:taskPlans) {
                    if (y.getId() == z.getTaskid()) {
                        if (y.getProgress() < z.getProgress()) {
                            taskRepository.updateTaskIsDelay(y.getId(), 1);
                        } else {
                            taskRepository.updateTaskIsDelay(y.getId(), 0);
                        }
                        break;
                    }
                }

            }

            for(Task t:tasks){
                if(t.getProgress()<100){

                    Calendar c1=Calendar.getInstance();
                    c1.setTime(new Date());
                    c1.add(Calendar.DAY_OF_MONTH,2);    //加两天

                    taskRepository.updateTaskTime(t.getId(),c1.getTime());
                    //taskRepository.updateTaskTime(t.getId(),new Date());
                }
            }

        }

        //System.out.println("延期处理完成...");
        logger.info("进行延期处理........处理结束。");

    }





}



