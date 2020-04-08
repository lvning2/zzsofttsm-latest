package com.zzsoft.zzsofttsm.common;

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
import com.zzsoft.zzsofttsm.vo.Download1;
import com.zzsoft.zzsofttsm.vo.DownloadTask;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

public class DownloadTemplate {

    public static Document createTaskDownload(OutputStream os, List<Download1> data, String manager, String starttime, String endtime, int week) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(os));
        Document document = new Document(pdfDoc);
        PdfFont f2 = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true);
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
                    Cell taskContent = new Cell(1, 1).add(new Paragraph(downloadTask.getTaskcontent()).setFont(f2));
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
                    Cell planContent = new Cell(1, 1).add(new Paragraph(downloadTask.getPlancontent()).setFont(f2));
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


        return document;

    }


}
