package com.atguigu.gmall.api.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PmsSkuSearchParam implements Serializable {
    private String catalog3Id;
    private String keyword;
    private String price;
    private int from;
    private int size = 12;
    private List<PmsSkuAttrValue> attrValueList;;
}
