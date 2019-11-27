package com.atguigu.gmall.api.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class PmsBaseCatalog2 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String catalog1Id;
    @Transient
    private List<PmsBaseCatalog3> catalog3List;

}
