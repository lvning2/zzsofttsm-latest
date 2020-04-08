
package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.Group;
import com.zzsoft.zzsofttsm.entity.Task;
import com.zzsoft.zzsofttsm.entity.TaskPlan;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.service.GroupService;
import com.zzsoft.zzsofttsm.service.TaskPlanService;
import com.zzsoft.zzsofttsm.service.TaskService;
import com.zzsoft.zzsofttsm.vo.GroupTaskVo;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import com.zzsoft.zzsofttsm.vo.TaskPlanVo;
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
@RequestMapping("/plan")
@CrossOrigin(maxAge = 3600)
@Api(tags = "任务计划信息接口")
public class PlanController {

    @Autowired
    private TaskPlanService taskPlanService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TaskService taskService;

    /**
     * 根据当前时间获取下周的任务计划
     */
    @PostMapping("/getNestTaskPlan")
    @ApiOperation("根据组id获取当前时间的下周任务计划")     //
    public ResultVo getNextPlan(@ApiParam("组id") @RequestParam Integer gid,@RequestParam String time, HttpServletRequest request){

        try {

            //User user = (User)request.getSession().getAttribute("user");

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(time);

            List<TaskPlan> taskPlans = taskPlanService.getTaskPlanByTime(gid,parse);

            List<TaskPlanVo> list=new ArrayList();
            List list1 = getList(taskPlans, list);

            return new ResultVo(0,"获取成功",list1.size(),list1);

        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }

    @PostMapping("/getNestTaskPlan2")
    @ApiOperation("根据组id获取当前时间的下周任务计划--测试")     //@ApiParam("组id") @RequestParam Integer gid,
    public ResultVo getNextPlan2(@RequestParam String time, HttpServletRequest request){

        ResultVo resultVo=new ResultVo();
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User user = (User)session.getAttribute("user");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(time);

            List<TaskPlan> taskPlans = taskPlanService.getTaskPlanByTime(user.getGid(),parse);

            List<TaskPlanVo> list=new ArrayList();

            List list1 = getList(taskPlans, list);
            return new ResultVo(0,"获取成功",0,list1);

        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }




    //@PostMapping("/getNextPlanByTimeAndGid")                //根据组id和某个时间所在的星期查询下周任务计划
    //@ApiOperation("根据组id和时间，查询该时间所在星期的下周任务计划")
    public ResultVo getNextPlanByTime(@ApiParam("组id")@RequestParam Integer gid,@ApiParam("时间，格式：2019-12-01") @RequestParam String time) throws ParseException {
        ResultVo resultVo=new ResultVo();
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(time);
            List<TaskPlan> taskPlans = taskPlanService.getTaskPlanByTime(gid, parse);

            List<TaskPlanVo> list=new ArrayList<>();
            List list1 = getList(taskPlans, list);

            return new ResultVo(0,"第"+gid+"组"+time+"的下周计划",list1.size(),list1);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }

    @PostMapping("/createPlan")
    @ApiOperation("创建任务计划，对已有任务创建计划")
    public ResultVo createPlan(@ApiParam("任务id")@RequestParam Long tid,@ApiParam("任务进度") @RequestParam Integer progress){

        try {
            taskPlanService.createPlan(tid,progress);
            return new ResultVo(0,"创建成功",0,null);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }
    @PostMapping("/createNewPlan")
    @ApiOperation("创建任务计划，新任务")
    public ResultVo createNewPlan(@ApiParam("任务内容") @RequestParam String content,@ApiParam("计划任务进度") @RequestParam Integer progress,@ApiParam("组id") @RequestParam Integer gid){

        try {

            Date date=new Date();
            Date mondayOfNextWeek = DateUtils.getMondayOfNextWeek(date);
            Task task = taskService.createTask(content, gid, mondayOfNextWeek, 0);
            taskPlanService.createPlan(task.getId(),progress);
            return new ResultVo(0,"创建成功",0,null);

        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }



    @PostMapping("/getPlanByTime")
    @ApiOperation("获取某组某周的任务计划")
    public ResultVo getPlanByTime(@ApiParam("组id")@RequestParam Integer gid,@ApiParam("时间，格式：2019-12-01") @RequestParam String time) throws ParseException {//获取某组某周的任务计划
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(time);
            List<TaskPlan> planByTime = taskPlanService.getPlanByTime(gid, parse);
            List<TaskPlanVo> list=new ArrayList<>();

            List list1 = getList(planByTime, list);

            return new ResultVo(0,"第"+gid+"组"+time+"的任务计划",list1.size(),list1);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }

    @GetMapping("/getAllGroupTaskPlanThisWeek")
    @ApiOperation("获取所有组某一时间的下周计划---经理")
    public ResultVo getAllGroupTaskPlanThisWeek(@ApiParam("时间，格式：2019-12-01") @RequestParam String time) throws ParseException {

        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(time);

            List<Group> all = groupService.getAll();
            List list=new ArrayList();

            for (Group x:all){

                List<TaskPlan> planByTime = taskPlanService.getTaskPlanByTime(x.getId(), parse);
                GroupTaskVo groupTaskVo=new GroupTaskVo();
                groupTaskVo.setGname(x.getGname());
                groupTaskVo.setGid(x.getId());
                groupTaskVo.setTask(planByTime);

                list.add(groupTaskVo);

            }

            return new ResultVo(0,"获取成功",list.size(),list);

        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }




    }


    private List getList(List<TaskPlan> taskPlans, List<TaskPlanVo> list) {

        Group byId = groupService.getById(taskPlans.get(0).getGid());

        for(TaskPlan tp:taskPlans){
            TaskPlanVo t=new TaskPlanVo();

            if(DateUtils.isThisWeek(tp.getCreatetime().getTime())){
                t.setIsNew(1);
            }else{
                t.setIsNew(0);
            }
            t.setContent(tp.getContent());
            t.setCreatetime(tp.getCreatetime());
            t.setProgress(tp.getProgress());
            t.setGid(tp.getGid());
            t.setId(tp.getId());
            t.setTime(tp.getTime());
            t.setTaskid(tp.getTaskid());
            t.setGname(byId.getGname());
            list.add(t);
        }

        return list;
    }

    @PostMapping("/deletePlanById")
    @ApiOperation("删除一个任务计划")
    public ResultVo deletePlanById(@ApiParam("任务计划id") @RequestParam Long id){
        taskPlanService.deletePlanById(id);
        return new ResultVo(0,"删除成功",0,null);
    }



}

