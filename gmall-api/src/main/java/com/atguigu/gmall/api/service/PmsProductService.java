package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsProductImage;
import com.atguigu.gmall.api.bean.PmsProductInfo;
import com.atguigu.gmall.api.bean.PmsProductSaleAttr;

import java.util.List;

public interface PmsProductService {
    List<PmsProductInfo> getSpuList(String catalog3Id);

    List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId);

    List<PmsProductImage> getSpuImageList(String spuId);

    int saveSpuInfo(PmsProductInfo pmsProductInfo);
}
