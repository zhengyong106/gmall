package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 商品库存单元图片
 */
@Data
public class PmsSkuImage implements Serializable {
    @Id
    String id; // sku图片id
    String skuId; // sku id
    String imgName; // sku图片名称
    String imgUrl; // sku图片url
    String spuImgId; // spu图片id（spu中包含所有sku图片）
    String isDefault; // 是否是默认图片
}
