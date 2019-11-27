package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class PmsBaseAttrInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String attrName;
    private String catalog3Id;
    private String isEnabled;
}
