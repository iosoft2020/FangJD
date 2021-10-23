package com.iosoft.mall.product.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.product.pojo.Brand;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface BrandService extends IService<Brand> {

    List<Brand> list();

}
