package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr> {
    @Insert("<script> insert into pms_product_sale_attr (id,product_id,sale_attr_id,sale_attr_name) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.productId},#{item.saleAttrId},#{item.saleAttrName})" +
            "  </foreach>" +
            "</script>")
    int batchInsert(List<PmsProductSaleAttr> saleAttrList);

    @ResultMap("saleAttrMapper")
    @Select("SELECT" +
            "   A.id, A.product_ID, A.sale_attr_id, A.sale_attr_name, " +
            "   B.id AS sale_attr_value_id, B.sale_attr_value_name, " +
            "   if(C.id, 1, 0) AS checked " +
            "FROM" +
            "   pms_product_sale_attr A " +
            "   INNER JOIN pms_product_sale_attr_value B " +
            "       ON A.product_id = #{spuId} " +
            "       AND A.product_id = B.product_id " +
            "       AND A.sale_attr_id = B.sale_attr_id " +
            "   LEFT JOIN pms_sku_sale_attr_value C " +
            "       ON C.sku_id = #{skuId} " +
            "       AND B.id = C.sale_attr_value_id ")
    List<PmsProductSaleAttr> selectCheckedBySpuIdAndSkuId(@Param("spuId") String spuId, @Param("skuId") String skuId);
}
