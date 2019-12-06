package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gmall.api.bean.*;
import com.atguigu.gmall.manage.ManageServiceApplication;
import com.atguigu.gmall.manage.mapper.*;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageServiceApplication.class)
public class MyTest1 {
    @Autowired
    PmsBaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    PmsBaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    PmsProductInfoMapper productInfoMapper;

    @Autowired
    PmsProductImageMapper productImageMapper;

    @Autowired
    PmsSkuInfoMapper skuInfoMapper;

    @Autowired
    PmsSkuImageMapper skuImageMapper;

    @Autowired
    PmsSkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Test
//    @Transactional
    public void test1(){
        String basePath = "C:/Users/Adminstrator/PycharmProjects/untitled/data5";
        for(File firstPath1: new File(basePath).listFiles()){
            for(File file: firstPath1.listFiles()){
                Map<String, Object> mapObj;
                try {
                    String jsonString = FileUtils.readFileToString(file, "UTF-8");
                    mapObj = JSON.parseObject(jsonString, new TypeReference<Map<String, Object>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                try{
                    task1(mapObj);
                } catch (Exception e) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }

    public void task1(Map<String, Object> mapObj) {
        // SPU 信息表
        PmsProductInfo productInfo = new PmsProductInfo();
        productInfo.setCatalog3Id("61");
        productInfo.setDescription((String) mapObj.get("desc"));
        productInfo.setSpuName((String) mapObj.get("name"));
        productInfoMapper.insert(productInfo);


        JSONArray skuList = (JSONArray) mapObj.get("sku_list");
        for(int i = 0; i < skuList.size(); i++) {
            JSONObject sku = (JSONObject) skuList.get(i);

            // SKU 信息表
            PmsSkuInfo skuInfo = new PmsSkuInfo();
            skuInfo.setPrice(BigDecimal.valueOf(sku.getFloat("price") * 100));
            skuInfo.setCatalog3Id("61");
            skuInfo.setSpuId(productInfo.getId());
            skuInfo.setSkuDefaultImg(null); // 暂时设置为空
            skuInfo.setSkuName(sku.getString("name"));
            skuInfo.setSkuDesc(sku.getString("name"));
            skuInfo.setWeight(null);// 暂时设置为空
            skuInfoMapper.insert(skuInfo);

            List<PmsSkuImage> skuImageList = new ArrayList<>();
            JSONArray img_list = (JSONArray) sku.get("img_list");
            for(int j = 0; j < img_list.size(); j++) {
                // SPU 图片表
                PmsProductImage productImage = new PmsProductImage();
                productImage.setProductId(productInfo.getId());
                productImage.setImgName("图片" + j);
                productImage.setImgUrl(img_list.getString(j));
                productImageMapper.insert(productImage);

                // SKU 图片表
                PmsSkuImage skuImage = new PmsSkuImage();
                skuImage.setImgName(productImage.getImgName());
                skuImage.setImgUrl(productImage.getImgUrl());
                skuImage.setSpuImgId(productImage.getId());
                skuImage.setSkuId(skuInfo.getId());
                skuImage.setIsDefault(j == 0 ? "1" : "0");
                skuImageList.add(skuImage);
            }
            skuImageMapper.batchInsert(skuImageList);

//            List<PmsSkuAttrValue> skuAttrValueList = new ArrayList<>();
//            JSONObject attrObject = (JSONObject) sku.get("attr");
//            for(String key: attrObject.keySet()) {
//                // SKU 平台属性表
//                PmsSkuAttrValue skuAttrValue = new PmsSkuAttrValue();
//                skuAttrValue.setSkuId(skuInfo.getId());
//                skuAttrValue.setAttrId(key); // 暂时先这样设置
//                skuAttrValue.setValueId(attrObject.getString(key)); // 暂时先这样设置
//                skuAttrValueList.add(skuAttrValue);
//            }
//            skuAttrValueMapper.batchInsert(skuAttrValueList);

            // SKU 颜色销售属性
            String color = sku.getString("color");
            PmsSkuSaleAttrValue skuSaleAttrValue = new PmsSkuSaleAttrValue();
            skuSaleAttrValue.setSaleAttrId(null); // 暂时设置为空
            skuSaleAttrValue.setSaleAttrValueId(null); // 暂时设置为空
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            skuSaleAttrValue.setSaleAttrName("颜色");
            skuSaleAttrValue.setSaleAttrValueName(color);
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);

            // SKU 版本销售属性
            String version = sku.getString("version");
            if (null != version) {
                skuSaleAttrValue = new PmsSkuSaleAttrValue();
                skuSaleAttrValue.setSaleAttrId(null); // 暂时设置为空
                skuSaleAttrValue.setSaleAttrValueId(null); // 暂时设置为空
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValue.setSaleAttrName("版本");
                skuSaleAttrValue.setSaleAttrValueName(version);
                skuSaleAttrValueMapper.insert(skuSaleAttrValue);
            }
        }
    }
}
