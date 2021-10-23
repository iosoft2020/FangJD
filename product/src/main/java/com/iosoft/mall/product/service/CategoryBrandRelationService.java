package com.iosoft.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.product.pojo.Category;
import com.iosoft.mall.product.pojo.CategoryBrandRelation;

/**
 * <p>
 * 品牌分类关联 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelation> {

    void updateCategory(Category category);
}
