package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Long> {

    @Query(value = "select p from Problem p where p.gid=?1 and p.time between ?2 and ?3")
    List<Problem> findByGidAndTime(Integer gid, Date monday, Date sunday);

}
