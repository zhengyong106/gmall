package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsSkuImage;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuImageMapper extends Mapper<PmsSkuImage> {
    @Insert("<script> insert into pms_sku_image (id,sku_id,img_name,img_url,spu_img_id,is_default) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.skuId},#{item.imgName},#{item.imgUrl},#{item.spuImgId},#{item.isDefault})\n" +
            "  </foreach>" +
            "</script>")
    int batchInsert(List<PmsSkuImage> skuImageList);
}
