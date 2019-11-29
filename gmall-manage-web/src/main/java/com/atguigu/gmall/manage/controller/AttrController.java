package com.atguigu.gmall.manage.controller;

import com.atguigu.gmall.api.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.api.service.PmsBaseAttrService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品平台属性管理
 */
@CrossOrigin
@RestController
public class AttrController {
    @Reference
    PmsBaseAttrService attrService;

    @RequestMapping("attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        return attrService.getAttrInfo(catalog3Id);
    }
}
