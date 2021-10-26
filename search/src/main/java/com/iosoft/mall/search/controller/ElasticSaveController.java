package com.iosoft.mall.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iosoft.mall.common.exception.BizCodeEnum;
import com.iosoft.mall.common.to.es.SkuEsModel;
import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.search.service.ProductSaveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R saveProducts2Es(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean hasFailures = false;
        try {
            hasFailures = productSaveService.saveProducts2Es(skuEsModels);
        } catch (Exception e) {
            log.error("商品上架错误:{}", e);
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if (!hasFailures) {
            return R.ok();
        } else {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }

    }

}
