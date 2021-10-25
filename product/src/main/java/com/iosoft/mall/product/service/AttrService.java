package com.iosoft.mall.product.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.product.pojo.Attr;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface AttrService extends IService<Attr> {

    List<Long> selectSearchAttrIds(List<Long> attrIds);
}
