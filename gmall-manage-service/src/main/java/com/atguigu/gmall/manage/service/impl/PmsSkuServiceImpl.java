package com.atguigu.gmall.manage.service.impl;

import com.atguigu.gmall.api.bean.*;
import com.atguigu.gmall.api.service.PmsProductService;
import com.atguigu.gmall.api.service.PmsSkuService;
import com.atguigu.gmall.manage.mapper.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PmsSkuServiceImpl implements PmsSkuService {

    @Autowired
    PmsSkuInfoMapper skuInfoMapper;

    @Autowired
    PmsSkuImageMapper skuImageMapper;

    @Autowired
    PmsSkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public int saveSkuInfo(PmsSkuInfo skuInfo) {
        // 保存sku信息
        skuInfo.setId(null);
        int inserted = skuInfoMapper.insert(skuInfo);

        // 保存sku图片列表
        List<PmsSkuImage> imageList = skuInfo.getSkuImageList();
        for (PmsSkuImage image: imageList){
            image.setId(null);
            image.setSkuId(skuInfo.getId());
        }
        skuImageMapper.batchInsert(imageList);

        // 保存sku平台属性值列表
        List<PmsSkuAttrValue> attrValueList = skuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue attrValue: attrValueList){
            attrValue.setId(null);
            attrValue.setSkuId(skuInfo.getId());
        }
        skuAttrValueMapper.batchInsert(attrValueList);

        // 保存sku销售属性值列表
        List<PmsSkuSaleAttrValue> saleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue saleAttrValue: saleAttrValueList){
            saleAttrValue.setId(null);
            saleAttrValue.setSkuId(skuInfo.getId());
        }
        skuSaleAttrValueMapper.batchInsert(saleAttrValueList);
        return inserted;
    }
}
