package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.Group;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.service.CommentService;
import com.zzsoft.zzsofttsm.service.GroupService;
import com.zzsoft.zzsofttsm.service.ProblemService;
import com.zzsoft.zzsofttsm.vo.ProblemVo;
import com.zzsoft.zzsofttsm.vo.ResultVo;
import com.zzsoft.zzsofttsm.vo.TreeNode;
import com.zzsoft.zzsofttsm.vo.Znode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/other")
@CrossOrigin(maxAge = 3600)
@Api(tags = "其他信息接口")
public class OtherController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/getProblem")
    @ApiOperation("获取某组某星期的问题")
    public ResultVo get(@ApiParam("组id") @RequestParam Integer gid,@ApiParam("时间，格式：2019-12-22") @RequestParam String time) throws ParseException {     //获取某组某星期的问题

        try {
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(time);
            List all = problemService.getProblemByTime(gid, d);
            return  new ResultVo(0,"获取成功",all.size(),all);
        }catch (Exception e){
            return  new ResultVo(1,e.getMessage(),0,null);
        }
    }

    @GetMapping("/getAllGroupProblem")
    @ApiOperation("获取所有组某星期的问题")
    public ResultVo getAllGroupProblem(@ApiParam("时间，格式：2019-12-22") @RequestParam String time){

        try {
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(time);
            List<ProblemVo> list=new ArrayList<>();
            List<Group> groups = groupService.getAll();
            for(Group x:groups){
                ProblemVo problemVo=new ProblemVo();
                problemVo.setGid(x.getId());
                problemVo.setGname(x.getGname());
                List problemByTime = problemService.getProblemByTime(x.getId(), d);
                problemVo.setProblems(problemByTime);
                list.add(problemVo);
            }
            return  new ResultVo(0,"获取成功",list.size(),list);
        }catch (Exception e){
            return  new ResultVo(1,e.getMessage(),0,null);
        }
    }


    @GetMapping("/getComment")
    @ApiOperation("获取所有备注")
    public ResultVo getAll(){
        try {
            List all = commentService.getAll();
            return  new ResultVo(0,"获取成功",all.size(),all);
        }catch (Exception e){
            return  new ResultVo(1,e.getMessage(),0,null);
        }

    }

    @GetMapping("/getWeekOfYear")
    @ApiOperation("获取一个时间所在年的第几周")
    public ResultVo getWeekOfYear(@ApiParam("时间，格式：2019-12-22") @RequestParam String time)  {

        try {
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(time);
            Date mondayOfThisWeek = DateUtils.getMondayOfThisWeek(d);
            Calendar c=Calendar.getInstance();
            c.setTime(mondayOfThisWeek);
            int i = c.get(Calendar.WEEK_OF_YEAR);
            return new ResultVo(0,"获取成功",0,i);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }



    }

    @DeleteMapping("/getWeekOfYear2")
    @ApiOperation("获取一个时间所在年的第几周")
    public ResultVo getWeekOfYear2(@ApiParam("时间，格式：2019-12-22") @RequestParam String time)  {

        try {
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(time);

            Date mondayOfThisWeek = DateUtils.getMondayOfThisWeek(d);

            Calendar c=Calendar.getInstance();
            c.setTime(mondayOfThisWeek);
            int i = c.get(Calendar.WEEK_OF_YEAR);

            return new ResultVo(0,"获取成功",0,i);
        }catch (Exception e){
            return new ResultVo(1,e.getMessage(),0,null);
        }



    }

    @GetMapping("/getGroupUsers")
    @ApiOperation("ZNode")
    public Object getGroupsUser(){

        List<Znode> list=new ArrayList<>();

        List<Group> allGroups = groupService.getAll();

        for(Group g : allGroups) {

            Znode znodeg = new Znode();
            znodeg.setId("g"+g.getId());
            znodeg.setpId("0");
            znodeg.setName(g.getGname());

            List<User> all = groupService.getUsersByGid(g.getId());

            for (User u : all) {
                Znode znode = new Znode();
                znode.setId("u"+u.getId());

                znode.setpId("g"+g.getId());
                znode.setName(u.getName());
                list.add(znode);
            }
            list.add(znodeg);

        }

        return list;

    }

    @GetMapping("/test10")
    public Object test10(){
        List<TreeNode> list =new ArrayList<>();

        List<Group> allGroups = groupService.getAll();

        for(Group g : allGroups) {

            TreeNode treeNode=new TreeNode();
            treeNode.setId("g"+g.getId());
            treeNode.setTitle(g.getGname());

            List<User> all = groupService.getUsersByGid(g.getId());
            List<TreeNode> childrens =new ArrayList<>();
            for (User u : all) {


                TreeNode children=new TreeNode();
                children.setId("u"+u.getId());
                children.setTitle(u.getName());
                children.setChildren(new ArrayList(0));
                childrens.add(children);
            }
            treeNode.setChildren(childrens);
            list.add(treeNode);
        }
        return list;

    }


}



