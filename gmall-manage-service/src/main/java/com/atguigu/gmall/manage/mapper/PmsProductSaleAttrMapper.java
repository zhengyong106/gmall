package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr> {

    @Insert("<script> insert into pms_product_sale_attr (id,product_id,sale_attr_id,sale_attr_name) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.productId},#{item.saleAttrId},#{item.saleAttrName})\n" +
            "  </foreach>" +
            "</script>")
    int batchInsert(List<PmsProductSaleAttr> saleAttrList);
}
