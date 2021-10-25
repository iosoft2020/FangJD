package com.iosoft.mall.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.product.mapper.SkuInfoMapper;
import com.iosoft.mall.product.pojo.SkuInfo;
import com.iosoft.mall.product.service.SkuInfoService;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Override
    public List<SkuInfo> getSkusBySpuId(Long spuId) {
        List<SkuInfo> skus = this.list(new QueryWrapper<SkuInfo>().eq("spu_id", spuId));
        return skus;
    }
}
