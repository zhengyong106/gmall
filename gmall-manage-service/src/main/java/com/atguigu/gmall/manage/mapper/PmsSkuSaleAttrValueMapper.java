package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsSkuSaleAttrValue;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuSaleAttrValueMapper extends Mapper<PmsSkuSaleAttrValue> {
    @Insert("<script> insert into pms_sku_sale_attr_value " +
            "(id,sku_id,sale_attr_id,sale_attr_value_id,sale_attr_name,sale_attr_value_name) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.skuId},#{item.saleAttrId},#{item.saleAttrValueId},#{item.saleAttrName},#{item.saleAttrValueName})\n" +
            "  </foreach>" +
            "</script>")
    void batchInsert(List<PmsSkuSaleAttrValue> saleAttrValueList);
}
