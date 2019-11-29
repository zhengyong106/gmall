package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 二级目录
 */
@Data
public class PmsBaseCatalog2 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 二级目录id
    private String name; // 二级目录名称
    private String catalog1Id; // 一级目录id
    @Transient
    private List<PmsBaseCatalog3> catalog3List; // // 三级目录列表

}
