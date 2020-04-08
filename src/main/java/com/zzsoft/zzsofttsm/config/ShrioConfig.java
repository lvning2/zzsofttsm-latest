package com.zzsoft.zzsofttsm.config;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShrioConfig {

    // 自定义Realm
    @Bean(name = "userRealm")
    public UserRealm userRealm(@Qualifier("bCryptCredentialsMatcher") BCryptCredentialsMatcher bCryptCredentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(bCryptCredentialsMatcher);

        return userRealm;
    }

    // 避免这个错误：Consider injecting the bean as one of its interfaces or forcing the use of CGLib-based proxies by
    // setting proxyTargetClass=true on @EnableAsync and/or @EnableCaching.
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    // SecurityManager关联Realm
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);

        return securityManager;
    }



    // 配置url过滤器
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){

        DefaultShiroFilterChainDefinition chainDefinition=new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/login.html","anon");
        chainDefinition.addPathDefinition("/shrioLogin","anon");
        chainDefinition.addPathDefinition("/css/**","anon");
        chainDefinition.addPathDefinition("/js/**","anon");
        chainDefinition.addPathDefinition("/img/**","anon");
        chainDefinition.addPathDefinition("/layui/**","anon");
        chainDefinition.addPathDefinition("/v2/**","anon");
        chainDefinition.addPathDefinition("/swagger-resources/**","anon");
        chainDefinition.addPathDefinition("/webjars/**","anon");
        chainDefinition.addPathDefinition("/swagger-ui.html/**","anon");

        //chainDefinition.addPathDefinition("/**","authc");
        //chainDefinition.addPathDefinition("/**","shrioAuthFilter");

        return chainDefinition;

    }





    // 设置用于匹配密码的CredentialsMatcher
    @Bean("bCryptCredentialsMatcher")
    public BCryptCredentialsMatcher bCryptCredentialsMatcher() {
        return new BCryptCredentialsMatcher();
    }

    //开启shiro aop注解支持
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//        System.out.println("开启了Shiro注解支持");
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }


}
