package com.zzsoft.zzsofttsm.controller;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.Group;
import com.zzsoft.zzsofttsm.entity.Task;
import com.zzsoft.zzsofttsm.entity.TaskPlan;
import com.zzsoft.zzsofttsm.service.GroupService;
import com.zzsoft.zzsofttsm.service.LoginService;
import com.zzsoft.zzsofttsm.service.TaskPlanService;
import com.zzsoft.zzsofttsm.service.TaskService;
import com.zzsoft.zzsofttsm.vo.Download1;
import com.zzsoft.zzsofttsm.vo.DownloadTask;
import com.zzsoft.zzsofttsm.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/download")
@CrossOrigin(maxAge = 3600)
@Api(tags = "下载接口")
public class DownloadController2 {

    @Autowired
    private GroupService groupService;

    @Autowired
    private TaskPlanService taskPlanService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/get6")
    //@ResponseBody
    @ApiOperation("下载")
    public void get6(String time, HttpServletResponse response) throws IOException, ParseException {


        List<Download1> data = new ArrayList();      //data数据

        String manager=loginService.getManager().get(0).getName();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(time);
        Date monday = DateUtils.getMondayOfThisWeek(parse);
        Date sunday = DateUtils.getSundayOfThisWeek(parse);
        String starttime=sdf.format(monday);
        String endtime=sdf.format(sunday);

        int week=DateUtils.getWeekOfYear(time);

        List<Group> groups = groupService.getAll();     //获取所有组
        for (Group group : groups) {
            Download1 download1 = new Download1();    //一个组的所有信息，加到map的data里，渲染


            download1.setGname(group.getGname());
            List<TaskPlan> plan = taskPlanService.getTaskPlanByTime(group.getId(), parse);    //获取一个组的下周计划
            List<Task> tasks = listTesk(group.getId(), parse);                                 //获取一个组的本周任务

            List<DownloadTask> downloadTasks = extractDownloadTask(tasks, plan);
            download1.setDownloadTasks(downloadTasks);
            data.add(download1);

        }


        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(response.getOutputStream()));
        Document document = new Document(pdfDoc);
        //PdfFont f2 = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true);
        PdfFont f2 = PdfFontFactory.createFont("/font/simsun.ttc,1", PdfEncodings.IDENTITY_H, false);
        document.add(new Paragraph("产品一部").setFont(f2).setFontSize(25).setTextAlignment(TextAlignment.CENTER)); //表格、list其他方式也是这种方式

        //Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        float[] w={70F,170F,40F,170F,40F};
        Table table = new Table(w);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        Cell cellTopLeft=new Cell(1,2);
        cellTopLeft.setBorder(Border.NO_BORDER).add(new Paragraph("部门经理："+manager).setFont(f2));
        table.addCell(cellTopLeft);

        Cell cellTopRight=new Cell(1,3);
        cellTopRight.setBorder(Border.NO_BORDER).add(new Paragraph("日期："+starttime+"至"+endtime+"(第"+week+"周)").setFont(f2).setTextAlignment(TextAlignment.RIGHT));
        table.addCell(cellTopRight);

        DeviceRgb deviceRgb = new DeviceRgb(211, 211, 211); //填充颜色
        DeviceCmyk deviceCmyk = Color.convertRgbToCmyk(deviceRgb);

        Cell cellFenZu=new Cell(2,1).add(new Paragraph("分组").setFont(f2).setTextAlignment(TextAlignment.CENTER));
        cellFenZu.setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(deviceCmyk);

        Cell cellChengGuo=new Cell(1,2).add(new Paragraph("本周成果").setFont(f2).setTextAlignment(TextAlignment.CENTER));
        Cell cellJiHua=new Cell(1,2).add(new Paragraph("下周计划").setFont(f2).setTextAlignment(TextAlignment.CENTER));
        table.addCell(cellFenZu.setBackgroundColor(deviceCmyk));
        table.addCell(cellChengGuo.setBackgroundColor(deviceCmyk));
        table.addCell(cellJiHua.setBackgroundColor(deviceCmyk));
        Cell RenWu=new Cell(1,1).add(new Paragraph("任务").setFont(f2).setTextAlignment(TextAlignment.CENTER));
        table.addCell(RenWu.setBackgroundColor(deviceCmyk));
        Cell RenWuJinDu=new Cell(1,1).add(new Paragraph("进度").setFont(f2).setTextAlignment(TextAlignment.CENTER));
        table.addCell(RenWuJinDu.setBackgroundColor(deviceCmyk));
        Cell JiHua=new Cell(1,1).add(new Paragraph("任务").setFont(f2).setTextAlignment(TextAlignment.CENTER));
        table.addCell(JiHua.setBackgroundColor(deviceCmyk));
        Cell JiHuaJinDu=new Cell(1,1).add(new Paragraph("进度").setFont(f2).setTextAlignment(TextAlignment.CENTER));
        table.addCell(JiHuaJinDu.setBackgroundColor(deviceCmyk));

        for(Download1 download1:data){
            Cell gname=new Cell(download1.getDownloadTasks().size(),1).add(new Paragraph(download1.getGname()).setFont(f2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(gname.setVerticalAlignment(VerticalAlignment.MIDDLE));
            List<DownloadTask> downloadTasks = download1.getDownloadTasks();
            int i=0;
            if (downloadTasks.size()>0) {
                for (DownloadTask downloadTask : downloadTasks) {
                    i++;
                    //成果内容
                    Cell taskContent = new Cell(1, 1).add(new Paragraph("·"+downloadTask.getTaskcontent()).setFont(f2));
                    if(i>1){
                        taskContent.setBorderTop(Border.NO_BORDER);
                    }

                    if (i<downloadTasks.size()){    //为最后一个添加边框
                        taskContent.setBorderBottom(Border.NO_BORDER);
                    }

                    taskContent.setBorderRight(Border.NO_BORDER);
                    table.addCell(taskContent);

                    //成果进度
                    Cell taskProgress = new Cell(1, 1).add(new Paragraph("".equals(downloadTask.getTaskcontent())?"":(downloadTask.getTp() + "%")).setFont(f2).setTextAlignment(TextAlignment.CENTER));
                    if(i>1){
                        taskProgress.setBorderTop(Border.NO_BORDER);
                    }
                    if(i<downloadTasks.size()){
                        taskProgress.setBorderBottom(Border.NO_BORDER);
                    }
                    taskProgress.setBorderLeft(Border.NO_BORDER);


                    table.addCell(taskProgress);


                    //计划内容
                    Cell planContent = new Cell(1, 1).add(new Paragraph("·"+downloadTask.getPlancontent()).setFont(f2));
                    if(i>1){
                        planContent.setBorderTop(Border.NO_BORDER);
                    }
                    if(i<downloadTasks.size()){
                        planContent.setBorderBottom(Border.NO_BORDER);
                    }


                    planContent.setBorderRight(Border.NO_BORDER);
                    table.addCell(planContent);

                    //计划进度
                    Cell planProgress = new Cell(1, 1).add(new Paragraph("".equals(downloadTask.getPlancontent())?"":(downloadTask.getPp() + "%")).setFont(f2).setTextAlignment(TextAlignment.CENTER));
                    if(i>1){
                        planProgress.setBorderTop(Border.NO_BORDER);
                    }
                    if(i<downloadTasks.size()){
                        planProgress.setBorderBottom(Border.NO_BORDER);
                    }
                    planProgress.setBorderLeft(Border.NO_BORDER);

                    table.addCell(planProgress);

                }
            }else {
                Cell taskContent = new Cell(1, 1);
                table.addCell(taskContent);
                Cell taskProgress = new Cell(1, 1);
                table.addCell(taskProgress);
                Cell planContent = new Cell(1, 1);
                table.addCell(planContent);
                Cell planProgress = new Cell(1, 1);
                table.addCell(planProgress);
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="+time+".pdf");
        document.add(table);
        document.close();

    }



    //获取某组本周成果
    private List<Task> listTesk(Integer gid, Date date){

        boolean inWeek = DateUtils.isInWeek(date, new Date());
        if (inWeek) {
            List<Task> tasks = taskService.getTaskByGroupAndTime(gid, new Date()); //获取任务信息
            return tasks;
        }else {
            List<TaskVo> pastTaskByGroupAndTime = taskService.getPastTaskByGroupAndTime(gid, date);
            List<Task> list=new ArrayList<>();
            for(TaskVo taskVo:pastTaskByGroupAndTime) {
                Task t=new Task();
                t.setId(taskVo.getId());
                t.setGid(taskVo.getGid());
                t.setProgress(taskVo.getProgress());
                t.setContent(taskVo.getContent());
                t.setIsDelay(taskVo.getIsDelay());
                t.setTime(taskVo.getTime());
                list.add(t);
            }
            return list;
        }
    }

    private List<DownloadTask> extractDownloadTask(List<Task> tasks,List<TaskPlan> plans){

        List<DownloadTask> downloadTaskList = new ArrayList<>();

        int a = tasks.size();
        int b=plans.size();

        for(int i=0;i<Math.max(a,b);i++){
            DownloadTask downloadTask=new DownloadTask();
            if(a>b){
                if(i<b){
                    Task task = tasks.get(i);
                    TaskPlan taskPlan = plans.get(i);
                    downloadTask.setTaskcontent(task.getContent());
                    downloadTask.setTp(task.getProgress());
                    downloadTask.setPlancontent(taskPlan.getContent());
                    downloadTask.setPp(taskPlan.getProgress());
                    downloadTaskList.add(downloadTask);
                }else {
                    Task task = tasks.get(i);
                    downloadTask.setTaskcontent(task.getContent());
                    downloadTask.setTp(task.getProgress());
                    downloadTaskList.add(downloadTask);
                }

            }else {
                if(i<a){
                    Task task = tasks.get(i);
                    TaskPlan taskPlan = plans.get(i);
                    downloadTask.setTaskcontent(task.getContent());
                    downloadTask.setTp(task.getProgress());
                    downloadTask.setPlancontent(taskPlan.getContent());
                    downloadTask.setPp(taskPlan.getProgress());
                    downloadTaskList.add(downloadTask);

                }else {
                    TaskPlan taskPlan = plans.get(i);
                    downloadTask.setPlancontent(taskPlan.getContent());
                    downloadTask.setPp(taskPlan.getProgress());
                    downloadTaskList.add(downloadTask);
                }
            }

        }
        return downloadTaskList;
    }

}


