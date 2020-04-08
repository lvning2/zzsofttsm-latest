package com.zzsoft.zzsofttsm.config;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义时间转换器
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        if(StringUtils.isBlank(s)){
            return null;
        }
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(s);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
