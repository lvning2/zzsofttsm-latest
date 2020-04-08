package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.entity.Group;
import com.zzsoft.zzsofttsm.service.AttendanceService;
import com.zzsoft.zzsofttsm.service.GroupService;
import com.zzsoft.zzsofttsm.service.TaskService;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin(maxAge = 3600)
public class TestController {

    private final GroupService groupService;

    private final TaskService taskService;

    private final AttendanceService attendanceService;

    public TestController(GroupService groupService, TaskService taskService, AttendanceService attendanceService) {
        this.groupService = groupService;
        this.taskService = taskService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/test1")
    public ResultVo test(){
        List<Group> all = groupService.getAll();
        return new ResultVo(0,"测试",all.size(),all);
    }

    @PostMapping("test2")
    public ResultVo test2(){

        taskService.test();
        return new ResultVo(0,"",0,null);

    }

    @GetMapping("/TriggerError")
    public ResultVo test3(){
        int b=10/0;
        return new ResultVo();
    }

    @RequestMapping(value = "/unauth",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultVo unauth(){
        return new ResultVo(1,"没有权限",0,null);
    }

    @GetMapping("/testa")
    public ResultVo getA(String attendanceId,String day) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return new ResultVo(0,"测试",0,attendanceService.getAttendanceByAttendanceIdAndDay(attendanceId,sdf.parse(day)));
    }


}


