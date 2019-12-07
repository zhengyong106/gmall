package com.atguigu.gmall.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
public class ItemController {
    @RequestMapping("item")
    public String index(ModelMap modelMap) {
        modelMap.put("123", "231");
        modelMap.put("222", "3332221");
        System.out.println("119213");
        return "item";
    }
}
