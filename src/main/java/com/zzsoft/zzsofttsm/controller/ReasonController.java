package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.entity.Reason;
import com.zzsoft.zzsofttsm.service.ReasonService;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/reason")
@Api(tags = "延期信息相关接口")
public class ReasonController {

    private final ReasonService reasonService;

    ReasonController(ReasonService reasonService){
        this.reasonService = reasonService;
    }

    @GetMapping("/getReasonByTaskIdAndTime")
    @ApiOperation("根据任务id和时间，获取所在时间的延期原因")
    public ResultVo getReasonByTaskIdAndTime(Long taskId,String time) throws ParseException {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        Reason reason = reasonService.getReasonByTaskIdAndTime(taskId, date);
        return new ResultVo(0,"查询成功",reason);
    }

    @PostMapping("/editById")
    @ApiOperation("根据一条延期原因的id,修改")
    public ResultVo updateById(Long id,String rea){
        reasonService.updateById(id,rea);
        return new ResultVo(0,"修改成功",null);
    }

    @PostMapping("/createReason")
    @ApiOperation("创建一条延期信息")
    public ResultVo createReason(Long taskId,String time,String rea) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        reasonService.createReason(taskId,date,rea);
        return new ResultVo(0,"创建成功",null);
    }

    //@PostMapping("/deleteById")
    //@ApiOperation("根据id删除")
    public ResultVo deleteById(Long id){
        reasonService.deleteById(id);
        return new ResultVo(0,"删除成功",null);
    }

    @ExceptionHandler
    public ResultVo ExceptionHandler(Exception e){
        e.printStackTrace();
        return new ResultVo(1,e.getMessage(),null);
    }

}
