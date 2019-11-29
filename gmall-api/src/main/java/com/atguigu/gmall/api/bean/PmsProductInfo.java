package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 标准商品单元信息
 */
@Data
public class PmsProductInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // spu id
    private String productName; // spu名称
    private String description; // spu描述
    private  String catalog3Id; // 三级目录id
    @Transient
    private List<PmsProductSaleAttr> spuSaleAttrList; // spu销售属性列表
    @Transient
    private List<PmsProductImage> spuImageList;  // spu图片列表
}
