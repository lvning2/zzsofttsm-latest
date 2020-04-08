package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.service.LoginService;
import com.zzsoft.zzsofttsm.service.UserService;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@RestController
@CrossOrigin(maxAge = 3600)
@Api(tags="登录注册有关接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    private Logger logger= LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "登录功能",notes = "进行登录")
    //@RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultVo login(@ApiParam("用户名") @RequestParam String username,@ApiParam("密码") @RequestParam String password, HttpServletRequest request){

        User u=new User();
        u.setUsername(username);
        u.setPassword(password);

        User user = loginService.login(u,request);
        if(null==user){
            return  new ResultVo(1,"登录失败",0,null);
        }
        request.getSession().setAttribute("user",user);
        return  new ResultVo(0,"登录成功",0,user);

    }

    //@RequestMapping(value = "/login2",method = {/*RequestMethod.GET,*/RequestMethod.POST})
    public ResultVo login2(@ApiParam("用户名") @RequestParam String username,@ApiParam("密码") @RequestParam String password, HttpServletRequest request){

        User u=new User();
        u.setUsername(username);
        u.setPassword(password);

        User user = loginService.login(u,request);
        if(null==user){
            return  new ResultVo(1,"登录失败",0,null);
        }
        request.getSession().setAttribute("user",user);

        return  new ResultVo(0,"登录成功",0,user);
    }

    @RequestMapping(value = "/logout",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation("退出登录")
    public ResultVo logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        HttpSession session = request.getSession();
        session.removeAttribute("user");

        return  new ResultVo(0,"退出成功",0,null);
    }


    @ApiOperation("注册,id不需要提供，由系统自动生成")
    @PostMapping("/register")
    public ResultVo register(User user){

        boolean register = loginService.register(user);
        if(register){
            user.setPassword("");
            return new ResultVo(0,"注册成功",0,user);
        }
        return new ResultVo(1,"注册失败",0,null);
    }




    @ExceptionHandler
    //异常处理器
    public ResultVo exception(Exception e){
        e.printStackTrace();
        return new ResultVo(1,e.getMessage(),0,null);
    }




//    @GetMapping("/user/getAll2")
//    @ApiOperation("获取所有用户-------测试")
//    public ResultVo getAllUser2(@RequestParam(defaultValue = "0") Integer page,@RequestParam Integer limit) {
//        ResultVo resultVo = new ResultVo();
//        try {
//            PageRequest id = PageRequest.of(page, limit, Sort.by("id"));
//            List all = loginService.getAllTest(id);
//            resultVo.setCode(0);
//            resultVo.setCount(all.size());
//            resultVo.setData(all);
//            return resultVo;
//        } catch (Exception e) {
//            resultVo.setCode(1);
//            resultVo.setMsg(e.getMessage());
//            return resultVo;
//        }
//
//    }

    @PostMapping("/shrioLogin")
    public ResultVo shrioLogin(@RequestParam String username,@RequestParam String password, HttpServletRequest request) {

        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //3.执行登录方法
        try {
            subject.login(token);
            User user = loginService.getUeserByUsername(username);
            user.setPassword("");
            //request.getSession().setAttribute("user",user);     // 登录成功之后将用户信息放进session里
            subject.getSession().setAttribute("user",user);
            //
            //loginService.setLastLoginTimeAndIp(request.getRemoteHost());   // 设置登录时间和ip
            // 记录日志
            userService.updateIpAndTime(user.getId(),request.getRemoteAddr());
            logger.info(request.getRemoteAddr()+" "+user.getUsername()+"("+user.getName()+"),login success.");
            //登录成功
            //跳转到main.html
            return new ResultVo(0,"",0,user);
        } catch (UnknownAccountException e) {
            //登录失败:用户名不存在
            logger.info(username+",用户名不存在");
            return new ResultVo(1,"用户名不存在",0,null);
        } catch (IncorrectCredentialsException e) {
            //登录失败:密码错误
            logger.info(username+",密码错误");
            return new ResultVo(1,"密码错误",0,null);
        }

    }

}


