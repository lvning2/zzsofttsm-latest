package com.zzsoft.zzsofttsm.interceptor;

import com.zzsoft.zzsofttsm.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            return true;        // 放行
        }

        //request.getRequestDispatcher("/login").forward(request,response);
        response.sendRedirect("/login.html");
        //System.out.println("拦截...");
        return false;           // 拦截

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
