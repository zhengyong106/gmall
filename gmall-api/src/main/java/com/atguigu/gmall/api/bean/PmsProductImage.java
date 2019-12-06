package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 标准商品单元图片
 */
@Data
public class PmsProductImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // spu图片id
    private String productId; // spu id
    private String imgName; // spu图片名称
    private String imgUrl; // spu图片url
}
