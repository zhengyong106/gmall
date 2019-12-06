package com.atguigu.gmall.manage.service.impl;

import com.atguigu.gmall.api.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.api.bean.PmsBaseAttrValue;
import com.atguigu.gmall.api.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.api.service.PmsBaseService;
import com.atguigu.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class PmsBaseServiceImpl implements PmsBaseService {
    @Autowired
    PmsBaseAttrInfoMapper attrInfoMapper;

    @Autowired
    PmsBaseAttrValueMapper attrValueMapper;

    @Autowired
    PmsBaseSaleAttrMapper saleAttrMapper;

    public List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id) {
        // 查询平台属性列表
        PmsBaseAttrInfo record = new PmsBaseAttrInfo();
        record.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> attrInfoList = attrInfoMapper.select(record);

        // 关联查询平台属性值列表
        PmsBaseAttrValue record2 = new PmsBaseAttrValue();
        record2.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrValue> attrValueList = (attrValueMapper.select(record2));

        // 生成字典映射平台属性和平台属性值
        Map<String, List<PmsBaseAttrValue>> relationMap = new HashMap<>();
        for(PmsBaseAttrValue attrValue: attrValueList){
            String attrInfoId = attrValue.getAttrId();
            if (null != relationMap.get(attrInfoId)){
                relationMap.get(attrInfoId).add(attrValue);
            } else {
                relationMap.put(attrInfoId, new ArrayList<>(Collections.singletonList(attrValue)));
            }
        }

        // 通过字典关联查询
        for(PmsBaseAttrInfo attrInfo: attrInfoList){
            String attrInfoId = attrInfo.getId();
            attrInfo.setAttrValueList(relationMap.get(attrInfoId));
        }
        return attrInfoList;
    }

    public List<PmsBaseSaleAttr> getSaleAttrList() {
        return saleAttrMapper.selectAll();
    }

    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue record = new PmsBaseAttrValue();
        record.setAttrId(attrId);
        return attrValueMapper.select(record);
    }

    public int saveAttrInfo(PmsBaseAttrInfo attrInfo) {
        List<PmsBaseAttrValue> attrValueList = attrInfo.getAttrValueList();
        int saved;
        // 插入或修改平台属性
        if (null == attrInfo.getId()) {
            saved = attrInfoMapper.insertSelective(attrInfo);
        } else {
            saved = attrInfoMapper.updateByPrimaryKey(attrInfo);
        }

        // 删除所有平台属性的属性值
        PmsBaseAttrValue record = new PmsBaseAttrValue();
        record.setAttrId(attrInfo.getId());
        attrValueMapper.delete(record);

        // 插入新的平台属性值
        for (PmsBaseAttrValue attrValue: attrValueList){
            attrValue.setId(null);
            attrValue.setAttrId(attrInfo.getId());
        }
        attrValueMapper.batchInsert(attrValueList);
        return saved;
    }
}
