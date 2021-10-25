package com.iosoft.mall.product.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.product.pojo.SkuInfo;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface SkuInfoService extends IService<SkuInfo> {

    List<SkuInfo> getSkusBySpuId(Long spuId);
}
