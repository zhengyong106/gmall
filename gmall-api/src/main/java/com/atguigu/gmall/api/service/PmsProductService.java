package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsProductInfo;

import java.util.List;

public interface PmsProductService {
    List<PmsProductInfo> getSpuList(String catalog3Id);
}
