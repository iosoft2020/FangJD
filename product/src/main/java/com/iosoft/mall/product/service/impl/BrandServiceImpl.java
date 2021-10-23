package com.iosoft.mall.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.product.mapper.BrandMapper;
import com.iosoft.mall.product.pojo.Brand;
import com.iosoft.mall.product.service.BrandService;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Override
    public List<Brand> list() {

        return baseMapper.selectList(null);
    }
}
