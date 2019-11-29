package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 平台属性值
 */
@Data
public class PmsBaseAttrValue implements Serializable {
    @Id
    private String id; // 平台属性值id
    private String valueName; // 平台属性值名称
    private String attrId; // 平台属性id
    private String isEnabled; // 平台属性值是否启用
}
