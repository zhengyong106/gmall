package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 销售属性
 */
@Data
public class PmsBaseSaleAttr implements Serializable {
    @Id
    String id ;
    String name;
}
