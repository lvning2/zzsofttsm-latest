package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.entity.WorkLog;
import com.zzsoft.zzsofttsm.mapper.WorklogMapper;
import com.zzsoft.zzsofttsm.service.GroupService;
import com.zzsoft.zzsofttsm.service.UserService;
import com.zzsoft.zzsofttsm.service.WorkLogService;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import com.zzsoft.zzsofttsm.vo.WorkLogVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/log")
@CrossOrigin(maxAge = 3600)
@Api(tags = "工作日志有关接口")
//@RequiresAuthentication()
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    @ApiOperation("创建工作日志，覆盖创建")
    public ResultVo createWorkLog(@ApiParam("用户id")@RequestParam Integer uid,@ApiParam("今天内容") @RequestParam String todayLog,@ApiParam("时间") @RequestParam String time,@ApiParam("昨天内容") @RequestParam String yesterdayLog, HttpServletRequest request) throws ParseException {      //创建工作日志

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);

        WorkLog workLog=new WorkLog();
        workLog.setTodayLog(todayLog);
        workLog.setYesterdayLog(yesterdayLog);
        workLog.setTime(date);
        workLog.setUid(uid);
        workLogService.createWorkLog(workLog);
        return new ResultVo(0,"创建成功",0,null);

    }

    @PostMapping("/delete")
    @ApiOperation("删除一条日志")
    public ResultVo delete(@ApiParam("日志id")@RequestParam Long id){
        try {
            workLogService.delete(id);
            return new ResultVo(0,"删除成功",0,null);
        } catch (Exception e) {
            return new ResultVo(1,"删除失败",0,null);
        }

    }

    @PostMapping("/updateLog")
    public ResultVo updateLog(@ApiParam("日志id") @RequestParam Long id,@ApiParam("今天内容") @RequestParam String todayLog,@ApiParam("昨天内容") @RequestParam String yesterdayLog){
        workLogService.updateLogById(id,todayLog,yesterdayLog);
        return new ResultVo(0,"修改成功",0,null);
    }

    @ApiOperation("获取某人某日的工作日志")
    @PostMapping("/getOne")
    public ResultVo getByTime(@ApiParam("用户id") @RequestParam Integer uid,@ApiParam("时间，格式：2019-12-20") @RequestParam String time) throws ParseException {                //根据时间查询某个人的日志
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(time);
        List<WorkLog> workLogs = workLogService.selectWorkLogByUidAndTime(uid, time);

        return new ResultVo(0,"获取成功",workLogs.size(),workLogs);

    }

    @PostMapping("/getMyself")
    public ResultVo getMyself(@RequestParam String time , HttpServletRequest request) throws ParseException {                //根据时间查询自己的个人的日志
        User user = (User)request.getSession().getAttribute("user");

        if(user==null){
            return new ResultVo(1,"没有登录，请登录",0,null);
        }

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(time);
        //WorkLog workLog = workLogService.selectWorkLogByUidAndTime(user.getId(), time);
        List<WorkLog> workLogs = workLogService.selectWorkLogByUidAndTime(user.getId(), time);
        return new ResultVo(0,"获取成功",workLogs.size(),workLogs);

    }

    @ApiOperation("获取某人某一周的工作日志")
    @PostMapping("/getOneOfWeek")
    public ResultVo getLogOfWork(@ApiParam("用户id") @RequestParam Integer uid,@ApiParam("时间，该周的第一天，格式 2019-12-20") @RequestParam String time) throws ParseException {        //根据时间查询某人该周的所有日志

        User user = userService.getUserById(uid);

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(time);
        List<WorkLog> list = workLogService.getOfThisWeek(uid, d);
        List<WorkLogVo> workLogVos = WorklogMapper.toVoList(list);
        for(WorkLogVo workLogVo:workLogVos){
            workLogVo.setName(user.getName());
        }

        return new ResultVo(0,user.getName(),workLogVos.size(),workLogVos);
    }

    @ApiOperation("更新工作日志内容")
    @PostMapping("/update")
    public ResultVo updateLogById(@ApiParam("日志id") @RequestParam Long id,@ApiParam("今天内容") @RequestParam String todayLog,@ApiParam("昨天内容") @RequestParam String yesterdayLog){
        workLogService.updateById(id,todayLog,yesterdayLog);
        return new ResultVo(0,"更新成功",0,null);

    }

    @ApiOperation("获取某人某个月的工作日志")
    @PostMapping("/getMonth")
    public ResultVo getOfMonth(@ApiParam("用户id") @RequestParam Integer uid,@ApiParam("时间，该月的第一天即可，格式：2019-12-20") @RequestParam String time) throws ParseException {          //查询某人一个月的日志情况

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        User user = userService.getUserById(uid);
        Date d = sdf.parse(time);
        List ofMonth = workLogService.getOfMonth(uid, d);
        return new ResultVo(0,user.getName(),ofMonth.size(),ofMonth);
    }

    @ApiOperation("获取某一组某天的工作日志")
    @PostMapping("/getGroup")
    public ResultVo getWordLogByGidAdnTime(@ApiParam("组id") @RequestParam Integer gid,@ApiParam("时间，格式：2019-12-20") @RequestParam String time) throws ParseException {           //获取某组某天的日志
        //Group group = groupService.getById(gid);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(time);
        List list = workLogService.getWorkLogByGidAndTime(gid, d);
        return new ResultVo(0,"获取成功",list.size(),list);
    }

    //@ApiOperation("获取某一组某天的工作日志")
    @PostMapping("/getGroup2")
    public ResultVo getWordLogByGidAdnTime2(@ApiParam("时间，格式：2019-12-20") @RequestParam String time,HttpServletRequest request) throws ParseException {           //获取某组某天的日志

        User user = (User)request.getSession().getAttribute("user");
        System.out.println(user.getGid());
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(time);

        List list = workLogService.getWorkLogByGidAndTime(user.getGid(), d);
        return new ResultVo(0,"获取成功",list.size(),list);
    }

    @ApiOperation("获取某人最近7天的工作日志")
    @PostMapping("/getOneOfLastSevenDays")
    public ResultVo getOneOfLastSevenDays(@ApiParam("用户id") @RequestParam Integer uid,@ApiParam("时间，格式 2019-12-20") @RequestParam String time) throws ParseException {        //根据时间查询某人该周的所有日志

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        User user = userService.getUserById(uid);
        Date d = sdf.parse(time);
        Calendar c=Calendar.getInstance();
        c.setTime(d);

        c.add(Calendar.DATE,-7);
        Date starttime = c.getTime();
        c.setTime(d);
       // c.add(Calendar.DATE,-1);
        Date endtime=c.getTime();

        List list = workLogService.getOneOfLastSevenDays(uid,starttime,endtime);
        return new ResultVo(0,user.getName(),list.size(),list);
    }

    @GetMapping("/getLogBeforeDay")
    @ApiOperation("获取某人一个时间的前一天日志")
    public ResultVo getlogBefore(@ApiParam("用户id") @RequestParam Integer uid,@ApiParam("时间，格式 2019-12-20") @RequestParam String time) throws ParseException {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(time);

        WorkLog logByIdAndTime = workLogService.getLogByIdAndTime(uid, DateUtils.getDateBeforeDay(parse));
        return new ResultVo(0,"获取成功",0,logByIdAndTime);

    }

//    @GetMapping("/test")
//    public ResultVo getTest(@RequestParam(required = false) Integer uid, @RequestParam(required = false) String time) throws ParseException {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        Date parse=null;
//        if (!StringUtils.isBlank(time)){
//            parse = sdf.parse(time);
//        }
//        List<WorkLog> test = workLogService.test(uid, parse);
//        return new ResultVo(0,"获取成功",test.size(),test);
//    }



    @ExceptionHandler
    public ResultVo exception(Exception e){
        e.printStackTrace();
        return new ResultVo(1,e.getMessage(),0,null);
    }


}




