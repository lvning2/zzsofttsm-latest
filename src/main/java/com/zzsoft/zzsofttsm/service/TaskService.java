package com.zzsoft.zzsofttsm.service;

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
import com.zzsoft.zzsofttsm.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PastTaskRepository pastTaskRepository;

    @Autowired
    private TaskPlanRepository taskPlanRepository;

    @Autowired
    private GroupRepository groupRepository;

    /**
     * 根据时间查询所在星期的任务
     * @param gid
     * @param time
     * @return
     */
    public List<Task> getTaskByGroupAndTime(Integer gid, Date time) {

        Date monday= DateUtils.getMondayOfThisWeek(time);
        Date sunday = DateUtils.getSundayOfThisWeek(time);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


        return taskRepository.getTaskByGroupAndTime(gid,sdf.format(monday),sunday);

    }


    /**
     * 创建一个任务
     * @param content
     * @param gid
     * @param time
     */
    public Task createTask(String content,Integer gid,Date time,Integer progress) {
        Task task=new Task();

        task.setContent(content);
        task.setCreateTime(new Date());
        task.setProgress(progress);
        task.setTime(time);
        task.setGid(gid);

        return taskRepository.save(task);
    }

    /**
     * 根据时间查询过往的任务进度详情,截止到每周周日23点
     * @param gid
     * @param time
     * @return
     */
    public List<TaskVo> getPastTaskByGroupAndTime(Integer gid, Date time) {

        Date monday= DateUtils.getMondayOfThisWeek(time);
        Date sunday = DateUtils.getSundayOfThisWeek(time);
        List<PastTask> pastTask = pastTaskRepository.getPastTaskByGroupAndTime(gid, monday, sunday);
        List<TaskVo> list = new ArrayList<>();
        if(pastTask!=null&&pastTask.size()>0) {
            Group one = groupRepository.getOne(pastTask.get(0).getGid());



            for (PastTask x : pastTask) {
                TaskVo taskVo = new TaskVo();
                taskVo.setContent(x.getContent());
                taskVo.setGid(x.getGid());
                taskVo.setGname(one.getGname());
                taskVo.setId(x.getId());
                taskVo.setProgress(x.getProgress());
                taskVo.setTime(x.getTime());
                taskVo.setIsDelay(x.getIsDelay());
                if (TaskUtils.isNew2(x)) {
                    taskVo.setIsNew(1);
                } else {
                    taskVo.setIsNew(0);
                }

                list.add(taskVo);

            }
        }

        return list;


    }



    /**
     * 修改任务,目前只支持修改任务内容和进度
     * @param task
     */
    @Transactional
    public void updateTask(Task task) {
        taskRepository.updateTask(task.getId(),task.getContent(),task.getProgress());
    }

    /**
     * 修改任务的进度
     * @param id
     * @param progress
     */
    @Transactional
    public void updateTaskProgress(Long id, Integer progress) {
        taskRepository.updateTaskProgress(id,progress);
    }

    /**
     * 修改任务的内容
     * @param id
     * @param content
     */
    @Transactional
    public void updateTaskContent(Long id, String content) {
        taskRepository.updateTaskContent(id,content);
    }

    /**
     * 根据任务的id获取任务进度信息
     * @param id
     * @return
     */
    public Task getTaskById(Long id) {
        Optional<Task> byId = taskRepository.findById(id);
        return byId.get();
    }

    /**
     * 根据当前时间获取某组下周任务计划
     * @param gid
     * @param date
     * @return
     */
    public List<TaskPlan> getNextWeekTaskPlan(Integer gid, Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date sunday=DateUtils.getSundayOfNextWeek(date);
        Date monday=DateUtils.getMondayOfNextWeek(date);
        return taskPlanRepository.getTaskPlanByTime(gid,sdf.format(monday),sdf.format(sunday));
    }

    /**
     * 根据当前时间获取某组上周任务计划
     * @param gid
     * @param date
     * @return
     */
    public List<TaskPlan> getLastWeekTaskPlan(Integer gid,Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date sunday=DateUtils.getSundayOfLastWeek(date);
        Date monday=DateUtils.getMondayOfLastWeek(date);
        return taskPlanRepository.getTaskPlanByTime(gid,sdf.format(monday),sdf.format(sunday));
    }

    public List<TaskPlan> getThisWeekTaskPlan(Integer gid,Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date sunday=DateUtils.getSundayOfThisWeek(date);
        Date monday=DateUtils.getMondayOfThisWeek(date);
        return taskPlanRepository.getTaskPlanByTime(gid,sdf.format(monday),sdf.format(sunday));
    }

//    public boolean click(TaskPlan taskPlan,List<PastTask> list){
//        PastTask t = null;
//        for(PastTask pt: list){
//            if(taskPlan.getId()==pt.getId()){
//                t=pt;
//                break;
//            }
//        }
//        if(t.getProgress()<taskPlan.getProgress()){
//            return true;
//        }else {
//            return false;
//        }
//
//
//    }

    public PastTask clicks(TaskPlan taskPlan,List<PastTask> list){
        PastTask t = null;
        for(PastTask pt: list){
            if(taskPlan.getId().equals(pt.getId())){
                return pt;
            }
        }
        return null;


    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void test(){
        updateTaskContent(41L,"aaa");
        updateTaskProgress(41L,88);

    }

    @Transactional
    public void deleteTaskById(Long id) {       //删除一个任务
        Task one = taskRepository.getOne(id);
        taskRepository.delete(one);
    }

}
