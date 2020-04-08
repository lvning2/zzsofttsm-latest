package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.Reason;
import com.zzsoft.zzsofttsm.repository.ReasonRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ReasonService {

    private final ReasonRepository reasonRepository;

    ReasonService(ReasonRepository reasonRepository){
        this.reasonRepository=reasonRepository;
    }

    @Transactional
    public Reason getReasonByTaskIdAndTime(Long taskId, Date time){     // 获取一个任务的延期原因，在这个时间所在的星期内
        Date monday = DateUtils.getMondayOfThisWeek(time);
        Date sunday = DateUtils.getSundayOfThisWeek(time);
        return reasonRepository.findByTaskIdAndTimeBetween(taskId,monday,sunday);
    }

    public void updateById(Long id,String rea){        // 通过这条延期原因的id 修改
        Reason reason = reasonRepository.getOne(id);
        reason.setReason(rea);
        reasonRepository.save(reason);
    }

    public void createReason(Long taskId, Date time,String rea){        // 创建一条延期原因
        Reason reason=new Reason();
        reason.setReason(rea);
        reason.setTaskId(taskId);
        reason.setTime(time);
        reasonRepository.save(reason);
    }

    public void deleteById(Long id){                    // 根据id 删除
        Reason reason = reasonRepository.getOne(id);
        reasonRepository.delete(reason);
    }


}

