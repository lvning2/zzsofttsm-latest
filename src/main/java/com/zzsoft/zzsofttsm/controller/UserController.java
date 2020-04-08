package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.entity.Role;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.service.UserService;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(maxAge = 3600)
@Api(tags = "用户有关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/updateUserRole")
    @ApiOperation("改变用户的角色")
    public ResultVo updateUserRole(@ApiParam("用户id") @RequestParam Integer uid, @ApiParam("角色id") @RequestParam Integer rid){
        try {
            userService.updateUserRole(uid,rid);
            return new ResultVo(0,"更新成功",0,null);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }
    }

    @GetMapping("/getAll")
    @ApiOperation("获取所有用户")
    public ResultVo getAllUser(){
        try {
            List all = userService.getAllUser();
            return new ResultVo(0,"获取成功",all.size(),all);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }

    }

    @GetMapping("/getRoleOfUser")
    @ApiOperation("获取用户的所有角色")
    public ResultVo getRoleOfUser(@RequestParam Integer uid){     //获取用户的所有角色
        List<Role> list = userService.getRoleOfUser(uid);
        return new ResultVo(0,"查询成功",list.size(),list);
    }


    @GetMapping("/resetPassword")
    @RequiresPermissions(value = {"user:reset"})
    @ApiOperation("重置密码")
    public ResultVo resetPassword(@RequestParam Integer uid){
        userService.resetPassword(uid);
        return new ResultVo(0,"重置成功",0,null);
    }


    @GetMapping("/getUserById")
    @ApiOperation("根据用户id获取用户信息")
    public ResultVo getUserById(Integer id){
        User userById = userService.getUserById(id);
        User user=new User();
        user.setUsername(userById.getUsername());
        user.setGid(userById.getGid());
        user.setId(userById.getId());
        user.setName(userById.getName());
        user.setLastLoginIp(userById.getLastLoginIp());
        user.setLastLoginTime(userById.getLastLoginTime());

        return new ResultVo(0,"查询成功",0,user);
    }


    @PostMapping("/updatePassword")
    @ApiOperation("修改密码")
    public ResultVo updatePassword(@ApiParam("用户id") @RequestParam Integer uid,@ApiParam("旧密码") @RequestParam String oldPassword,@ApiParam("新密码") @RequestParam String newPassword){
        boolean b = userService.updatePassword(uid, oldPassword, newPassword);
        if(b){
            return new ResultVo(0,"修改成功",0,null);
        }else {
            return new ResultVo(1,"修改失败,密码不正确",0,null);
        }

    }






}


