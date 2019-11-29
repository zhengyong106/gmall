package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsProductImage;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductImageMapper extends Mapper<PmsProductImage> {
    @Insert("<script> insert into pms_product_image (id,product_id,img_name,img_url) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.productId},#{item.imgName},#{item.imgUrl})\n" +
            "  </foreach>" +
            "</script>")
    int batchInsert(List<PmsProductImage> imageList);
}
