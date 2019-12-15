package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsSkuInfo;
import com.atguigu.gmall.api.bean.PmsSkuSaleAttrValue;

import java.util.List;
import java.util.Map;

public interface PmsSkuService {
    PmsSkuInfo getSkuInfo(String skuId);

    PmsSkuInfo getSkuInfoFromCache(String skuId);

    PmsSkuInfo getSkuInfoFromDB(String skuId);

    Map<String, String> getSkuSaleAttrValueHashBySpuId(String spuId);

    Map<String, String> getSkuSaleAttrValueHashBySpuIdFromCache(String spuId);

    Map<String, String> getSkuSaleAttrValueHashBySpuIdFromDB(String spuId);

    int saveSkuInfo(PmsSkuInfo skuInfo);
}
