package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.entity.Computer;
import com.zzsoft.zzsofttsm.entity.HardDisk;
import com.zzsoft.zzsofttsm.service.ComputerService;
import com.zzsoft.zzsofttsm.service.EquipmentHistoryService;
import com.zzsoft.zzsofttsm.service.HardDiskService;
import com.zzsoft.zzsofttsm.vo.ComputerVo;
import com.zzsoft.zzsofttsm.vo.HistoryVo;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/computer")
@CrossOrigin(maxAge = 3600)
@Api(tags = "电脑设备有关接口")
public class ComputerController {

    @Autowired
    private ComputerService computerService;

    @Autowired
    private HardDiskService hardDiskService;

    @Autowired
    private EquipmentHistoryService historyService;

    //@GetMapping("/getComputerAll")
    @ApiOperation("查询所有电脑设备")
    public ResultVo getAll(){       //查询所有电脑设备
        ResultVo resultVo=new ResultVo();
        try {
            List all = computerService.findAll();
            resultVo.setCode(0);
            resultVo.setCount(all.size());
            resultVo.setData(all);
            return resultVo;

        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }
    }

    @GetMapping("/getComputerByid")
    @ApiOperation("通过id查询一台电脑设备")
    public ResultVo getComputerByid(@ApiParam("电脑设备的id")@RequestParam String id){          //查询一台电脑的详细信息
        ResultVo resultVo=new ResultVo();
        try {
            Computer one = computerService.findOne(id);
            if(one!=null){
                resultVo.setCode(0);
                resultVo.setData(one);
                return resultVo;
            }else {
                resultVo.setCode(2);
                resultVo.setMsg("查询不存在");
                return resultVo;
            }

        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }

    }

    //@GetMapping("/delComputerByid")
    @ApiOperation("删除一个电脑设备")
    public ResultVo deleteById(@ApiParam("电脑设备的id")@RequestParam String id){             //删除某一个电脑设备
        ResultVo resultVo=new ResultVo();
        try {
            computerService.deleteById(id);
            resultVo.setCode(0);
            resultVo.setMsg("删除成功");
            return resultVo;
        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;

        }

    }

    //@PostMapping("/delHdOfComputer")
    @ApiOperation("将一个硬盘从电脑移除")
    public ResultVo delHdOfComputer(@ApiParam("电脑设备的id")@RequestParam Integer cid,@ApiParam("硬盘设备的id")@RequestParam Integer hdid){     //将一个硬盘从电脑移除

        ResultVo resultVo=new ResultVo();
        try {
            //computerService.delHdOfComputer(cid,hdid);
            resultVo.setCode(0);
            resultVo.setMsg("移除成功");
            return resultVo;
        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }
    }

    //@PostMapping("/addHdToComputer")
    @ApiOperation("将一个硬盘添加到一个电脑")
    public ResultVo test(@ApiParam("电脑设备的id")@RequestParam Integer cid,@ApiParam("硬盘设备的id")@RequestParam Integer hdid){     //将一个硬盘添加到一个电脑

        ResultVo resultVo=new ResultVo();
        try {
           //Computer computer = computerService.addHdToComputer(cid, hdid);
            resultVo.setCode(0);
            resultVo.setMsg("添加成功");
           // resultVo.setData(computer);
            return resultVo;
        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }

    }


    //@PostMapping("/updateComputer")
    @ApiOperation("修改电脑设备信息,没有则创建")
    @ApiImplicitParams(@ApiImplicitParam(name = "Computer",dataType = "Computer"))
    public ResultVo updateComputer(ComputerVo computerVo){             //添加一个电脑设备
        //System.out.println(computerVo);
        //computerService.updateComputerByUid(computerVo);
        return null;
    }

    //@PostMapping("/addHd")
    @ApiOperation("添加一个硬盘")
    public ResultVo addHd(@ApiParam("硬盘")@RequestParam HardDisk hardDisk){           //添加一个硬盘

        ResultVo resultVo=new ResultVo();
        try {
            hardDisk.setCreatetime(new Date());
            HardDisk add = hardDiskService.add(hardDisk);
            resultVo.setCode(0);
            resultVo.setData(add);
            resultVo.setMsg("添加成功");
            return resultVo;
        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;

        }
    }


    //@PostMapping("/delHdByid")
    @ApiOperation("删除一个硬盘")
    public ResultVo deleteHdById(@ApiParam("硬盘设备的id")@RequestParam Integer id){             //删除某一个硬盘设备
        ResultVo resultVo=new ResultVo();
        try {
            hardDiskService.del(id);
            resultVo.setCode(0);
            resultVo.setMsg("删除成功");
            return resultVo;
        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }
    }

    @PostMapping("/")
    @ApiOperation("将电脑设备移交与某人")
    public ResultVo transfer(@ApiParam("电脑设备的id") @RequestParam String cid,@ApiParam("要移交的用户id") @RequestParam Integer uid){   //将电脑设备移交与某人

        ResultVo resultVo=new ResultVo();
        try {
            computerService.updateComputerPerson(cid,uid);

            historyService.transfer(cid,uid);
            resultVo.setCode(0);
            resultVo.setMsg("移交成功");
            return resultVo;
        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }
    }

    @GetMapping("/getHistory")
    @ApiOperation("通过id查询一台电脑设备的流转信息")
    public ResultVo getHistory(@ApiParam("电脑设备的id") @RequestParam String cid){   //查询设备的流转信息
        ResultVo resultVo=new ResultVo();
        try {
            List<HistoryVo> history = historyService.getHistory(cid);
            resultVo.setCode(0);
            resultVo.setCount(history.size());
            resultVo.setData(history);
            return resultVo;
        }catch (Exception e){
            resultVo.setCode(1);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }


    }

    @PostMapping("/insertEquipment")
    @ApiOperation("添加电脑设备")
    public ResultVo insertEquipment(Computer computer){
        System.out.println(computer);
        computerService.insertEquipment(computer);
        return new ResultVo(0,"保存成功",null);
    }

    @GetMapping("/getByUid")
    public ResultVo getBypersonLiableId(Integer uid){      // 查询一个人的所有电脑设备
        List<ComputerVo> byPersonLiableId = computerService.getByPersonLiableId(uid);
        return new ResultVo(0,"获取成功",byPersonLiableId.size(),byPersonLiableId);
    }

    @GetMapping("/getAllEquipment")
    @ApiOperation("获取所有的电脑设备")
    public ResultVo getAllEquipment(){
        List<ComputerVo> allEquipment = computerService.getAllEquipment();
        return new ResultVo(0,"获取成功",allEquipment.size(),allEquipment);
    }

    @GetMapping("/existEquipmentNumber")
    @ApiOperation("判断设备编号是否存在")
    public ResultVo existEquipmentNumber(String equipmentNumber){
        return new ResultVo(0,"获取成功",computerService.existEquipmentNumber(equipmentNumber));
    }

    @GetMapping("/getEquipmentById")
    @ApiOperation("通过id获取电脑设备的详细信息")
    public ResultVo getEquipmentById(String id){
        ComputerVo byEquipmentById = computerService.getByEquipmentById(id);
        return new ResultVo(0,"获取成功",byEquipmentById);
    }

    @PostMapping("/deleteEquipmentById")
    @ApiOperation("根据id删除一个电脑设备")
    public ResultVo deleteEquipment(String id){
        System.out.println(id);
        computerService.deleteById(id);
        return new ResultVo(0,"删除成功",null);
    }

    @PostMapping("/updateEquipmentById")
    @ApiOperation("根据id更新一个电脑设备")
    public ResultVo updateEquipment(ComputerVo computerVo){
        System.out.println(computerVo);
        computerService.updateComputerId(computerVo);
        return new ResultVo(0,"更新成功",null);
    }

    @GetMapping("/getAllGraphicsCard")
    @ApiOperation("获取所有显卡")
    public ResultVo getAllGraphicsCard(){
        return new ResultVo(0,"获取成功",computerService.getAllGraphicsCard());
    }

    @PostMapping("/insertGraphicsCard")
    public ResultVo insertGraphicsCard(String name){        // 添加显卡
        computerService.insertGraphicsCard(name);
        return new ResultVo(0,"添加成功",null);
    }

    @PostMapping("/insertCpu")
    public ResultVo insertCpu(String name){
        computerService.insertCpu(name);
        return new ResultVo(0,"添加成功",null);
    }

    @GetMapping("/getAllCpu")
    @ApiOperation("获取所有cpu")
    public ResultVo getAllCpu(){
        return new ResultVo(0,"获取成功",computerService.getAllCpu());
    }

}







