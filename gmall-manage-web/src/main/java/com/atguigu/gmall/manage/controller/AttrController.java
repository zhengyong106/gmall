package com.atguigu.gmall.manage.controller;

import com.atguigu.gmall.api.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.api.bean.PmsBaseAttrValue;
import com.atguigu.gmall.api.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.api.service.PmsBaseService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品平台属性管理
 */
@CrossOrigin
@RestController
public class AttrController {
    @Reference
    PmsBaseService baseService;

    @RequestMapping("attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam String catalog3Id){
        return baseService.getAttrInfoList(catalog3Id);
    }

    @RequestMapping("getAttrValueList")
    public List<PmsBaseAttrValue> attrValueList(@RequestParam String attrId){
        return baseService.getAttrValueList(attrId);
    }

    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo, HttpServletRequest request){
        return baseService.saveAttrInfo(pmsBaseAttrInfo) != 0 ? "SUCCESS": "FIELD";
    }

    @RequestMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> saleAttrList(){
        return baseService.getSaleAttrList();
    }
}
