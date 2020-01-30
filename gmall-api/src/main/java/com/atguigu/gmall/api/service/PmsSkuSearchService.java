package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.PmsSkuSearchParam;
import com.atguigu.gmall.api.bean.PmsSkuSearchResult;

import java.io.IOException;

public interface PmsSkuSearchService {
    boolean initIndex() throws IOException;
    boolean initDocument() throws IOException;
    PmsSkuSearchResult searchDocument(PmsSkuSearchParam searchParam) throws IOException;
}
