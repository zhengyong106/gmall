package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsProductSaleAttrValue;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductSaleAttrValueMapper extends Mapper<PmsProductSaleAttrValue> {
    @Insert("<script> insert into pms_product_sale_attr_value (id,product_id,sale_attr_id,sale_attr_value_name) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.productId},#{item.saleAttrId},#{item.saleAttrValueName})\n" +
            "  </foreach>" +
            "</script>")
    int batchInsert(List<PmsProductSaleAttrValue> saleAttrValueList);
}
