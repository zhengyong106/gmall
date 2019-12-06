package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsSkuAttrValue;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuAttrValueMapper extends Mapper<PmsSkuAttrValue> {
    @Insert("<script> insert into pms_sku_attr_value (id,attr_id,value_id,sku_id) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.attrId},#{item.valueId},#{item.skuId})\n" +
            "  </foreach>" +
            "</script>")
    int batchInsert(List<PmsSkuAttrValue> skuAttrValueList);
}
