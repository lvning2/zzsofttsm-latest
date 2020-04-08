package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.EquipmentHistory;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.repository.EquipmentHistoryRepository;
import com.zzsoft.zzsofttsm.vo.HistoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EquipmentHistoryService {

    @Autowired
    private EquipmentHistoryRepository historyRepository;

    @Autowired
    private LoginService loginService;

    @Transactional
    public void transfer(String cid,Integer uid){     //移交
        EquipmentHistory equipmentHistory=new EquipmentHistory();
        equipmentHistory.setEquipmentId(cid);
        equipmentHistory.setStartTime(new Date());
        equipmentHistory.setUserId(uid);
        List<EquipmentHistory> byDquipmentIdOrderByIdDesc = historyRepository.findByEquipmentIdOrderByIdDesc(cid);
        if (byDquipmentIdOrderByIdDesc!=null){
            EquipmentHistory eh = byDquipmentIdOrderByIdDesc.get(0);
            historyRepository.updateEndTimeById(eh.getId(), new Date());    //修改结束日期
        }

        historyRepository.save(equipmentHistory);


    }

    public List<HistoryVo> getHistory(String cid){        //查询设备流转信息
        List<HistoryVo> list=new ArrayList<>();

        List<EquipmentHistory> eh = historyRepository.findByEquipmentIdOrderByStartTimeAsc(cid);
        for(EquipmentHistory x:eh){

            User userByid = loginService.getUserByid(x.getUserId());

            HistoryVo historyVo=new HistoryVo();

            historyVo.setId(x.getId());
            historyVo.setName(userByid.getName());
            historyVo.setUsername(userByid.getUsername());
            historyVo.setEndTime(x.getEndTime());
            historyVo.setStartTime(x.getStartTime());

            historyVo.setEquipmentId(x.getEquipmentId());
            historyVo.setOther(x.getOther());
            list.add(historyVo);
        }

        return list;
    }




}



