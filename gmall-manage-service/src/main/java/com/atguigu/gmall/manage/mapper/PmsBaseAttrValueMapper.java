package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsBaseAttrValue;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsBaseAttrValueMapper extends Mapper<PmsBaseAttrValue> {
    @Insert("<script> insert into pms_base_attr_value (id,value_name,attr_id,is_enabled) values " +
            "  <foreach collection='list' item='item' separator=',' > " +
            "    (#{item.id},#{item.valueName},#{item.attrId},#{item.isEnabled})\n" +
            "  </foreach>" +
            "</script>")
    int batchInsert(List<PmsBaseAttrValue> attrValueList);
}
