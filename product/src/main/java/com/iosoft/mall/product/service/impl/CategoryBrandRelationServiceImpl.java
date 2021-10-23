package com.iosoft.mall.product.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.product.mapper.CategoryBrandRelationMapper;
import com.iosoft.mall.product.pojo.Category;
import com.iosoft.mall.product.pojo.CategoryBrandRelation;
import com.iosoft.mall.product.service.CategoryBrandRelationService;

/**
 * <p>
 * 品牌分类关联 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation> implements CategoryBrandRelationService {
    @Override
    public void updateCategory(Category category) {
        CategoryBrandRelation categoryBrandRelationEntity = new CategoryBrandRelation();
        categoryBrandRelationEntity.setCatelogName(category.getName());
        categoryBrandRelationEntity.setCatelogId(category.getCatId());
        this.update(
                categoryBrandRelationEntity,
                new UpdateWrapper<CategoryBrandRelation>()
                        .eq("catelog_id",category.getCatId()));
    }

}
