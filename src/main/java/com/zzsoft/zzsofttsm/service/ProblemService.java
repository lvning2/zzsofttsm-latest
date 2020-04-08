package com.zzsoft.zzsofttsm.service;


import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProblemService {


    @Autowired
    private ProblemRepository problemRepository;

    public List getProblemByTime(Integer gid, Date time){

        Date monday= DateUtils.getMondayOfThisWeek(time);
        Date sunday = DateUtils.getSundayOfThisWeek(time);

        return problemRepository.findByGidAndTime(gid, monday, sunday);

    }



}
