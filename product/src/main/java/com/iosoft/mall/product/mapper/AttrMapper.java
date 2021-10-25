package com.iosoft.mall.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iosoft.mall.product.pojo.Attr;

/**
 * <p>
 * 商品属性 Mapper 接口
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface AttrMapper extends BaseMapper<Attr> {

    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
