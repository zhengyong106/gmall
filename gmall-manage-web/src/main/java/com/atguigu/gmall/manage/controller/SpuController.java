package com.atguigu.gmall.manage.controller;

import com.atguigu.gmall.api.bean.PmsProductInfo;
import com.atguigu.gmall.api.service.PmsProductService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpuController {
    @Reference
    PmsProductService productService;

    @RequestMapping("spuList")
    public List<PmsProductInfo> spuList(String catalog3Id){
        return productService.getSpuList(catalog3Id);
    }
}
