package com.zzsoft.zzsofttsm.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取当前时间所在星期的星期一
     * @param date
     * @return
     */
    public static Date getMondayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);

        return c.getTime();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//
//        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String format = sdf.format(c.getTime());
//        Date d=null;
//        try {
//            d= sdf1.parse(format+" 01:00:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return d;

    }

    /**
     * 获取当前时间所在星期的星期日
     * @param date
     * @return
     */
    public static Date getSundayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }

    /**
     *
     * 判断两个日期的年月日是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        //fmt.setTimeZone(new TimeZone()); // 如果需要设置时间区域，可以在这里设置
        return fmt.format(d1).equals(fmt.format(d2));
    }

    /**
     * 获取一个日期的下周一
     * @param date
     * @return
     */
    public static Date getMondayOfNextWeek(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        // 获得入参日期是一周的第几天
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        // 获得入参日期相对于下周一的偏移量（在国外，星期一是一周的第二天，所以下周一是这周的第九天）
        // 若入参日期是周日，它的下周一偏移量是1
        int nextMondayOffset = dayOfWeek == 1 ? 1 : 9 - dayOfWeek;

        // 增加到入参日期的下周一
        cd.add(Calendar.DAY_OF_MONTH, nextMondayOffset);
        return cd.getTime();

    }

    /**
     * 获取一个日期的下周日
     * @param date
     * @return
     */
    public static Date getSundayOfNextWeek(Date date) {
        //获得入参的日期
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        // 获得入参日期是一周的第几天
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        // 获得入参日期相对于下周日的偏移量（在国外，星期一是一周的第二天，所以下周日相对于本周来说，是第15天）
        // 若入参日期是周日，它的下周日偏移量是7
        int nextMondayOffset = dayOfWeek == 1 ? 7 : 15 - dayOfWeek;

        // 增加到入参日期的下周日
        cd.add(Calendar.DAY_OF_MONTH, nextMondayOffset);
        return cd.getTime();
    }

    /**
     * 获取上周一
     * @param date
     * @return
     */
    public static Date getMondayOfLastWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int n = -1;
        cal.add(Calendar.DATE, n*7);

        return getMondayOfThisWeek(cal.getTime());

    }

    /**
     * 获取上周日
     * @param date
     * @return
     */
    public static Date getSundayOfLastWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int n = -1;
        cal.add(Calendar.DATE, n*7);

        return getSundayOfThisWeek(cal.getTime());

    }

    /**
     * 判断date是否为当前时间所在星期
     * @param
     * @return
     */
//    public static boolean isThisWeek(Date date) {
//        Date d=new Date();
//        Date monday = getMondayOfThisWeek(d);
//        Date sunday = getSundayOfThisWeek(d);
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//        System.out.println("monday:"+sdf.format(monday));
//        System.out.println("sunday:"+sdf.format(sunday));
//        System.out.println("当前:"+sdf.format(d));
//
//        if(date.getTime()>=monday.getTime()&&date.getTime()<=sunday.getTime()){
//            return true;
//        }
//        return false;
//    }

    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    /**
     * 返回一个时间所在月的第一天的日期
     * @param date
     * @return
     */
    public static Date getFirstDayOfThisMonth(Date date){
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }

    /**
     * 返回一个时间所在月的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfThisMonth(Date date){

            final Calendar cal = Calendar.getInstance();

            cal.setTime(date);

            final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            cal.set(Calendar.DAY_OF_MONTH, last);

            return cal.getTime();

    }

    /**
     * 判断两个时间是否在同一星期
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isInWeek(Date date1,Date date2){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");


        Date monday1 = getMondayOfThisWeek(date1);
        Date monday2 = getMondayOfThisWeek(date2);
        String mondayStr1 = sdf.format(monday1);
        String mondatStr2=sdf.format(monday2);


        Date sunday1 = getSundayOfThisWeek(date1);
        Date sunday2 = getSundayOfThisWeek(date1);
        String sundayStr1 = sdf.format(sunday1);
        String sundayStr2 = sdf.format(sunday2);
        if(mondayStr1.equals(mondatStr2)&&sundayStr1.equals(sundayStr2)){
            return true;
        }
        return false;
    }

    public static int getWeekOfYear(String time) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(time);
            Date mondayOfThisWeek = DateUtils.getMondayOfThisWeek(d);
            Calendar c = Calendar.getInstance();
            c.setTime(mondayOfThisWeek);
            return c.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Date getDateBeforeDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,-1);
        return c.getTime();
    }

    public static String getDayOfWeek(Date time){       // 获取一个日期是星期几   返回   周三
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        String w="";
        int i = c.get(Calendar.DAY_OF_WEEK);
        switch (i){
            case 1: w="周日";break;
            case 2: w="周一";break;
            case 3: w="周二";break;
            case 4: w="周三";break;
            case 5: w="周四";break;
            case 6: w="周五";break;
            case 7: w="周六";break;
        }
        return w;
    }

}




