package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.common.TaskUtils;
import com.zzsoft.zzsofttsm.entity.*;
import com.zzsoft.zzsofttsm.service.GroupService;
import com.zzsoft.zzsofttsm.service.LoginService;
import com.zzsoft.zzsofttsm.service.TaskPlanService;
import com.zzsoft.zzsofttsm.service.TaskService;
import com.zzsoft.zzsofttsm.vo.GroupTaskVo;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import com.zzsoft.zzsofttsm.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/task")
@CrossOrigin(maxAge = 3600)
@Api(tags = "任务信息接口")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TaskPlanService taskPlanService;


    @GetMapping("/get")
    @ApiOperation("根据组id，获取当前周的任务")         //
    public ResultVo get(@ApiParam("组id") @RequestParam Integer gid, HttpServletRequest request) throws ParseException {  //根据组id,和当前时间获取任务

        List<Task> taskByGroupAndTime = taskService.getTaskByGroupAndTime(gid, new Date());
        List list = getList(taskByGroupAndTime);
        return new ResultVo(0,"获取成功",list.size(),list);

    }

    @GetMapping("/get2")
    @ApiOperation("根据组id，获取当前周的任务----测试")         //@ApiParam("组id") @RequestParam Integer gid,
    public ResultVo get2( HttpServletRequest request) throws ParseException {  //根据组id,和当前时间获取任务

        //SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");

        List<Task> taskByGroupAndTime = taskService.getTaskByGroupAndTime(user.getGid(), new Date());
        List list=null;
        if(taskByGroupAndTime!=null&&taskByGroupAndTime.size()>0){
            list = getList(taskByGroupAndTime);
        }

        return new ResultVo(0,"获取成功",list.size(),list);
    }


    @PostMapping("/getPast")
    @ApiOperation("根据组id，获取某周的任务信息，（获取过往周的任务信息，截止到周日晚上10点）")
    public ResultVo getPastTask(@ApiParam("组id") @RequestParam Integer gid, @ApiParam("时间 格式：2019-12-20") @RequestParam String date) throws ParseException {             //获取过往的任务进度详情,截止到每周日22点

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(date);
        List<TaskVo> pastTaskByGroupAndTime = taskService.getPastTaskByGroupAndTime(gid, d);
        return new ResultVo(0,"获取成功",pastTaskByGroupAndTime.size(),pastTaskByGroupAndTime);
    }

    @PostMapping("/create")
    @ApiOperation("在本周创建一个新任务")
    public ResultVo createTask(@ApiParam("任务内容") @RequestParam String content, @ApiParam("组id") @RequestParam Integer gid) {     //在本周创建一个新任务

        try {
            taskService.createTask(content, gid, new Date(), 0);
            return new ResultVo(0,"创建任务成功",0,null);
        } catch (Exception e) {
            return new ResultVo(1,e.getMessage(),0,null);
        }
    }


    @PostMapping("/test/update/{id}/{progress}")
    public Task updateTask(@PathVariable Long id, @PathVariable Integer progress) {             //更新任务的进度、内容
        Task task = new Task();
        task.setId(id);
        task.setContent("测试");
        task.setProgress(progress);

        taskService.updateTask(task);

        return taskService.getTaskById(id);

    }

    @PostMapping("/update/progress")
    @ApiOperation("根据任务id更新任务进度")
    public ResultVo updateTaskProgress(@ApiParam("任务id") @RequestParam Long id, @ApiParam("任务进度") @RequestParam Integer progress) {   //更新任务的进度
        taskService.updateTaskProgress(id, progress);
        return new ResultVo(0,"更新成功",0,null);
    }

    @PostMapping("/update/content/{id}/{content}")
    public void updateTaskContent(@PathVariable Long id, @PathVariable String content) {      //更新任务的内容
        taskService.updateTaskContent(id, content);
    }

    @GetMapping("/getAllGroupTaskOfThisWeek")
    @ApiOperation("获取所有组的本周任务（成果），，经理专用")
    public ResultVo getAllGroupTaskOfThisWeek() throws ParseException {
        try {
            List<Group> all = groupService.getAll();
            List list = new ArrayList();

            for (Group x : all) {
                List<Task> tasks = taskService.getTaskByGroupAndTime(x.getId(), new Date());
                if (tasks != null && tasks.size() > 0) {
                    List l = getList(tasks);
                    list.add(l);
                } else {
                    List zz = new ArrayList(0);
                    list.add(zz);
                }
            }
            return new ResultVo(0,"获取成功",list.size(),list);
        } catch (Exception e) {
            return new ResultVo(1,e.getMessage(),0,null);
        }


    }

    @GetMapping("/getAllGroupTaskPastOfThisWeek")
    @ApiOperation("获取所有组的过往任务（成果），，经理专用")
    public ResultVo getAllGroupTaskPastOfThisWeek(@ApiParam("时间") @RequestParam String time) throws ParseException {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(time);
            List<Group> all = groupService.getAll();
            List list = new ArrayList();

            for (Group x : all) {
                List<TaskVo> pastTaskByGroupAndTime = taskService.getPastTaskByGroupAndTime(x.getId(), parse);
                if (pastTaskByGroupAndTime != null && pastTaskByGroupAndTime.size() > 0) {
                    list.add(pastTaskByGroupAndTime);
                } else {
                    List zz = new ArrayList(0);
                    list.add(zz);
                }
            }

            return new ResultVo(0,"获取成功",list.size(),list);
        } catch (Exception e) {
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }

    @GetMapping("/getAllGroupTask")
    @ApiOperation("获取所有组的本周任务信息，（根据时间自动识别本周或是过往）经理专用")
    public ResultVo getAllGroupTaskOfWeek(@ApiParam("时间") @RequestParam String time) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(time);
        boolean inWeek = DateUtils.isInWeek(parse, new Date());
        List<Group> all = groupService.getAll();

        if (inWeek) {
            List list = new ArrayList();

            for (Group x : all) {
                List<Task> tasks = taskService.getTaskByGroupAndTime(x.getId(), new Date());
                GroupTaskVo groupTaskVo = new GroupTaskVo();
                groupTaskVo.setGid(x.getId());
                groupTaskVo.setGname(x.getGname());
                groupTaskVo.setTask(tasks);
                list.add(groupTaskVo);
//                if(tasks!=null&&tasks.size()>0){
//
//                    List l = getList(tasks);
//
//                    list.add(l);
//
//                }else {
//                    List zz=new ArrayList(0);
//                    list.add(zz);
//                }

            }

            return new ResultVo(0,"获取成功",list.size(), list);

        } else {
            List list = new ArrayList();

            for (Group x : all) {
                List<TaskVo> pastTaskByGroupAndTime = taskService.getPastTaskByGroupAndTime(x.getId(), parse);
                GroupTaskVo groupTaskVo = new GroupTaskVo();
                groupTaskVo.setGid(x.getId());
                groupTaskVo.setGname(x.getGname());
                groupTaskVo.setTask(pastTaskByGroupAndTime);
                list.add(groupTaskVo);


//                if(pastTaskByGroupAndTime!=null&&pastTaskByGroupAndTime.size()>0){
//
//                    list.add(pastTaskByGroupAndTime);
//
//                }else {
//                    List zz=new ArrayList(0);
//                    list.add(zz);
//                }


            }

            return new ResultVo(0,"获取成功",list.size(), list);
        }


    }


    //@ExceptionHandler
    public ResultVo exception(Exception e) {
        return new ResultVo(1,e.getMessage(),0,null);
    }

    private List getList(List<Task> list) {

        List<TaskVo> l = new ArrayList<>();
        Group byId = groupService.getById(list.get(0).getGid());
        for (Task x : list) {
            TaskVo taskVo = new TaskVo();
            taskVo.setId(x.getId());
            taskVo.setTime(x.getTime());
            taskVo.setProgress(x.getProgress());
            taskVo.setIsDelay(x.getIsDelay());
            taskVo.setGid(x.getGid());
            taskVo.setContent(x.getContent());
            taskVo.setGname(byId.getGname());
            if (TaskUtils.isNew(x)) {
                taskVo.setIsNew(1);
            } else {
                taskVo.setIsNew(0);
            }
            l.add(taskVo);

        }
        return l;
    }


    @GetMapping("/getTaskByTime")
    @ApiOperation("获取任务信息（根据时间自动识别本周或是过往）")
    public ResultVo getTask(@ApiParam("组id") @RequestParam Integer gid, @ApiParam("时间") @RequestParam String time) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(time);

        boolean inWeek = DateUtils.isInWeek(parse, new Date());
        if (inWeek) {
            List<Task> taskByGroupAndTime = taskService.getTaskByGroupAndTime(gid, new Date());
            List list = null;
            if(taskByGroupAndTime!=null&&taskByGroupAndTime.size()>0){
                list = getList(taskByGroupAndTime);
                return new ResultVo(0,"获取成功",list.size(),list);
            }
            return new ResultVo(0,"获取成功",0,list);
        } else {
            List<TaskVo> pastTaskByGroupAndTime = taskService.getPastTaskByGroupAndTime(gid, parse);
            return new ResultVo(0,"获取成功",pastTaskByGroupAndTime.size(),pastTaskByGroupAndTime);
        }

    }

    @GetMapping("/getTaskOfThisWeekAndPlanOfNextWeek")
    @ApiOperation("经理使用---获取本周的任务以及下周的计划,根据时间自动识别本周或是过往")
    public ResultVo getTaskOfThisWeekAndPlanOfNextWeek(@ApiParam("时间") @RequestParam String time) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(time);

        boolean inWeek = DateUtils.isInWeek(parse, new Date());
        List<Group> all = groupService.getAll();
        List list = new ArrayList();
        if (inWeek) {
            for (Group x : all) {
                List<Task> tasks = taskService.getTaskByGroupAndTime(x.getId(), new Date());
                GroupTaskVo groupTaskVo = new GroupTaskVo();
                groupTaskVo.setGid(x.getId());
                groupTaskVo.setGname(x.getGname());
                groupTaskVo.setTask(tasks);

                List<TaskPlan> planByTime = taskPlanService.getTaskPlanByTime(x.getId(), parse);
                groupTaskVo.setPlan(planByTime);

                //tasklist.add(groupTaskVo);

                list.add(groupTaskVo);

            }
            return new ResultVo(0,"获取成功",list.size(),list);

        } else {
            //List tasklist = new ArrayList();
            for (Group x : all) {


                List<TaskVo> pastTaskByGroupAndTime = taskService.getPastTaskByGroupAndTime(x.getId(), parse);
                GroupTaskVo groupTaskVo = new GroupTaskVo();
                groupTaskVo.setGid(x.getId());
                groupTaskVo.setGname(x.getGname());
                groupTaskVo.setTask(pastTaskByGroupAndTime);
                System.out.println("---------"+taskPlanService);
                List<TaskPlan> planByTime = taskPlanService.getTaskPlanByTime(x.getId(), parse);
                groupTaskVo.setPlan(planByTime);

                //tasklist.add(groupTaskVo);

                list.add(groupTaskVo);

            }
            return new ResultVo(0,"获取成功",list.size(),list);
        }

    }

    @PostMapping("/deleteTaskById")
    @ApiOperation("删除一个任务")
    public ResultVo deleteByTaskById(@ApiParam("任务id") @RequestParam Long id){
        taskService.deleteTaskById(id);
        return new ResultVo(0,"删除成功",0,null);
    }


}