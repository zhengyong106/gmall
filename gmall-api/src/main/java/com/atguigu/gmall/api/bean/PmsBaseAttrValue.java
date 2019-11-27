package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class PmsBaseAttrValue implements Serializable {
    @Id
    private String id;
    private String valueName;
    private String attrId;
    private String isEnabled;
}
