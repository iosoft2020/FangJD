package com.iosoft.mall.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.product.mapper.AttrMapper;
import com.iosoft.mall.product.pojo.Attr;
import com.iosoft.mall.product.service.AttrService;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Override
    public List<Long> selectSearchAttrIds(List<Long> attrIds) {
        return baseMapper.selectSearchAttrIds(attrIds);
    }
}
