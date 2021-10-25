package com.iosoft.mall.search.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.iosoft.mall.common.to.es.SkuEsModel;
import com.iosoft.mall.search.config.ElasticSearchConfig;
import com.iosoft.mall.search.constant.EsConstant;
import com.iosoft.mall.search.service.ProductSaveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductSaveServiceimpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean saveProducts2Es(List<SkuEsModel> skuEsModels) throws IOException {

        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String s = JSON.toJSONString(skuEsModel);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        boolean hasFailures = bulkResponse.hasFailures();
        List<String> collect = Arrays.stream(bulkResponse.getItems()).map(item -> item.getId())
                .collect(Collectors.toList());

        log.info("商品上架完成：{}", collect);

        return hasFailures;
    }
}
