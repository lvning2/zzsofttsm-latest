package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Attendance findByAttendanceIdAndDay(String attendanceId, Date date);

    List<Attendance> findByAttendanceIdAndDayBetween(String attendanceId, Date start,Date end);


}
