package com.iosoft.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.product.pojo.SpuInfo;

/**
 * <p>
 * spu信息 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface SpuInfoService extends IService<SpuInfo> {

    void up(Long spuId);
}
