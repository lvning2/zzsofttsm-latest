package com.zzsoft.zzsofttsm.repository;

import com.zzsoft.zzsofttsm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    User findByAttendanceId(String attendanceId);

    List<User> findByGid(Integer gid);

    @Modifying
    @Query("update User u set u.rid=?2 where u.id=?1")
    void updateUserRole(Integer uid,Integer rid);


    @Query("select u from User u where u.rid=0")
    List<User> getManager();


}




