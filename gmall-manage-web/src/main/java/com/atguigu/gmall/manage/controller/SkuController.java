package com.atguigu.gmall.manage.controller;

import com.atguigu.gmall.api.bean.PmsSkuInfo;
import com.atguigu.gmall.api.service.PmsSkuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SkuController {
    @Reference
    PmsSkuService skuService;

    @RequestMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody PmsSkuInfo skuInfo) {
        return skuService.saveSkuInfo(skuInfo) > 0 ? "SUCCESS": "FIELD";
    }
}
