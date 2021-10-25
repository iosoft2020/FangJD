package com.iosoft.mall.ware.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.common.to.es.SkuHasStockTo;
import com.iosoft.mall.ware.pojo.WareSku;

/**
 * <p>
 * 商品库存 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface WareSkuService extends IService<WareSku> {

    List<SkuHasStockTo> getSkuHasStocks(List<Long> ids);
}
