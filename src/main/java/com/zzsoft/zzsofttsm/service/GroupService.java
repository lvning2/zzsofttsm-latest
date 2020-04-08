package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.Group;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.repository.GroupRepository;
import com.zzsoft.zzsofttsm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public List getAll(){
        return groupRepository.findAll(Sort.by("id").ascending());
    }

    public List<User> getUsersByGid(Integer gid){
        List<User> byGid = userRepository.findByGid(gid);
        for(User u:byGid){
            u.setPassword("");
        }
        return byGid;
    }


    public Group getById(Integer id) {
        return groupRepository.getOne(id);
    }

}

