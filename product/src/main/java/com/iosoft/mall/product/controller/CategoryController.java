package com.iosoft.mall.product.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iosoft.mall.product.pojo.Category;
import com.iosoft.mall.product.service.CategoryService;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类并将查到分类通过树状结构组装起来
     */
    @RequestMapping("/list/tree")
    public List<Category> list(){
        List<Category> categoryEntities = categoryService.listWithTree();

        return categoryEntities;
    }
}

