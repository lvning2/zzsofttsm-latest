package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.mapper.CGlibMapper;
import com.zzsoft.zzsofttsm.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;


    private Logger logger= LoggerFactory.getLogger(LoginService.class);

    public User login(User u, HttpServletRequest request){
        User user = userRepository.findByUsername(u.getUsername());
        //logger.info(request.getRemoteAddr()+" "+u.getUsername()+"("+u.getName()+"),try login...");

        if(null==user){
            logger.info(request.getRemoteAddr()+" "+u.getUsername()+"("+u.getName()+"),login failed.");
            return null;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(u.getPassword());
        boolean matches = bCryptPasswordEncoder.matches(u.getPassword(), user.getPassword());

        if(matches){
            user.setPassword("");   //密码置空
            logger.info(request.getRemoteAddr()+" "+user.getUsername()+"("+user.getName()+"),login success.");
            return user;
        }
        return null;
    }

    public boolean register(User user){
        User byUsername = userRepository.findByUsername(user.getUsername());
        if(null!=byUsername){
            return false;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return true;

    }



    public List getAllTest(Pageable pageable){       //获取所有用户,分页,---测试
        Specification<User> userSpecification= new Specification<User>(){
            @Override
            public Predicate toPredicate(javax.persistence.criteria.Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder){
                return null;
            }
        };

        Page<User> all1 = userRepository.findAll(pageable);
        List<User> all = all1.getContent();
        for(User x: all){
            x.setPassword("");
        }
        return all;
    }

    public User getUserByid(Integer id){    //通过id获取用户
        return userRepository.getOne(id);

    }

    public void setLastLoginTimeAndIp(Integer uid){    // 登录成功后设置时间和ip
        User user = userRepository.getOne(uid);
    }


    public List<User> getManager(){         //获取经理
        return userRepository.getManager();
    }

    // shrio登录使用
    public User getUeserByUsername(String username){
        User byUsername = userRepository.findByUsername(username);
        User mapper = CGlibMapper.mapper(byUsername, User.class);
        return mapper;
    }

}


