package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsBaseAttrInfo;

import java.util.List;

public interface PmsBaseAttrService {
    List<PmsBaseAttrInfo> getAttrInfo(String catalog3Id);
}
