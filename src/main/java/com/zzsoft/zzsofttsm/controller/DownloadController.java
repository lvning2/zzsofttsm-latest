package com.zzsoft.zzsofttsm.controller;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.entity.WorkLog;
import com.zzsoft.zzsofttsm.service.ComputerService;
import com.zzsoft.zzsofttsm.service.UserService;
import com.zzsoft.zzsofttsm.service.WorkLogService;
import com.zzsoft.zzsofttsm.vo.ComputerVo;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/download")
@CrossOrigin(maxAge = 3600)
@Api(tags = "下载接口")
public class DownloadController {

    @Autowired
    private ComputerService computerService;

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private UserService userService;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


    @GetMapping("/downloadEquipmentExcel")
    public void downloadEquipmentExcel(HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("合并");
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);

        HSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);     // 水平居中
        cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);   // 垂直居中

        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        String[] title1={"容量","硬盘序列号","IP地址","MAC地址","操作系统版本","安装时间"};
        int count1=3;
        for (String title: title1){
            HSSFCell cell = row1.createCell(count1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title);
            count1++;
        }

        String[] title0={"序号","设备编号","设备类型","计算机配置","用途","放置位置","使用部门","责任人","使用情况","备注","是否联网","品牌型号","cpu","显卡","内存","锁号","显示器尺寸"};
        int count0=0;
        for(String title: title0){
            if(count0<=3){
                HSSFCell cell = row0.createCell(count0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(title);
                count0++;
                continue;
            }
            if (count0==4){
                count0+=5;
            }
            HSSFCell cell = row0.createCell(count0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title);
            count0++;
        }

        // 合并
        CellRangeAddress cellAddresses=new CellRangeAddress(0,0,3,8);
        sheet.addMergedRegion(cellAddresses);

        // 合并
        for (int i=0;i<3;i++){
            CellRangeAddress cellAddresses1=new CellRangeAddress(0,1,i,i);
            sheet.addMergedRegion(cellAddresses1);
        }
        for (int i=9;i<22;i++){
            CellRangeAddress cellAddresses1=new CellRangeAddress(0,1,i,i);
            sheet.addMergedRegion(cellAddresses1);
        }

        List<ComputerVo> allEquipment = computerService.getAllEquipment();
        int contentCount=2;     //   数据行号，从第二行开始
        int i=1;        // 序号

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

        for (ComputerVo computerVo: allEquipment){              // 填充内容
            HSSFRow row = sheet.createRow(contentCount);

            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(computerVo.getEquipmentNumber());
            row.createCell(2).setCellValue(computerVo.getEquipmentType());

            String check = check(computerVo.getHdCapacity());
            row.createCell(3).setCellValue(check);



            row.createCell(4).setCellValue(computerVo.getHdSerialNumber());
            row.createCell(5).setCellValue(computerVo.getIpAddress());
            row.createCell(6).setCellValue(computerVo.getMacAddress());
            row.createCell(7).setCellValue(computerVo.getOsVersion());
            row.createCell(8).setCellValue(computerVo.getOsInstallTime()!=null?sdf.format(computerVo.getOsInstallTime()):"");
            row.createCell(9).setCellValue(computerVo.getComputerUse());
            row.createCell(10).setCellValue(computerVo.getSetPosition());
            row.createCell(11).setCellValue(computerVo.getUseDepartment());
            row.createCell(12).setCellValue(computerVo.getPersonName());
            row.createCell(13).setCellValue(computerVo.getComputerUsage());
            row.createCell(14).setCellValue(computerVo.getRemark());

            Integer isNetwork = computerVo.getIsNetwork();
            switch (isNetwork){
                case 0: row.createCell(15).setCellValue("否");break;
                case 1: row.createCell(15).setCellValue("是");break;
                case 2: row.createCell(15).setCellValue("内外网");break;
            }
            row.createCell(16).setCellValue(computerVo.getBrandModel());
            row.createCell(17).setCellValue(computerVo.getCpuName());
            row.createCell(18).setCellValue(computerVo.getGraphicsCardName());
            row.createCell(19).setCellValue(StringUtils.isBlank(computerVo.getMemory())?"":computerVo.getMemory()+"G");
            row.createCell(20).setCellValue(computerVo.getLockNumber());
            row.createCell(21).setCellValue(computerVo.getMonitor());

            contentCount++;
            i++;
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=computer.xls");

        workbook.write(response.getOutputStream());
        workbook.close();

    }

    private String check(String hdCapacity){                    //     12+    +12
        if (StringUtils.isBlank(hdCapacity)){
            return "";
        }
        if (hdCapacity.startsWith("+")){
            return hdCapacity;
        }
        if (hdCapacity.endsWith("+")){
            return hdCapacity.substring(0,hdCapacity.length()-1);
        }
        return hdCapacity;
    }


    @GetMapping("/logOfMonth")
    public void downloadOneMonth(Integer id,String time,HttpServletResponse response) throws ParseException, IOException {

        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(time);
        HSSFRow head = sheet.createRow(0);

        HSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);     // 水平居中
        cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);   // 垂直居中

        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        String[] titles={"序号","姓名","日期","昨天工作","今天计划"};

        for(int i=0;i<titles.length;i++){       // 设置头
            HSSFCell cell = head.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(titles[i]);
        }
        User user = userService.getUserById(id);

        List<WorkLog> ofMonth = workLogService.getOfMonth(id, sdf.parse(time));

        for (int i=0;i<ofMonth.size();i++){
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i+1);    // 序号
            row.createCell(1).setCellValue(user.getName());    // 姓名
            WorkLog workLog = ofMonth.get(i);
            Date day = workLog.getTime();

            row.createCell(2).setCellValue(sdf.format(day)+" "+DateUtils.getDayOfWeek(day));    // 日期
            row.createCell(3).setCellValue(workLog.getYesterdayLog());      // 昨天工作
            row.createCell(4).setCellValue(workLog.getTodayLog());          // 今天计划
        }


        String filename=new String(URLEncoder.encode("工作日志.xls","utf-8").getBytes(),"ISO8859-1");


        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=log.xls");

        workbook.write(response.getOutputStream());
        workbook.close();


    }


}





