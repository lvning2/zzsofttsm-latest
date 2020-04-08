package com.zzsoft.zzsofttsm.test;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.zzsoft.zzsofttsm.common.JsonUtils;

import com.zzsoft.zzsofttsm.vo.Download1;
import com.zzsoft.zzsofttsm.vo.DownloadTask;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;

import javax.persistence.Column;

public class Test {



    public static void main(String[] args) throws ParseException, IOException {

        List list=new ArrayList();
        list.add("s");

        String a ="ceshi";

        a.substring(1,2);

    }

    public void fun() throws IOException, ParseException {

        File file = ResourceUtils.getFile("classpath:font/simsun.ttc");
        System.out.println(file.getPath());
//        String path = ClassUtils.getDefaultClassLoader().getResource("font/simsun.ttc").getPath();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter("F://itext.pdf"));
        Document document = new Document(pdfDoc);
        //PdfFont f2 = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true);
        PdfFont f2 = PdfFontFactory.createFont("classpath:font/simsun.ttc,1", PdfEncodings.IDENTITY_H, false);
        //PdfFont f2 = PdfFontFactory.createFont("c://windows//fonts//simsun.ttc,1", PdfEncodings.IDENTITY_H, false);

        document.add(new Paragraph("产品一部").setFont(f2).setFontSize(25).setTextAlignment(TextAlignment.CENTER)); //表格、list其他方式也是这种方式

        //Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        float[] w={70F,170F,40F,170F,40F};
        Table table = new Table(w);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        Cell cellTopLeft=new Cell(1,2);
        cellTopLeft.setBorder(Border.NO_BORDER).add(new Paragraph("部门经理：陈硕").setFont(f2));
        table.addCell(cellTopLeft);

        Cell cellTopRight=new Cell(1,3);
        cellTopRight.setBorder(Border.NO_BORDER).add(new Paragraph("日期：2019-12-30至2020-01-05(第2周)").setFont(f2).setTextAlignment(TextAlignment.RIGHT));
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

        List<Download1> data = getData();

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
                    taskContent.setBorderBottom(Border.NO_BORDER);
                    taskContent.setBorderRight(Border.NO_BORDER);
                    table.addCell(taskContent);

                    //成果进度
                    Cell taskProgress = new Cell(1, 1).add(new Paragraph("".equals(downloadTask.getTaskcontent())?"":(downloadTask.getTp() + "%")).setFont(f2).setTextAlignment(TextAlignment.CENTER));
                    if(i>1){
                        taskProgress.setBorderTop(Border.NO_BORDER);
                    }
                    taskProgress.setBorderLeft(Border.NO_BORDER);
                    taskProgress.setBorderBottom(Border.NO_BORDER);

                    table.addCell(taskProgress);


                    //计划内容
                    Cell planContent = new Cell(1, 1).add(new Paragraph("·"+downloadTask.getPlancontent()).setFont(f2));
                    if(i>1){
                        planContent.setBorderTop(Border.NO_BORDER);
                    }
                    planContent.setBorderBottom(Border.NO_BORDER);
                    planContent.setBorderRight(Border.NO_BORDER);
                    table.addCell(planContent);

                    //计划进度
                    Cell planProgress = new Cell(1, 1).add(new Paragraph("".equals(downloadTask.getPlancontent())?"":(downloadTask.getPp() + "%")).setFont(f2).setTextAlignment(TextAlignment.CENTER));
                    if(i>1){
                        planProgress.setBorderTop(Border.NO_BORDER);
                    }
                    planProgress.setBorderLeft(Border.NO_BORDER);
                    planProgress.setBorderBottom(Border.NO_BORDER);
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

        document.add(table);


        document.close();

    }

    public List<Download1> getData() throws ParseException {

        //List<Download1> data = new ArrayList();      //data数据

        String target="[{\"gname\":\"综合组\",\"downloadTasks\":[{\"taskcontent\":\"余杭区三维管线三期项目\",\"tp\":84,\"plancontent\":\"余杭区三维管线三期项目\",\"pp\":90},{\"taskcontent\":\"孝感城乡规划\",\"tp\":50,\"plancontent\":\"孝感城乡规划\",\"pp\":95},{\"taskcontent\":\"贺州项目二期\",\"tp\":50,\"plancontent\":\"国土技术平台\",\"pp\":80},{\"taskcontent\":\"国土技术平台\",\"tp\":0,\"plancontent\":\"南阳第十五小学\",\"pp\":60},{\"taskcontent\":\"南阳第十五小学\\n\",\"tp\":50,\"plancontent\":\"\",\"pp\":0}]},{\"gname\":\"管理组\",\"downloadTasks\":[{\"taskcontent\":\"大活动十点半\",\"tp\":10,\"plancontent\":\"\\n大活动十点半\",\"pp\":90},{\"taskcontent\":\"\\t电科技覅第三方is\",\"tp\":0,\"plancontent\":\"\",\"pp\":0}]},{\"gname\":\"PC框架\",\"downloadTasks\":[{\"taskcontent\":\"直连mapguide\",\"tp\":60,\"plancontent\":\"直连mapguide\",\"pp\":95},{\"taskcontent\":\"智慧工地-人脸识别\",\"tp\":30,\"plancontent\":\"智慧工地-人脸识别\",\"pp\":90},{\"taskcontent\":\"余杭管线系统\",\"tp\":30,\"plancontent\":\"余杭管线系统\",\"pp\":92}]},{\"gname\":\"微服务组\",\"downloadTasks\":[]},{\"gname\":\"WEB框架\",\"downloadTasks\":[]},{\"gname\":\"服务组\",\"downloadTasks\":[]}]";

        List<Download1> data = JsonUtils.jsonToList(target, Download1.class);

        return data;
    }




}








