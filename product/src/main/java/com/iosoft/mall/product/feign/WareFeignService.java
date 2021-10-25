package com.iosoft.mall.product.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iosoft.mall.common.to.es.SkuHasStockTo;
import com.iosoft.mall.common.utils.R;

@FeignClient("ware")
public interface WareFeignService {

    @RequestMapping("/ware/waresku/getSkuHasStocks")
    R<List<SkuHasStockTo>> getSkuHasStocks(@RequestBody List<Long> ids);
}
