//package com.zzsoft.zzsofttsm.config.service;
//
//import com.zzsoft.zzsofttsm.config.domain.Permission;
//import com.zzsoft.zzsofttsm.entity.User;
//import com.zzsoft.zzsofttsm.mapper.UserMapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
//{
//
//    @Resource
//    private UserMapper userMapper;
//
//    @Autowired
//    private PermissionService permissionService;
//
//    @Override
//   public UserDetails loadUserByUsername(String username){
//        User user = userMapper.selectByUsername(username);
//        //System.out.println("测试...ceshi...");
//        //List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
//        List<GrantedAuthority> grantedAuthorities = new ArrayList();
//        if(user!=null){
//            List<Permission> tbPermissions = permissionService.selectByUserId(user.getId());
//
//            // 声明用户授权
//            tbPermissions.forEach(tbPermission -> {
//                if (tbPermission != null && tbPermission.getEnname() != null) {
//                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getEnname());
//                    grantedAuthorities.add(grantedAuthority);
//                }
//            });
//
//        }
//
//        //System.out.println(grantedAuthorities);
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
//
//    }
//
//
//}
//
//
//
//
//
