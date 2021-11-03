package com.iosoft.mall.product.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iosoft.mall.product.pojo.SpuInfo;

/**
 * <p>
 * spu信息 Mapper 接口
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface SpuInfoMapper extends BaseMapper<SpuInfo> {

    void upSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);

    void downSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
