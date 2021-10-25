package com.iosoft.mall.product.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.iosoft.mall.common.to.es.SkuEsModel;
import com.iosoft.mall.common.utils.R;

@FeignClient("search")
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    R saveProducts2Es(@RequestBody List<SkuEsModel> skuEsModels);

}
