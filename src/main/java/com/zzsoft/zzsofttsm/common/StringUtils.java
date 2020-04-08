package com.zzsoft.zzsofttsm.common;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtils {

    /**
     * 字符数组转字符串
     * @param a
     * @return
     */
    public static String ArrayToString(char[] a){
        String s="";
        for (char x:a){
            s+=x;
        }
        return s;
    }



}
