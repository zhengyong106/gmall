package com.atguigu.gmall.manage.service.impl;

import com.atguigu.gmall.api.bean.PmsProductInfo;
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
}
