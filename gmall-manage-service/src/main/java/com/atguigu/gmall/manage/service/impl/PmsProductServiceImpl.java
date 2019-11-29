package com.atguigu.gmall.manage.service.impl;

import com.atguigu.gmall.api.bean.PmsProductInfo;
import com.atguigu.gmall.api.service.PmsProductService;
import com.atguigu.gmall.manage.mapper.PmsProductImageMapper;
import com.atguigu.gmall.manage.mapper.PmsProductInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
}
