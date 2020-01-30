package com.atguigu.gmall.search.mapper;

import com.atguigu.gmall.api.bean.PmsSkuSearch;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PmsSkuSearchMapper {
    @ResultMap("skuSearchMapper")
    @Select("SELECT " +
            "A.id, " +
            "A.sku_name, " +
            "A.sku_desc, " +
            "A.catalog3_id, " +
            "A.price, " +
            "A.sku_default_img, " +
            "0.0 'hot_score', " +
            "A.spu_id, " +
            "B.id 'attr_value_id', " +
            "B.attr_id, " +
            "B.value_id " +
            "FROM " +
            "pms_sku_info A " +
            "LEFT JOIN pms_sku_attr_value B ON A.id = B.sku_id")
    List<PmsSkuSearch> selectAll();
}
