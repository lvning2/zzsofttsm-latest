package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.service.GroupService;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/group")
@Api(tags = "组信息接口")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @PostMapping("/get")
    @ApiOperation("获取所有组")
    public ResultVo getAll(){           //获取所有组
        List all = groupService.getAll();
        return  new ResultVo(0,"获取成功",all.size(),all);

    }

    @PostMapping("/getUsersByGid")
    @ApiOperation("根据组id获取该组所有的人")
    public ResultVo getUsersByGid(@RequestParam Integer gid){           //通过组id，获取该组的所有人
        List list = groupService.getUsersByGid(gid);
        return  new ResultVo(0,"查询成功",list.size(),list);
    }


}







