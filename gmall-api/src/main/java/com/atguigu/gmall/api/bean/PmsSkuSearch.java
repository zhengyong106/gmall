package com.atguigu.gmall.api.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PmsSkuSearch implements Serializable {
    private long id;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private BigDecimal price;
    private String skuDefaultImg;
    private double hotScore;
    private String spuId;
    private List<PmsSkuAttrValue> attrValueList;
}
