package com.iosoft.mall.product.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.product.service.SpuInfoService;

/**
 * <p>
 * spu信息 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@RestController
@RequestMapping("/product/spuInfo")
public class SpuInfoController {


    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 商品上架
     * @return
     */
    @PostMapping("/{spuId}/up")
    public R upSpuForSearch(@PathVariable("spuId")Long spuId) {
        spuInfoService.up(spuId);
        return R.ok();
    }
}

