package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 标准商品单元销售属性
 */
@Data
public class PmsProductSaleAttr implements Serializable {
    @Id
    String id ; // spu销售属性id
    String productId; // spu id
    String saleAttrId; // 基本销售属性id
    String saleAttrName; // 基本销售属性名称
    @Transient
    List<PmsProductSaleAttrValue> spuSaleAttrValueList; // spu销售属性值列表
}
