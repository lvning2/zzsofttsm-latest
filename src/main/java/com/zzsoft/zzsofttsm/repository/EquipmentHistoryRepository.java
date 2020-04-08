package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.EquipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.Date;
import java.util.List;

public interface EquipmentHistoryRepository extends JpaRepository<EquipmentHistory,Integer> {

    List<EquipmentHistory> findByEquipmentIdOrderByIdDesc(String id);

    @Modifying
    @Query("update EquipmentHistory e set e.endTime=?2 where e.id=?1")
    void updateEndTimeById(Integer id, Date endTime);

    List<EquipmentHistory> findByEquipmentIdOrderByStartTimeAsc(String cid);

}
