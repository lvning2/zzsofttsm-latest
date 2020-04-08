package com.zzsoft.zzsofttsm.config;

import com.zzsoft.zzsofttsm.entity.Permission;
import com.zzsoft.zzsofttsm.entity.Role;
import com.zzsoft.zzsofttsm.entity.User;
import com.zzsoft.zzsofttsm.service.LoginService;
import com.zzsoft.zzsofttsm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 *  自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入了授权方法..."+principalCollection.getPrimaryPrincipal());

        Subject subject = SecurityUtils.getSubject();
        User user =(User) subject.getSession().getAttribute("user");

        //User user = userService.findUserByUsername(username);
        //System.out.println(user.getName()+" "+user.getId());

        List<Role> roles = userService.getRoleOfUser(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        for(Role role : roles){
            info.addRole(role.getEnname());
            Set<Permission> permissions = role.getPermissions();
            for(Permission permission : permissions){
                info.addStringPermission(permission.getPermissionName());
            }

        }

        System.out.println(info.getStringPermissions());


        return info;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //System.out.println("进入了认证方法...");
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;

        User user = loginService.getUeserByUsername(token.getUsername());
        if (user==null){        // 用户名不存在
            return null;       // shrio 底层会抛出 UnKnowAccountExcption
        }

        return new SimpleAuthenticationInfo("",user.getPassword(),"");

    }

}





