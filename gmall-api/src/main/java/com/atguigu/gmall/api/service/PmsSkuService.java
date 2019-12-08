package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsSkuInfo;

public interface PmsSkuService {
    PmsSkuInfo getSkuById(String skuId);

    int saveSkuInfo(PmsSkuInfo skuInfo);
}
