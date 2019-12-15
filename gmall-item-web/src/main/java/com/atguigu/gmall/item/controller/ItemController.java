package com.atguigu.gmall.item.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.api.bean.PmsProductSaleAttr;
import com.atguigu.gmall.api.bean.PmsSkuInfo;
import com.atguigu.gmall.api.service.PmsSkuService;
import com.atguigu.gmall.api.service.PmsSpuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class ItemController {
    @Reference
    PmsSpuService spuService;

    @Reference
    PmsSkuService skuService;

    @GetMapping("{skuId}.html")
    public String index(@PathVariable String skuId, ModelMap modelMap) {
        // sku信息
        PmsSkuInfo skuInfo = skuService.getSkuInfoById(skuId);
//        // spu销售属性信息
//        List<PmsProductSaleAttr> spuSaleAttrListChecked = spuService.getSpuSaleAttrListCheckedBySpuIdAndSkuId(skuInfo.getSpuId(), skuId);
//
//        // sku销售属性值列表
//        Map<String, String> saleAttrValueHash = skuService.getSkuSaleAttrValueHashBySpuId(skuInfo.getSpuId());

        modelMap.put("skuInfo", skuInfo);
//        modelMap.put("spuSaleAttrListChecked", spuSaleAttrListChecked);
//        modelMap.put("skuSaleAttrValueHash", JSON.toJSONString(saleAttrValueHash));
        return "item";
    }

    @GetMapping("test")
    public void test() {
        skuService.test(3);
    }
}
