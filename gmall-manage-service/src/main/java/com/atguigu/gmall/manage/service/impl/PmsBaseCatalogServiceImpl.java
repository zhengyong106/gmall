package com.atguigu.gmall.manage.service.impl;

import com.atguigu.gmall.api.bean.PmsBaseCatalog1;
import com.atguigu.gmall.api.bean.PmsBaseCatalog2;
import com.atguigu.gmall.api.bean.PmsBaseCatalog3;
import com.atguigu.gmall.api.service.PmsBaseCatalogService;
import com.atguigu.gmall.manage.mapper.PmsBaseCatalog1Mapper;
import com.atguigu.gmall.manage.mapper.PmsBaseCatalog2Mapper;
import com.atguigu.gmall.manage.mapper.PmsBaseCatalog3Mapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PmsBaseCatalogServiceImpl implements PmsBaseCatalogService {
    @Autowired
    PmsBaseCatalog1Mapper catalog1Mapper;

    @Autowired
    PmsBaseCatalog2Mapper catalog2Mapper;

    @Autowired
    PmsBaseCatalog3Mapper catalog3Mapper;

    public List<PmsBaseCatalog1> getCatalog1() {
        return catalog1Mapper.selectAll();
    }

    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 record = new PmsBaseCatalog2();
        record.setCatalog1Id(catalog1Id);
        return catalog2Mapper.select(record);
    }

    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 record = new PmsBaseCatalog3();
        record.setCatalog2Id(catalog2Id);
        return catalog3Mapper.select(record);
    }
}
