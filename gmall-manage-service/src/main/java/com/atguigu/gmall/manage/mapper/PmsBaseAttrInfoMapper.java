package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.api.bean.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface PmsBaseAttrInfoMapper extends Mapper<PmsBaseAttrInfo> {
    @ResultMap("attrInfoMapper")
    @Select("<script>" +
            "SELECT " +
            "A.*, " +
            "B.id value_id, " +
            "B.attr_id, " +
            "B.value_name, " +
            "B.is_enabled value_is_enabled " +
            "FROM " +
            "pms_base_attr_info A " +
            "LEFT JOIN pms_base_attr_value B ON A.id = B.attr_id " +
            "WHERE " +
            "B.id IN " +
            "<foreach item=\"item\" collection=\"collection\" open=\"(\" separator=\",\" close=\")\">" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<PmsBaseAttrInfo> selectByAttrValueIds(Set<String> attrValueIds);
}
