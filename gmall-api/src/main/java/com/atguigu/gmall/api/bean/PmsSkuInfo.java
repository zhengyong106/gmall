package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品库存单元信息
 */
@Data
public class PmsSkuInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id; // 商品库存单元（sku）id
    String productId; // 标准商品单元（spu）id
    BigDecimal price; // sku价格
    String skuName; // sku名称
    BigDecimal weight; // sku重量
    String skuDesc; // sku描述信息
    String catalog3Id; // 三级分类id
    String skuDefaultImg; // sku默认图片
    @Transient
    List<PmsSkuImage> skuImageList; // sku图片列表
    @Transient
    List<PmsSkuAttrValue> skuAttrValueList; // sku平台属性值列表
    @Transient
    List<PmsSkuSaleAttrValue> skuSaleAttrValueList; // sku销售属性值列表
}
