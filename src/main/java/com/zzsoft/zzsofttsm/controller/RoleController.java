package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.entity.Permission;
import com.zzsoft.zzsofttsm.service.RoleService;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin(maxAge = 3600)
@Api(tags = "角色有关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllRole")
    @ApiOperation("获取所有角色")
    public ResultVo getAllRole(){           //获取所有角色

        try {
            List allRole = roleService.getAllRole();
            return new ResultVo(0,"获取成功",allRole.size(),allRole);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }
    }

    @GetMapping()
    @ApiOperation("获取角色的所有权限")
    public ResultVo getAllPermissionOfRole(Long rid){
        List<Permission> list = roleService.getPermissionOfRole(rid);
        return new ResultVo(0,"查询成功",list.size(),list);
    }




}
