package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 标准商品单元销售属性值
 */
@Data
public class PmsProductSaleAttrValue implements Serializable {
    @Id
    String id ; // spu销售属性值id
    String productId; // spu id
    String saleAttrId; // 基本销售属性id
    String saleAttrValueName; // spu销售属性值名称
    @Transient
    String checked; // 当前销售属性值是否被选中
}
