package com.atguigu.gmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.api.bean.*;
import com.atguigu.gmall.api.service.PmsBaseService;
import com.atguigu.gmall.api.service.PmsSkuSearchService;
import com.atguigu.gmall.search.mapper.PmsSkuSearchMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class PmsSkuSearchServiceImpl implements PmsSkuSearchService {
    @Resource
    RestHighLevelClient highLevelClient;

    @Autowired
    PmsSkuSearchMapper skuSearchMapper;

    @Reference
    PmsBaseService baseService;

    @Override
    public boolean initIndex() throws IOException {
        CreateIndexResponse response = highLevelClient.indices().create(
                new CreateIndexRequest("gmall").
                        settings(Settings.builder()
                                .put("index.number_of_shards", 5)
                                .put("index.number_of_replicas", 0)).
                        mapping("{\"properties\":{\"id\":{\"type\":\"long\",\"index\":false},\"skuName\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"},\"skuDesc\":{\"type\":\"text\",\"analyzer\":\"ik_smart\"},\"catalog3Id\":{\"type\":\"keyword\"},\"price\":{\"type\":\"long\"},\"skuDefaultImg\":{\"type\":\"text\",\"index\":false},\"hotScore\":{\"type\":\"long\"},\"spuId\":{\"type\":\"keyword\",\"index\":false},\"attrValueList\":{\"properties\":{\"id\":{\"type\":\"keyword\",\"index\":false},\"attrId\":{\"type\":\"keyword\"},\"valueId\":{\"type\":\"keyword\"},\"skuId\":{\"type\":\"keyword\",\"index\":false}}}}}", XContentType.JSON),
                RequestOptions.DEFAULT);
        return true;
    }

    @Override
    public boolean initDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        for (PmsSkuSearch pmsSkuSearch : skuSearchMapper.selectAll()) {
            bulkRequest.add(new IndexRequest("gmall", "_doc").source(JSON.toJSONString(pmsSkuSearch), XContentType.JSON));
        }
        BulkResponse response = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return true;
    }

    @Override
    public PmsSkuSearchResult searchDocument(PmsSkuSearchParam searchParam) throws IOException {
        // 构造查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (null != searchParam.getKeyword()) {
            queryBuilder
                    .must(QueryBuilders
                            .matchQuery("skuName", searchParam.getKeyword()));
        }
        if (null != searchParam.getCatalog3Id()) {
            queryBuilder
                    .filter(QueryBuilders
                            .termQuery("catalog3Id", searchParam.getCatalog3Id()));
        }
        if (null != searchParam.getAttrValueList()) {
            for (PmsSkuAttrValue pmsSkuAttrValue : searchParam.getAttrValueList()) {
                BoolQueryBuilder innerQueryBuilder = QueryBuilders.boolQuery();
                if (null != pmsSkuAttrValue.getAttrId()) {
                    innerQueryBuilder
                            .must(QueryBuilders
                                    .termQuery("attrValueList.attrId", pmsSkuAttrValue.getAttrId()));
                }
                if (null != pmsSkuAttrValue.getValueId()) {
                    innerQueryBuilder
                            .must(QueryBuilders
                                    .termQuery("attrValueList.valueId", pmsSkuAttrValue.getValueId()));
                }
                queryBuilder.filter(innerQueryBuilder);
            }
        }

        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style=\"color: red\">");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");

        // 构建搜索请求
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(queryBuilder)
                .from(searchParam.getFrom())
                .size(searchParam.getSize())
                .highlighter(highlightBuilder)
                .aggregation(AggregationBuilders
                        .terms("sku")
                        .field("attrValueList.valueId").size(Integer.MAX_VALUE));
        SearchRequest searchRequest = new SearchRequest("gmall").source(builder);

        // 处理搜索结果集
        SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<PmsSkuSearch> hits = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            PmsSkuSearch skuSearch = JSON.parseObject(hit.getSourceAsString(), PmsSkuSearch.class);

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (null != highlightFields && null != highlightFields.get("skuName")) {
                String highlightSkuName = highlightFields.get("skuName").getFragments()[0].toString();
                skuSearch.setSkuName(highlightSkuName);
            }
            hits.add(skuSearch);
        }

        // 处理聚合搜索结果集
        ParsedStringTerms parsedStringTerms = response.getAggregations().get("sku");
        List<PmsBaseAttrInfo> attrInfoList = null;
        if (null != parsedStringTerms && !parsedStringTerms.getBuckets().isEmpty()) {
            Set<String> attrValueIds  = new HashSet<>();
            for (Terms.Bucket bucket : parsedStringTerms.getBuckets()) {
                attrValueIds.add(bucket.getKeyAsString());
            }
            attrInfoList = baseService.getAttrInfoListByAttrValueIds(attrValueIds);
        }

        // 封装搜索结果集
        PmsSkuSearchResult searchResults = new PmsSkuSearchResult();
        searchResults.setHits(hits);
        searchResults.setAttrInfoList(attrInfoList);
        searchResults.setTotalHits(response.getHits().totalHits);
        return searchResults;
    }
}
