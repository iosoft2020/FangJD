package com.iosoft.mall.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iosoft.mall.common.utils.R;

@FeignClient("product")
public interface ProductFeignService {
    @RequestMapping("product/attr/info/{attrId}")
    R info(@PathVariable("attrId") Long attrId);
}
