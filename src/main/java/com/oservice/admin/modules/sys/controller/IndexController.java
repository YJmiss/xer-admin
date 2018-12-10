package com.oservice.admin.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: oservice
 * @description: IndexController
 * @author: YJmiss
 * @create: 2018-12-07 15:24
 **/
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
