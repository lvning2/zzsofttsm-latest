package com.zzsoft.zzsofttsm;

import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeWithZoneIdSerializer;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan({"com.zzsoft.zzsofttsm.entity"})
@EnableScheduling()
@EnableSwagger2Doc
public class ZzsofttsmApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext run = SpringApplication.run(ZzsofttsmApplication.class, args);


    }

}












