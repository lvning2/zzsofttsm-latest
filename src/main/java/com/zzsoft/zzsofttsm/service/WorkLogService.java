package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.common.DateUtils;
import com.zzsoft.zzsofttsm.entity.Group;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.entity.WorkLog;
import com.zzsoft.zzsofttsm.repository.GroupRepository;
import com.zzsoft.zzsofttsm.repository.UserRepository;
import com.zzsoft.zzsofttsm.repository.WorkLogRepository;
import com.zzsoft.zzsofttsm.vo.WorkLogVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void createWorkLog(WorkLog workLog){         //覆盖创建

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


        List<WorkLog> byUidAndTime = workLogRepository.findByUidAndTimeOrderByTimeAsc(workLog.getUid(), sdf.format(workLog.getTime()));

        if(byUidAndTime!=null&&byUidAndTime.size()>0){
            WorkLog workLog1 = byUidAndTime.get(0);
            workLog1.setYesterdayLog(workLog.getYesterdayLog());
            workLog1.setTodayLog(workLog.getTodayLog());
            workLogRepository.updateContentById(workLog1.getId(),workLog.getTodayLog(),workLog.getYesterdayLog());
        }else {
            System.out.println(workLog.getTime());
            workLogRepository.save(workLog);
        }


    }


    public void delete(Long id) {        //删除一个日志

        WorkLog one = workLogRepository.getOne(id);
        workLogRepository.delete(one);

    }

    public List<WorkLog> selectWorkLogByUidAndTime(Integer uid, String date){   //根据时间查询日志
        return workLogRepository.findByUidAndTimeOrderByTimeAsc(uid, date);

    }


    public List<WorkLog> getOfThisWeek(Integer uid,Date time){                   //根据时间查询某人该周的所有日志
        Date monday= DateUtils.getMondayOfThisWeek(time);
        Date sunday = DateUtils.getSundayOfThisWeek(time);
        return workLogRepository.findByTimeBetweenAndUidOrderByTimeAsc(monday,sunday,uid);
    }


    @Transactional
    public void updateById(Long id, String todayLog,String yesterdayLog) {
        workLogRepository.updateContentById(id,todayLog,yesterdayLog);
    }



    public List getOfMonth(Integer uid, Date date) {

        Date firstDay=DateUtils.getFirstDayOfThisMonth(date);
        Date lastDay=DateUtils.getLastDayOfThisMonth(date);

        return workLogRepository.findByUidAndTimeBetweenOrderByTimeAsc(uid, firstDay, lastDay);

    }

    public List getWorkLogByGidAndTime(Integer gid,Date time){   //获取某组某天的工做日志

        Group group = groupRepository.findById(gid).get();

        List<User> users = userRepository.findByGid(gid);
        List<WorkLogVo> list=new ArrayList();
        for(User u:users){
            WorkLogVo workLogVo = new WorkLogVo();
            workLogVo.setUid(u.getId());
            workLogVo.setName(u.getName());

            WorkLog log = workLogRepository.findByUidAndAndTime(u.getId(), time);
            if(log!=null){
                workLogVo.setId(log.getId());
                workLogVo.setYesterdayLog(log.getYesterdayLog());
                workLogVo.setTodayLog(log.getTodayLog());
            }
            list.add(workLogVo);
        }
        return list;
    }

    public List getOneOfLastSevenDays(Integer uid,Date starttime,Date endtime){
        return workLogRepository.findByUidAndTimeBetweenOrderByTimeAsc(uid,starttime,endtime);
    }

    @Transactional
    public void updateLogById(Long id,String todayLog,String yesterdayLog){
        WorkLog log = workLogRepository.getOne(id);
        log.setTodayLog(todayLog);
        log.setYesterdayLog(yesterdayLog);
        workLogRepository.save(log);
    }

    @Transactional
    public WorkLog getLogByIdAndTime(Integer uid,Date time){
        return workLogRepository.findByUidAndAndTime(uid,time);
    }

    @Transactional
    public List<WorkLog> getLogOfMonth(Integer id,Date time){     // 获取某人一个月的日志信息

        Date starttime = DateUtils.getFirstDayOfThisMonth(time);
        Date endtime = DateUtils.getLastDayOfThisMonth(time);

        List<WorkLog> list = workLogRepository.findByUidAndTimeBetweenOrderByTimeAsc(id, starttime, endtime);
        return list;

    }

    @Transactional
    public List<WorkLog> test(Integer uid,Date time){           // 测试

        Specification<WorkLog> workLogSpecification=new Specification<WorkLog>() {
            @Override
            public Predicate toPredicate(Root<WorkLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                if (uid!=null){
                    Path<Object> uidPath = root.get("uid");
                    list.add(criteriaBuilder.equal(uidPath,uid));
                }
                if (time!=null){
                    Date monday = DateUtils.getMondayOfThisWeek(time);
                    Date sunday = DateUtils.getSundayOfThisWeek(time);
                    list.add(criteriaBuilder.between(root.get("time"), monday, sunday));
                }
                return  criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        List<WorkLog> all = workLogRepository.findAll(workLogSpecification, Sort.by("time").ascending());
        return all;

    }


}



