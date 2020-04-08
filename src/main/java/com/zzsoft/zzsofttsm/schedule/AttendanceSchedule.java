package com.zzsoft.zzsofttsm.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class AttendanceSchedule {

    Logger logger= LoggerFactory.getLogger(AttendanceSchedule.class);

    //private String crawler="python F:\\lvning\\start\\pa.py";

    @Value("${zzsofttsm.python.crawler}")
    private String crawler;

    @Scheduled(cron = "0 50 23 * * ?")      // 每天晚上11点50分统计一侧
    public void addup(){            // 统计考勤，存入attendance（考勤）表中,调用python爬虫代码
        Process proc;
        try {
            logger.info("Start counting attendance...");
            proc = Runtime.getRuntime().exec(crawler);
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
