package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.common.AttendanceUtils;
import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.Attendance;
import com.zzsoft.zzsofttsm.repository.AttendanceRepository;
import com.zzsoft.zzsofttsm.repository.UserRepository;
import com.zzsoft.zzsofttsm.vo.ArrAndRetVo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(UserRepository userRepository, AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public Attendance getAttendanceByAttendanceIdAndDay(String attendanceId, Date data){  // 获取某人某天的考勤信息
        return attendanceRepository.findByAttendanceIdAndDay(attendanceId,data);
    }

    public void getArriveAndRetreat(String attendanceId, Date data){  // 获取某人某天的 到达的时间、离开的时间
        Attendance attendance = attendanceRepository.findByAttendanceIdAndDay(attendanceId, data);
        String arrive= AttendanceUtils.getArrive(attendance);   // 到达的时间
        String retreat=AttendanceUtils.getRetreat(attendance);  // 离开的时间
    }

    @Transactional
    public List<ArrAndRetVo> getArrAndRetByAttIdOfMonth(String attendanceId, String dataStr) throws ParseException {               // 获取某人一个月的某天的 到达的时间、离开的时间
        dataStr+="-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(dataStr);
        Date firstDay = DateUtils.getFirstDayOfThisMonth(parse);
        Date lastDay = DateUtils.getLastDayOfThisMonth(parse);
        List<Attendance> attendances = attendanceRepository.findByAttendanceIdAndDayBetween(attendanceId, firstDay, lastDay);

        Calendar calendar=Calendar.getInstance();

        List<ArrAndRetVo> list=new ArrayList<>();
        for (Attendance attendance: attendances){
            ArrAndRetVo arrAndRetVo=new ArrAndRetVo();
            arrAndRetVo.setId(attendance.getId());
            arrAndRetVo.setDay(attendance.getDay());
            calendar.setTime(attendance.getDay());
            arrAndRetVo.setDayOfWook(calendar.get(Calendar.DAY_OF_WEEK)-1);   // 周日为 0   周六为 6
            arrAndRetVo.setArrive(AttendanceUtils.getArrive(attendance));
            arrAndRetVo.setRetreat(AttendanceUtils.getRetreat(attendance));
            list.add(arrAndRetVo);
        }

        return list;

    }



}



