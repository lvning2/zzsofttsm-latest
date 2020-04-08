package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComputerRepository extends JpaRepository<Computer,String> {

    List<Computer> findByPersonLiableId(Integer personLiableId);        // 获取责任人的所有设备

    boolean existsByEquipmentNumber(String equipmentNumber);        // 判断是否存在 设备编号


}




