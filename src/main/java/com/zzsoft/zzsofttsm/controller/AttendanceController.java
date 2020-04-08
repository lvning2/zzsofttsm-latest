package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.service.AttendanceService;
import com.zzsoft.zzsofttsm.vo.ArrAndRetVo;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/attend")
@CrossOrigin(maxAge = 3600)
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/getArrAndRetOfMonth")
    @ApiOperation("获取某人一月的考勤信息")
    public ResultVo getArriveAndRetreatByAttendanceId(@ApiParam("考勤id：0030602") @RequestParam String attendanceId, @ApiParam("月份：2020-03") @RequestParam String dateStr) throws ParseException {
        List<ArrAndRetVo> list = attendanceService.getArrAndRetByAttIdOfMonth(attendanceId, dateStr);
        return new ResultVo(0,"获取成功",list.size(),list);
    }


}

