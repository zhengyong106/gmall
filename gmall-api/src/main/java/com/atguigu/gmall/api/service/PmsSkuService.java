package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsSkuInfo;
import com.atguigu.gmall.api.bean.PmsSkuSaleAttrValue;

import java.util.List;
import java.util.Map;

public interface PmsSkuService {
    PmsSkuInfo getSkuInfoById(String skuId);

    PmsSkuInfo getSkuInfoByIdFromCache(String skuId);

    PmsSkuInfo getSkuInfoByIdFromDB(String skuId);

    Map<String, String> getSkuSaleAttrValueHashBySpuId(String spuId);

    int saveSkuInfo(PmsSkuInfo skuInfo);

    void test(int i);
}
