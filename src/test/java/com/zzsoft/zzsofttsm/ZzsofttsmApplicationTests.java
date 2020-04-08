package com.zzsoft.zzsofttsm;


import com.zzsoft.zzsofttsm.common.AttendanceUtils;
import com.zzsoft.zzsofttsm.entity.Attendance;
import com.zzsoft.zzsofttsm.service.AttendanceService;
import com.zzsoft.zzsofttsm.service.ComputerService;
import com.zzsoft.zzsofttsm.vo.ComputerVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootTest
class ZzsofttsmApplicationTests {


    @Autowired
    private ComputerService computerService;

    @Test
    void contextLoads() {
    }

    @Test
    public void datasourceTest() throws IOException {

        List<ComputerVo> allEquipment = computerService.getAllEquipment();
        for (ComputerVo computerVo: allEquipment){
            System.out.println(computerVo);
        }

        int count=1;


        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("合并");
        HSSFRow row = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);

        row1.createCell(3).setCellValue("容量");
        row1.createCell(4).setCellValue("硬盘序列号");
        row1.createCell(5).setCellValue("IP地址");
        row1.createCell(6).setCellValue("MAC地址");
        row1.createCell(7).setCellValue("操作系统版本");
        row1.createCell(8).setCellValue("安装时间");

        row.createCell(0).setCellValue("序号");
        row.createCell(1).setCellValue("设备编号");
        row.createCell(2).setCellValue("设备类型");
        row.createCell(3).setCellValue("计算机配置");
        row.createCell(4).setCellValue("用途");
        row.createCell(5).setCellValue("放置位置");
        row.createCell(6).setCellValue("使用部门");
        row.createCell(7).setCellValue("责任人");
        row.createCell(8).setCellValue("使用情况");
        row.createCell(9).setCellValue("备注");
        row.createCell(10).setCellValue("是否联网");
        row.createCell(11).setCellValue("品牌型号");
        row.createCell(12).setCellValue("cpu");
        row.createCell(13).setCellValue("显卡");
        row.createCell(14).setCellValue("内存");
        row.createCell(15).setCellValue("锁号");
        row.createCell(16).setCellValue("显示器尺寸");


        workbook.write(new File("F:\\excel\\test.xlsx"));
        workbook.close();

    }








}





