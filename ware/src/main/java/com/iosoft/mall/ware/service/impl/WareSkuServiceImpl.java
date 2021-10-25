package com.iosoft.mall.ware.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.common.to.es.SkuHasStockTo;
import com.iosoft.mall.ware.mapper.WareSkuMapper;
import com.iosoft.mall.ware.pojo.WareSku;
import com.iosoft.mall.ware.service.WareSkuService;

/**
 * <p>
 * 商品库存 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {

    @Override
    public List<SkuHasStockTo> getSkuHasStocks(List<Long> ids) {
        List<SkuHasStockTo> skuHasStockTos = ids.stream().map(id -> {
            SkuHasStockTo skuHasStockTo = new SkuHasStockTo();
            skuHasStockTo.setSkuId(id);
            Integer count = baseMapper.getTotalStock(id);
            skuHasStockTo.setHasStock(count == null ? false : count > 0);
            return skuHasStockTo;
        }).collect(Collectors.toList());
        return skuHasStockTos;
    }
}
