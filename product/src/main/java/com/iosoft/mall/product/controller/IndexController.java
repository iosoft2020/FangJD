package com.iosoft.mall.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iosoft.mall.product.pojo.Category;
import com.iosoft.mall.product.service.CategoryService;
import com.iosoft.mall.product.vo.Catalog2Vo;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Controller
@RequestMapping
public class IndexController {



    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "index.html"})
    public String getIndex(Model model) {
        //获取所有的一级分类
        List<Category> catagories = categoryService.getLevel1Catagories();
        model.addAttribute("catagories", catagories);
        return "index";
    }


    @GetMapping("index/json/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCategoryMap() {
//        return categoryService.getCatalogJsonDbWithSpringCache();
        return categoryService.getCategoryMap();
    }
}
