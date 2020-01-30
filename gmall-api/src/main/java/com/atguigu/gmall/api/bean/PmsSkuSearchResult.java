package com.atguigu.gmall.api.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PmsSkuSearchResult implements Serializable {
    private List<PmsSkuSearch> hits;
    private List<PmsBaseAttrInfo> attrInfoList;
    private long totalHits;
}
