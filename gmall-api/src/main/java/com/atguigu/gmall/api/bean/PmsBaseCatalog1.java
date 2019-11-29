package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 一级目录
 */
@Data
public class PmsBaseCatalog1 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 一级目录id
    private String name; // 一级目录名称
    @Transient
    private List<PmsBaseCatalog2> catalog2List;  // 二级目录列表
}

