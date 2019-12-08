package com.atguigu.gmall.item.controller;

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

import java.util.List;

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
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId);
        // spu销售属性信息
        List<PmsProductSaleAttr> spuSaleAttrListChecked = spuService.getSpuSaleAttrListCheckedBySpuIdAndSkuId(skuInfo.getSpuId(), skuId);
        modelMap.put("spuSaleAttrListChecked", spuSaleAttrListChecked);
        modelMap.put("skuInfo", skuInfo);
        return "item";
    }

}
