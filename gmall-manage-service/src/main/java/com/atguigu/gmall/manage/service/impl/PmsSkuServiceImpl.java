package com.atguigu.gmall.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gmall.api.bean.*;
import com.atguigu.gmall.api.service.PmsSkuService;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuImageMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.util.RedisUtil;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class PmsSkuServiceImpl implements PmsSkuService {

    @Autowired
    PmsSkuInfoMapper skuInfoMapper;

    @Autowired
    PmsSkuImageMapper skuImageMapper;

    @Autowired
    PmsSkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    private final Logger LOGGER = LoggerFactory.getLogger(PmsSkuServiceImpl.class);
    private final int REDIS_KEY_EXPIRE = 30 * 60;
    private final int REDIS_NULL_KEY_EXPIRE = 10;

    @Override
    public PmsSkuInfo getSkuInfo(String skuId) {
        String redisKey = "sku:" + skuId + ":info";
        String lockName = "sku:" + skuId + ":info:lock";

        // 先尝试查找缓存
        PmsSkuInfo skuInfo = getSkuInfoFromCache(redisKey);

        if (null != skuInfo) {
            return skuInfo;
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
            skuInfo = getSkuInfoFromCache(redisKey);
            if (null != skuInfo) {
                if (null != lock) lock.unlock(); // 释放锁
                return skuInfo;
            }

            // 如果查询不到结果，则通过数据库查询
            try {
                skuInfo = getSkuInfoFromDB(skuId);
            } catch (Exception e){
                if (null != lock) lock.unlock(); // 释放锁
                throw e;
            }

            // 将查询结果添加到缓存
            try {
                // 需要缓存查询为空的数据，防止缓存穿透
                if (null == skuInfo) {
                    redisUtil.set(redisKey, "", REDIS_NULL_KEY_EXPIRE);
                } else {
                    redisUtil.set(redisKey, JSON.toJSONString(skuInfo), REDIS_KEY_EXPIRE);
                }
            } catch (Exception e) {
                LOGGER.error("添加缓存结果失败！", e);
            } finally {
                if (null != lock) lock.unlock(); // 释放锁
            }
            return skuInfo;
        }
    }

    @Override
    public PmsSkuInfo getSkuInfoFromCache(String redisKey) {
        try {
            String value = redisUtil.get(redisKey);
            if (null != value) {
                return JSON.parseObject(value, PmsSkuInfo.class);
            }
        } catch (Exception e) {
            LOGGER.error("查询缓存结果失败！", e);
        }
        return null;
    }

    @Override
    public PmsSkuInfo getSkuInfoFromDB(String skuId) {
        // 获取sku信息
        PmsSkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        // 获取sku图片
        PmsSkuImage record = new PmsSkuImage();
        record.setSkuId(skuId);
        skuInfo.setSkuImageList(skuImageMapper.select(record));
        return skuInfo;
    }

    @Override
    public Map<String, String> getSkuSaleAttrValueHashBySpuId(String spuId) {
        String redisKey = "spu:" + spuId + ":saleAttrValueHash";
        String lockName = "spu:" + spuId + ":saleAttrValueHash:lock";

        // 先尝试查找缓存
        Map<String, String> map = getSkuSaleAttrValueHashBySpuIdFromCache(redisKey);

        if (null != map) {
            return map;
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
            map = getSkuSaleAttrValueHashBySpuIdFromCache(redisKey);
            if (null != map) {
                if (null != lock) lock.unlock(); // 释放锁
                return map;
            }

            // 如果查询不到结果，则通过数据库查询
            try {
                map = getSkuSaleAttrValueHashBySpuIdFromDB(spuId);
            } catch (Exception e){
                if (null != lock) lock.unlock(); // 释放锁
                throw e;
            }

            // 将查询结果添加到缓存
            try {
                // 需要缓存查询为空的数据，防止缓存穿透
                if (null == map) {
                    redisUtil.set(redisKey, "", REDIS_NULL_KEY_EXPIRE);
                } else {
                    redisUtil.set(redisKey, JSON.toJSONString(map), REDIS_KEY_EXPIRE);
                }
            } catch (Exception e) {
                LOGGER.error("添加缓存结果失败！", e);
            } finally {
                if (null != lock) lock.unlock(); // 释放锁
            }
            return map;
        }
    }

    @Override
    public Map<String, String> getSkuSaleAttrValueHashBySpuIdFromCache(String redisKey) {
        try {
            String value = redisUtil.get(redisKey);
            if (null != value) {
                return JSON.parseObject(value, new TypeReference<Map<String, String>>(){});
            }
        } catch (Exception e) {
            LOGGER.error("查询缓存结果失败！", e);
        }
        return null;
    }

    @Override
    public Map<String, String> getSkuSaleAttrValueHashBySpuIdFromDB(String spuId) {
        // sku销售属性值列表
        PmsSkuSaleAttrValue record = new PmsSkuSaleAttrValue();
        record.setSpuId(spuId);
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuSaleAttrValueMapper.select(record);

        // 生成hash映射skuId和sku销售属性值列表
        Map<String, List<PmsSkuSaleAttrValue>> skuIdMap = new HashMap<>();
        for(PmsSkuSaleAttrValue saleAttrValue: skuSaleAttrValueList) {
            String k = saleAttrValue.getSkuId();
            if (skuIdMap.containsKey(k)) {
                skuIdMap.get(k).add(saleAttrValue);
            } else {
                skuIdMap.put(k, new ArrayList<>(Collections.singletonList(saleAttrValue)));
            }
        }

        // 生成hash映射销售属性值字符串和skuId
        Map<String, String> saleAttrValueStringMap = new HashMap<>();
        for(Map.Entry<String, List<PmsSkuSaleAttrValue>> entry: skuIdMap.entrySet()) {
            StringBuilder k = new StringBuilder();
            String v = entry.getKey();
            for(PmsSkuSaleAttrValue saleAttrValue: entry.getValue()) {
                k.append(saleAttrValue.getSaleAttrValueId()).append("|");
            }
            saleAttrValueStringMap.put(k.deleteCharAt(k.length()-1).toString(), v);
        }
        return saleAttrValueStringMap;
    }

    @Override
    public int saveSkuInfo(PmsSkuInfo skuInfo) {
        // 保存sku信息
        skuInfo.setId(null);
        int inserted = skuInfoMapper.insert(skuInfo);

        // 保存sku图片列表
        List<PmsSkuImage> imageList = skuInfo.getSkuImageList();
        for (PmsSkuImage image: imageList){
            image.setId(null);
            image.setSkuId(skuInfo.getId());
        }
        skuImageMapper.batchInsert(imageList);

        // 保存sku平台属性值列表
        List<PmsSkuAttrValue> attrValueList = skuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue attrValue: attrValueList){
            attrValue.setId(null);
            attrValue.setSkuId(skuInfo.getId());
        }
        skuAttrValueMapper.batchInsert(attrValueList);

        // 保存sku销售属性值列表
        List<PmsSkuSaleAttrValue> saleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue saleAttrValue: saleAttrValueList){
            saleAttrValue.setId(null);
            saleAttrValue.setSpuId(skuInfo.getSpuId());
            saleAttrValue.setSkuId(skuInfo.getId());
        }
        skuSaleAttrValueMapper.batchInsert(saleAttrValueList);
        return inserted;
    }
}
