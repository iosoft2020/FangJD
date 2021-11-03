package com.iosoft.mall.product.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iosoft.mall.product.form.BrandForm;
import com.iosoft.mall.product.pojo.Brand;
import com.iosoft.mall.product.service.BrandService;
import com.iosoft.mall.product.valid.AddGroup;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Controller
@RequestMapping("/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("brands", brandService.list());
        return "Brand/list";
    }

    @GetMapping("/toAdd")
    public String toAdd() {
        return "Brand/add";
    }

    @PostMapping
    public String add(@Validated(AddGroup.class) BrandForm brandForm) {

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandForm, brand);
        brandService.save(brand);
        return "redirect:/product/brand/list";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        brandService.delete(id);
        return "redirect:/product/brand/list";
    }

}
