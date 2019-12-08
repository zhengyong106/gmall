package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.*;

import java.util.List;

public interface PmsBaseService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);

    List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> getSaleAttrList();

    int saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
}
