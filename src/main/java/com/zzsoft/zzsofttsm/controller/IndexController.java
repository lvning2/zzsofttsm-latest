package com.zzsoft.zzsofttsm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(maxAge = 3600)
public class IndexController {

    @RequestMapping("/")
    public String index(){              // 设置页面跳转
        return "/Daily.html";
    }


}
