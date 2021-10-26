package com.iosoft.mall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iosoft.mall.product.form.ProductForm;
import com.iosoft.mall.product.service.SpuInfoService;
import com.iosoft.mall.product.valid.AddGroup;

/**
 * <p>
 * spu信息 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 商品上架
     * @return
     */
    @GetMapping("/{spuId}/up")
    public String upSpuForSearch(@PathVariable("spuId") Long spuId, Model model) {
        spuInfoService.up(spuId);
        model.addAttribute("products", spuInfoService.list());
        return "Product/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("products", spuInfoService.list());
        return "Product/list";
    }

    @GetMapping("/toAdd")
    public String toAdd() {
        return "Product/add";
    }

    @PostMapping
    public String add(@Validated(AddGroup.class) ProductForm spu) {
        return "redirect:/product/spuInfo/list";
    }
}
