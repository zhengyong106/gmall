package com.atguigu.gmall.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("list.html")
    public String list() {
        return "list";
    }
}
