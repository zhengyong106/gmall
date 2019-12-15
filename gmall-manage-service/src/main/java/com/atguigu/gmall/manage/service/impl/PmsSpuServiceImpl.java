package com.atguigu.gmall.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gmall.api.bean.*;
import com.atguigu.gmall.api.service.PmsSpuService;
import com.atguigu.gmall.manage.mapper.PmsProductImageMapper;
import com.atguigu.gmall.manage.mapper.PmsProductInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.atguigu.gmall.service.util.RedisUtil;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class PmsSpuServiceImpl implements PmsSpuService {
    @Autowired
    PmsProductInfoMapper productInfoMapper;

    @Autowired
    PmsProductImageMapper productImageMapper;

    @Autowired
    PmsProductSaleAttrMapper productSaleAttrMapper;

    @Autowired
    PmsProductSaleAttrValueMapper productSaleAttrValueMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    private final Logger LOGGER = LoggerFactory.getLogger(PmsSkuServiceImpl.class);
    private final int REDIS_KEY_EXPIRE = 30 * 60;
    private final int REDIS_NULL_KEY_EXPIRE = 10;

    @Override
    public List<PmsProductInfo> getSpuList(String catalog3Id) {
        PmsProductInfo record = new PmsProductInfo();
        record.setCatalog3Id(catalog3Id);
        return productInfoMapper.select(record);
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrListBySpuId(String spuId) {
        // 查询销售属性列表
        PmsProductSaleAttr record = new PmsProductSaleAttr();
        record.setProductId(spuId);
        List<PmsProductSaleAttr> saleAttrList = productSaleAttrMapper.select(record);

        // 查询销售属性值列表
        PmsProductSaleAttrValue record2 = new PmsProductSaleAttrValue();
        record2.setProductId(spuId);
        List<PmsProductSaleAttrValue> saleAttrValueList = productSaleAttrValueMapper.select(record2);

        // 生成hash映射销售属性和销售属性值
        Map<String, List<PmsProductSaleAttrValue>> relationMap = new HashMap<>();
        for(PmsProductSaleAttrValue saleAttrValue: saleAttrValueList){
            String saleAttrId = saleAttrValue.getSaleAttrId();
            if (null != relationMap.get(saleAttrId)){
                relationMap.get(saleAttrId).add(saleAttrValue);
            } else {
                relationMap.put(saleAttrId, new ArrayList<>(Collections.singletonList(saleAttrValue)));
            }
        }

        // 通过hash关联查询
        for(PmsProductSaleAttr saleAttr: saleAttrList){
            String saleAttrId = saleAttr.getSaleAttrId();
            saleAttr.setSpuSaleAttrValueList(relationMap.get(saleAttrId));
        }
        return saleAttrList;
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrListCheckedBySpuIdAndSkuId(String spuId, String skuId) {
        String redisKey = "spu:" + spuId + "-" + skuId + ":saleAttrListChecked";
        String lockName = "spu:" + spuId + "-" + skuId + ":saleAttrListChecked:lock";

        // 先尝试查找缓存
        List<PmsProductSaleAttr> saleAttrList = getSpuSaleAttrListCheckedBySpuIdAndSkuIdFromCache(redisKey);

        if (null != saleAttrList) {
            return saleAttrList;
        } else {
            // 添加分布式锁，防止缓存击穿
            RLock lock = null;
            try {
                lock = redissonClient.getLock(lockName);
                lock.lock();
            } catch (Exception e) {
                LOGGER.error("分布式锁获取失败！", e);
            }

            // 再次查找缓存，防止锁争抢导致数据库的重复查询
            saleAttrList = getSpuSaleAttrListCheckedBySpuIdAndSkuIdFromCache(redisKey);
            if (null != saleAttrList) {
                if (null != lock) lock.unlock(); // 释放锁
                return saleAttrList;
            }

            // 如果查询不到结果，则通过数据库查询
            try {
                saleAttrList = getSpuSaleAttrListCheckedBySpuIdAndSkuIdFromDB(spuId, skuId);
            } catch (Exception e){
                if (null != lock) lock.unlock(); // 释放锁
                throw e;
            }

            // 将查询结果添加到缓存
            try {
                // 需要缓存查询为空的数据，防止缓存穿透
                if (null == saleAttrList) {
                    redisUtil.set(redisKey, "", REDIS_NULL_KEY_EXPIRE);
                } else {
                    redisUtil.set(redisKey, JSON.toJSONString(saleAttrList), REDIS_KEY_EXPIRE);
                }
            } catch (Exception e) {
                LOGGER.error("添加缓存结果失败！", e);
            } finally {
                if (null != lock) lock.unlock(); // 释放锁
            }
            return saleAttrList;
        }
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrListCheckedBySpuIdAndSkuIdFromCache(String redisKey) {
        try {
            String value = redisUtil.get(redisKey);
            if (null != value) {
                return JSON.parseObject(value, new TypeReference<List<PmsProductSaleAttr>>(){});
            }
        } catch (Exception e) {
            LOGGER.error("查询缓存结果失败！", e);
        }
        return null;
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrListCheckedBySpuIdAndSkuIdFromDB(String spuId, String skuId) {
        return productSaleAttrMapper.selectCheckedBySpuIdAndSkuId(spuId, skuId);
    }

    @Override
    public List<PmsProductImage> getSpuImageList(String spuId) {
        PmsProductImage record = new PmsProductImage();
        record.setProductId(spuId);
        return productImageMapper.select(record);
    }

    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {
        pmsProductInfo.setId(null);
        int inserted = productInfoMapper.insert(pmsProductInfo);

        List<PmsProductSaleAttr> saleAttrList = pmsProductInfo.getSpuSaleAttrList();
        List<PmsProductSaleAttrValue> saleAttrValueList = new ArrayList<>();
        List<PmsProductImage> imageList = pmsProductInfo.getSpuImageList();

        // 遍历spu销售属性列表
        if (null != saleAttrList && !saleAttrList.isEmpty()) {
            for (PmsProductSaleAttr saleAttr : saleAttrList) {
                saleAttr.setId(null);
                saleAttr.setProductId(pmsProductInfo.getId());

                // 遍历spu销售属性列表中的属性值
                List<PmsProductSaleAttrValue> tempList = saleAttr.getSpuSaleAttrValueList();
                if (null != tempList && !tempList.isEmpty()) {
                    for (PmsProductSaleAttrValue saleAttrValue : tempList) {
                        saleAttrValue.setId(null);
                        saleAttrValue.setProductId(pmsProductInfo.getId());
                        saleAttrValueList.add(saleAttrValue);
                    }
                }
            }
            // 批量插入spu销售属性列表
            productSaleAttrMapper.batchInsert(saleAttrList);
            if (!saleAttrValueList.isEmpty()) {
                // 批量插入spu销售属性值列表
                productSaleAttrValueMapper.batchInsert(saleAttrValueList);
            }
        }

        // 遍历spu图片列表
        if(null != imageList && !imageList.isEmpty()) {
            for(PmsProductImage image: imageList) {
                image.setId(null);
                image.setProductId(pmsProductInfo.getId());
            }
            // 批量插入spu图片列表
            productImageMapper.batchInsert(imageList);
        }

        return inserted;
    }
}
