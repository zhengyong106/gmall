package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 三级目录
 */
@Data
public class PmsBaseCatalog3 implements Serializable {

    @Id
    private String id; // 三级目录id
    private String name; // 三级目录名称
    private String catalog2Id; // 二级目录id
}
