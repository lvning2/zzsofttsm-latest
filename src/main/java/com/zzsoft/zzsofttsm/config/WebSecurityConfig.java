//package com.zzsoft.zzsofttsm.config;
//
//import com.zzsoft.zzsofttsm.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    UserService userService;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        // 设置默认的加密方式
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//    }
//
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////
////        // 将 check_token 暴露出去，否则资源服务器访问时报 403 错误
////       // web.ignoring().antMatchers("/**");
//////        web.ignoring().antMatchers("/login.html")
//////                .and().ignoring().antMatchers("/login2")
//////                .and().ignoring().antMatchers("/list.html")
//////                .and().ignoring().antMatchers("/css/**")
//////                .and().ignoring().antMatchers("/img/**")
//////                .and().ignoring().antMatchers("/js/**")
//////                .and().ignoring().antMatchers("/plan/**")
////
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/css/**","/img/**","/js/**","/layui/**").permitAll()
//                .and()
//                .formLogin().loginPage("/login");
//
//        http.logout().logoutSuccessUrl("/login.html");
//
//    }
//
//}
//
//
