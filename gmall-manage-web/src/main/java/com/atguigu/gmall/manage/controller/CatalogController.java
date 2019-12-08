package com.atguigu.gmall.manage.controller;

import com.atguigu.gmall.api.bean.PmsBaseCatalog1;
import com.atguigu.gmall.api.bean.PmsBaseCatalog2;
import com.atguigu.gmall.api.bean.PmsBaseCatalog3;
import com.atguigu.gmall.api.service.PmsBaseService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品类目管理
 */
@CrossOrigin
@RestController
public class CatalogController {
    @Reference
    PmsBaseService baseService;

    @RequestMapping("getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1(){
        return baseService.getCatalog1();
    }

    @RequestMapping("getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){
        return baseService.getCatalog2(catalog1Id);
    }

    @RequestMapping("getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){
        return baseService.getCatalog3(catalog2Id);
    }
}
