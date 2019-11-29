package com.atguigu.gmall.manage.service.impl;

import com.atguigu.gmall.api.bean.PmsProductImage;
import com.atguigu.gmall.api.bean.PmsProductInfo;
import com.atguigu.gmall.api.bean.PmsProductSaleAttr;
import com.atguigu.gmall.api.bean.PmsProductSaleAttrValue;
import com.atguigu.gmall.api.service.PmsProductService;
import com.atguigu.gmall.manage.mapper.PmsProductImageMapper;
import com.atguigu.gmall.manage.mapper.PmsProductInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class PmsProductServiceImpl implements PmsProductService {
    @Autowired
    PmsProductInfoMapper productInfoMapper;

    @Autowired
    PmsProductImageMapper productImageMapper;

    @Autowired
    PmsProductSaleAttrMapper productSaleAttrMapper;

    @Autowired
    PmsProductSaleAttrValueMapper productSaleAttrValueMapper;

    public List<PmsProductInfo> getSpuList(String catalog3Id) {
        PmsProductInfo record = new PmsProductInfo();
        record.setCatalog3Id(catalog3Id);
        return productInfoMapper.select(record);
    }

    public List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId) {
        PmsProductSaleAttr record = new PmsProductSaleAttr();
        record.setProductId(spuId);
        return productSaleAttrMapper.select(record);
    }

    public List<PmsProductImage> getSpuImageList(String spuId) {
        PmsProductImage record = new PmsProductImage();
        record.setProductId(spuId);
        return productImageMapper.select(record);
    }

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
