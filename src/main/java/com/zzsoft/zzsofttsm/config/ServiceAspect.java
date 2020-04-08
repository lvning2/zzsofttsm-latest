package com.zzsoft.zzsofttsm.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面
 *
 */

@Component
@Aspect
public class ServiceAspect {

    private final Logger logger= LoggerFactory.getLogger(ServiceAspect.class);

    @Around("execution(public * com.zzsoft.zzsofttsm.controller..*.*(..))")
    public Object controllerLog(ProceedingJoinPoint pdj) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        long starttime = System.currentTimeMillis();
        Object[] args = pdj.getArgs();
        Object result = pdj.proceed(args);
        long endtime = System.currentTimeMillis();

        //记录下请求内容
        StringBuilder sb=new StringBuilder();
        sb.append("请求url:"+request.getRequestURL().toString());
        sb.append("     IP:"+request.getRemoteAddr());
        sb.append("     params:"+ Arrays.toString(args));
        sb.append("     CLASS_METHOD:"+pdj.getSignature().getDeclaringTypeName()+"."+pdj.getSignature().getName());
        sb.append("     耗时:"+(endtime-starttime)+"毫秒");
        logger.info(sb.toString());
        return result;
    }

}
