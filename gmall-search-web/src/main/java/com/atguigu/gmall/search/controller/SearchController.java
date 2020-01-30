package com.atguigu.gmall.search.controller;

import com.atguigu.gmall.api.bean.PmsSkuSearchParam;
import com.atguigu.gmall.api.bean.PmsSkuSearchResult;
import com.atguigu.gmall.api.service.PmsSkuSearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class SearchController {
    @Reference
    PmsSkuSearchService searchService;

    @GetMapping("initIndex")
    @ResponseBody
    public String initIndex() {
        try {
            searchService.initIndex();
        } catch (Exception e) {
            return "Failed";
        }
        return "Success";
    }

    @GetMapping("initDocument")
    @ResponseBody
    public String initDocument() {
        try {
            searchService.initDocument();
        } catch (Exception e) {
            return "Failed";
        }
        return "Success";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("list.html")
    public String list(PmsSkuSearchParam searchParam, ModelMap modelMap) throws IOException {
//        searchParam.setCatalog3Id("61");
//        searchParam.setKeyword("Apple");
//        PmsSkuAttrValue attrValue1 = new PmsSkuAttrValue();
//        attrValue1.setAttrId("17");
//        attrValue1.setValueId("150");
//
//        PmsSkuAttrValue attrValue2 = new PmsSkuAttrValue();
//        attrValue2.setAttrId("11");
//        attrValue2.setValueId("50");
//        searchParam.setAttrValueList(new ArrayList<>(Arrays.asList(attrValue1, attrValue2)));

        PmsSkuSearchResult searchResult = searchService.searchDocument(searchParam);
        modelMap.put("skuSearchResult", searchResult);
        if (null != searchParam.getKeyword()) {
            modelMap.put("keyword", searchParam.getKeyword());
        }
        return "list";
    }
}
