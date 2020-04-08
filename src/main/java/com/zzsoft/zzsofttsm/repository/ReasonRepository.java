package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ReasonRepository extends JpaRepository<Reason,Long> {

    Reason findByTaskIdAndTimeBetween(Long taskId, Date time1,Date time2);

}
