package com.iosoft.mall.ware.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iosoft.mall.ware.pojo.WareSku;

/**
 * <p>
 * 商品库存 Mapper 接口
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface WareSkuMapper extends BaseMapper<WareSku> {

    Integer getTotalStock(@Param("id") Long id);
}
