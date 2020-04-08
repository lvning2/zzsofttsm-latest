package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.Permission;
import com.zzsoft.zzsofttsm.entity.Role;
import com.zzsoft.zzsofttsm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRole(){           //获取所有角色
        return roleRepository.findAll();
    }

    @Transactional
    public List<Permission> getPermissionOfRole(Long rid){      // 获取角色的所有权限
        Role role = roleRepository.getOne(rid);
        return new ArrayList<Permission>(role.getPermissions());
    }

}
