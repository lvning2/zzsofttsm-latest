package com.zzsoft.zzsofttsm.common;

import com.zzsoft.zzsofttsm.entity.Attendance;
import org.apache.commons.lang3.StringUtils;


public class AttendanceUtils {          // 考勤处理工具类,  依赖apache的comment包

    public static String getArrive(Attendance attendance){        // 获取这一天到达时间
        if (!StringUtils.isBlank(attendance.getMorningArrive())){
            return attendance.getMorningArrive();
        }
        if (!StringUtils.isBlank(attendance.getMorningRetreat())){
            return attendance.getMorningRetreat();
        }
        if (!StringUtils.isBlank(attendance.getAfternoonArrive())){
            return attendance.getAfternoonArrive();
        }
        if (!StringUtils.isBlank(attendance.getAfternoonRetreat())){
            return attendance.getAfternoonRetreat();
        }
        if (!StringUtils.isBlank(attendance.getNightRetreat())){
            return attendance.getNightRetreat();
        }
        return null;
    }

    public static String getRetreat(Attendance attendance){              // 获取这一天离开时间
        if (!StringUtils.isBlank(attendance.getNightRetreat())){
            return attendance.getNightRetreat();
        }
        if (!StringUtils.isBlank(attendance.getAfternoonRetreat())){
            return attendance.getAfternoonRetreat();
        }
        if (!StringUtils.isBlank(attendance.getAfternoonArrive())){
            return attendance.getAfternoonArrive();
        }
        if (!StringUtils.isBlank(attendance.getMorningRetreat())){
            return attendance.getMorningRetreat();
        }
        if (!StringUtils.isBlank(attendance.getMorningArrive())){
            return attendance.getMorningArrive();
        }
        return null;
    }




}
