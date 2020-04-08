package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.Role;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.mapper.CGlibMapper;
import com.zzsoft.zzsofttsm.mapper.UserMapper;
import com.zzsoft.zzsofttsm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService{

    @Value("${spring.application.user.initPassword}")
    private String initPassword;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateUserRole(Integer uid,Integer rid){        //改变用户角色
        userRepository.updateUserRole(uid,rid);
    }

    @Transactional
    public List getAllUser(){       //获取所有用户
        List<User> all = userRepository.findAll();
        List<User> list = UserMapper.toListUser(all);
        return list;
    }

    public User findUserByUsername(String username){    // 通过用户名查询账户
        return userRepository.findByUsername(username);
    }

    @Transactional
    public List<Role> getRoleOfUser(Integer uid){       // 获取用户所有角色
        User user = userRepository.getOne(uid);
        return new ArrayList<Role>(user.getRoles());
    }

    @Transactional
    public void resetPassword(Integer uid){         // 重置密码
        User user = userRepository.getOne(uid);
        user.setPassword(initPassword);
        userRepository.save(user);
    }

    public User getUserById(Integer id){
        return userRepository.getOne(id);
    }

    public boolean updatePassword(Integer uid,String oldPassword,String newPassword){     // 修改密码
        User user = userRepository.getOne(uid);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodeOld = bCryptPasswordEncoder.encode(oldPassword);
        boolean matches = bCryptPasswordEncoder.matches(encodeOld, user.getPassword());
        if(matches){
            //return "原密码不正确";
            return false;
        }else {
            String encodeNew = bCryptPasswordEncoder.encode(newPassword);
            user.setPassword(encodeNew);
            userRepository.save(user);
            //return "修改成功";
            return true;
        }

    }

    @Transactional
    public void updateIpAndTime(Integer id,String ip){
        User one = userRepository.getOne(id);
        one.setLastLoginTime(new Date());
        one.setLastLoginIp(ip);
        userRepository.save(one);
    }


}



