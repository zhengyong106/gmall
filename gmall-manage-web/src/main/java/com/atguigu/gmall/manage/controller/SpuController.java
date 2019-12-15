package com.atguigu.gmall.manage.controller;

import com.atguigu.gmall.api.bean.PmsProductImage;
import com.atguigu.gmall.api.bean.PmsProductInfo;
import com.atguigu.gmall.api.bean.PmsProductSaleAttr;
import com.atguigu.gmall.api.service.PmsSpuService;
import com.atguigu.gmall.web.util.FileUploadUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class SpuController {
    @Reference
    PmsSpuService spuService;

    @RequestMapping("spuList")
    public List<PmsProductInfo> spuList(String catalog3Id){
        return spuService.getSpuList(catalog3Id);
    }

    @RequestMapping("spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){
        return spuService.getSpuSaleAttrListBySpuId(spuId);
    }

    @RequestMapping("spuImageList")
    public List<PmsProductImage> spuImageList(String spuId){
        return spuService.getSpuImageList(spuId);
    }

    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam MultipartFile file){
        return FileUploadUtil.upload(file);
    }

    @RequestMapping("saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        return spuService.saveSpuInfo(pmsProductInfo) > 0 ? "SUCCESS": "FIELD";
    }
}
