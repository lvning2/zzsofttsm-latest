package com.zzsoft.zzsofttsm.config;

import org.springframework.context.annotation.Bean;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

@org.springframework.context.annotation.Configuration
public class WordConfig {

    @Bean
    public freemarker.template.Configuration wordConfiguration(){
        freemarker.template.Configuration configuration=new freemarker.template.Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        configuration.setClassForTemplateLoading(this.getClass(),"/wordtemp");
        return configuration;
    }


}


