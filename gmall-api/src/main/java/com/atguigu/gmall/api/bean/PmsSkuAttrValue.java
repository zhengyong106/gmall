package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 商品库存单元平台属性值
 */
@Data
public class PmsSkuAttrValue implements Serializable {
    @Id
    String id; // sku平台属性id
    String attrId; // 平台属性id
    String valueId; // 平台属性值id
    String skuId; // sku id
}
