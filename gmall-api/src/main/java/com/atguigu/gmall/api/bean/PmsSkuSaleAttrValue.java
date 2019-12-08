package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 商品库存单元销售属性值
 */
@Data
public class PmsSkuSaleAttrValue implements Serializable {
    @Id
    String id; // sku销售属性值id
    String spuId; // spu id
    String skuId; // sku id
    String saleAttrId; // 销售属性id
    String saleAttrValueId; // 销售属性值id
    String saleAttrName; // 销售属性名称
    String saleAttrValueName; // 销售属性值名称
}
