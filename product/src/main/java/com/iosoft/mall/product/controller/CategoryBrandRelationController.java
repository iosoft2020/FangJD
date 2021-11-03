package com.iosoft.mall.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iosoft.mall.product.pojo.CategoryBrandRelation;
import com.iosoft.mall.product.service.CategoryBrandRelationService;
import com.iosoft.mall.product.vo.CatalogVo;

/**
 * <p>
 * 品牌分类关联 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@RestController
@RequestMapping("/product/categoryBrandRelation")
public class CategoryBrandRelationController {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("/{id}")
    public List<CategoryBrandRelation> getCategoryRelations(@PathVariable int id) {
        return categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelation>().eq("brand_id", id));
    }

    @GetMapping("/delete/{brandId}/{catelogId}")
    public List<CategoryBrandRelation> deleteCategoryRelations(@PathVariable int brandId, @PathVariable int catelogId) {
        categoryBrandRelationService
                .remove(new QueryWrapper<CategoryBrandRelation>().eq("brand_id", brandId)
                        .and(wrapper -> wrapper.eq("catelog_id", catelogId)));
        return categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelation>().eq("brand_id", brandId));
    }

    @GetMapping("/create")
    public List<CategoryBrandRelation> create(CatalogVo catalogVo) {
        CategoryBrandRelation categoryBrandRelation = new CategoryBrandRelation();
        categoryBrandRelation.setBrandId(Long.parseLong(catalogVo.getBrandId()));
        categoryBrandRelation.setCatelogId(Long.parseLong(catalogVo.getCatelogId()));
        categoryBrandRelation.setBrandName(catalogVo.getBrandName());
        categoryBrandRelation.setCatelogName(catalogVo.getCatelogName());
        categoryBrandRelationService.save(categoryBrandRelation);
        return categoryBrandRelationService
                .list(new QueryWrapper<CategoryBrandRelation>().eq("brand_id", Long.parseLong(catalogVo.getBrandId())));
    }
}
