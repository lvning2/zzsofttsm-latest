package com.zzsoft.zzsofttsm.mapper;


import com.zzsoft.zzsofttsm.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper  {

    public static List<User> toListUser(List<User> users){      // 密码置空
        List<User> list=new ArrayList<>();
        for(User user : users){
            User mapper = CGlibMapper.mapper(user, User.class);
            mapper.setPassword("");
            list.add(mapper);
        }
        return list;
    }


}
