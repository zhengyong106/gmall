package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsProductImage;
import com.atguigu.gmall.api.bean.PmsProductInfo;
import com.atguigu.gmall.api.bean.PmsProductSaleAttr;
import com.atguigu.gmall.api.bean.PmsSkuSaleAttrValue;

import java.util.List;

public interface PmsSpuService {
    List<PmsProductInfo> getSpuList(String catalog3Id);

    List<PmsProductSaleAttr> getSpuSaleAttrListBySpuId(String spuId);

    List<PmsProductSaleAttr> getSpuSaleAttrListCheckedBySpuIdAndSkuId(String spuId, String skuId);

    List<PmsProductImage> getSpuImageList(String spuId);

    int saveSpuInfo(PmsProductInfo pmsProductInfo);
}
