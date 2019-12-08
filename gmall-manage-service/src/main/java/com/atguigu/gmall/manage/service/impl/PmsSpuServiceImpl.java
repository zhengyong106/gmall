package com.atguigu.gmall.manage.service.impl;

import com.atguigu.gmall.api.bean.PmsProductImage;
import com.atguigu.gmall.api.bean.PmsProductInfo;
import com.atguigu.gmall.api.bean.PmsProductSaleAttr;
import com.atguigu.gmall.api.bean.PmsProductSaleAttrValue;
import com.atguigu.gmall.api.service.PmsSpuService;
import com.atguigu.gmall.manage.mapper.PmsProductImageMapper;
import com.atguigu.gmall.manage.mapper.PmsProductInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class PmsSpuServiceImpl implements PmsSpuService {
    @Autowired
    PmsProductInfoMapper productInfoMapper;

    @Autowired
    PmsProductImageMapper productImageMapper;

    @Autowired
    PmsProductSaleAttrMapper productSaleAttrMapper;

    @Autowired
    PmsProductSaleAttrValueMapper productSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> getSpuList(String catalog3Id) {
        PmsProductInfo record = new PmsProductInfo();
        record.setCatalog3Id(catalog3Id);
        return productInfoMapper.select(record);
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrListBySpuId(String spuId) {
        // 查询销售属性列表
        PmsProductSaleAttr record = new PmsProductSaleAttr();
        record.setProductId(spuId);
        List<PmsProductSaleAttr> saleAttrList = productSaleAttrMapper.select(record);

        // 查询销售属性值列表
        PmsProductSaleAttrValue record2 = new PmsProductSaleAttrValue();
        record2.setProductId(spuId);
        List<PmsProductSaleAttrValue> saleAttrValueList = productSaleAttrValueMapper.select(record2);

        // 生成字典映射销售属性和销售属性值
        Map<String, List<PmsProductSaleAttrValue>> relationMap = new HashMap<>();
        for(PmsProductSaleAttrValue saleAttrValue: saleAttrValueList){
            String saleAttrId = saleAttrValue.getSaleAttrId();
            if (null != relationMap.get(saleAttrId)){
                relationMap.get(saleAttrId).add(saleAttrValue);
            } else {
                relationMap.put(saleAttrId, new ArrayList<>(Collections.singletonList(saleAttrValue)));
            }
        }

        // 通过字典关联查询
        for(PmsProductSaleAttr saleAttr: saleAttrList){
            String saleAttrId = saleAttr.getSaleAttrId();
            saleAttr.setSpuSaleAttrValueList(relationMap.get(saleAttrId));
        }
        return saleAttrList;
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrListCheckedBySpuIdAndSkuId(String spuId, String skuId) {
        return productSaleAttrMapper.selectCheckedBySpuIdAndSkuId(spuId, skuId);
    }

    @Override
    public List<PmsProductImage> getSpuImageList(String spuId) {
        PmsProductImage record = new PmsProductImage();
        record.setProductId(spuId);
        return productImageMapper.select(record);
    }

    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {
        pmsProductInfo.setId(null);
        int inserted = productInfoMapper.insert(pmsProductInfo);

        List<PmsProductSaleAttr> saleAttrList = pmsProductInfo.getSpuSaleAttrList();
        List<PmsProductSaleAttrValue> saleAttrValueList = new ArrayList<>();
        List<PmsProductImage> imageList = pmsProductInfo.getSpuImageList();

        // 遍历spu销售属性列表
        if (null != saleAttrList && !saleAttrList.isEmpty()) {
            for (PmsProductSaleAttr saleAttr : saleAttrList) {
                saleAttr.setId(null);
                saleAttr.setProductId(pmsProductInfo.getId());

                // 遍历spu销售属性列表中的属性值
                List<PmsProductSaleAttrValue> tempList = saleAttr.getSpuSaleAttrValueList();
                if (null != tempList && !tempList.isEmpty()) {
                    for (PmsProductSaleAttrValue saleAttrValue : tempList) {
                        saleAttrValue.setId(null);
                        saleAttrValue.setProductId(pmsProductInfo.getId());
                        saleAttrValueList.add(saleAttrValue);
                    }
                }
            }
            // 批量插入spu销售属性列表
            productSaleAttrMapper.batchInsert(saleAttrList);
            if (!saleAttrValueList.isEmpty()) {
                // 批量插入spu销售属性值列表
                productSaleAttrValueMapper.batchInsert(saleAttrValueList);
            }
        }

        // 遍历spu图片列表
        if(null != imageList && !imageList.isEmpty()) {
            for(PmsProductImage image: imageList) {
                image.setId(null);
                image.setProductId(pmsProductInfo.getId());
            }
            // 批量插入spu图片列表
            productImageMapper.batchInsert(imageList);
        }

        return inserted;
    }
}
