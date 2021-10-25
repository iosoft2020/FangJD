package com.iosoft.mall.ware.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iosoft.mall.common.to.es.SkuHasStockTo;
import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.ware.service.WareSkuService;

/**
 * <p>
 * 商品库存 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@RestController
@RequestMapping("/ware/waresku")
public class WareSkuController {

    @Autowired
    private WareSkuService wareSkuService;

    @RequestMapping("/getSkuHasStocks")
    public R<List<SkuHasStockTo>> getSkuHasStocks(@RequestBody List<Long> ids) {
        R<List<SkuHasStockTo>> ok = R.ok();
        ok.setData(wareSkuService.getSkuHasStocks(ids));
        return ok;
    }
}
