package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 台属性
 */
@Data
public class PmsBaseAttrInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 平台属性id
    private String attrName; // 平台属性名称
    private String catalog3Id; // 三级目录id
    private String isEnabled; // 平台属性是否可用
    @Transient
    List<PmsBaseAttrValue> attrValueList; // 平台属性值列表
}
