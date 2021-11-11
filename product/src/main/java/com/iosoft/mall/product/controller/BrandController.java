package com.iosoft.mall.product.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iosoft.mall.product.form.BrandForm;
import com.iosoft.mall.product.pojo.Brand;
import com.iosoft.mall.product.service.BrandService;
import com.iosoft.mall.product.valid.AddGroup;
import com.iosoft.mall.product.valid.UpdateGroup;

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

    @GetMapping
    public String toAdd(Model model) {
        Map<String, String> errors = (Map<String, String>) model.getAttribute("errors");
        BrandForm brandForm = (BrandForm) model.getAttribute("brandForm");
        if (!CollectionUtils.isEmpty(errors)) {
            model.addAttribute("errors", errors);
        }
        model.addAttribute("brandForm", new BrandForm());
        if (!Objects.isNull(brandForm)) {
            model.addAttribute("brandForm", brandForm);
        }

        return "Brand/add";
    }

    @GetMapping("/{brandId}")
    public String toEdit(@PathVariable int brandId, Model model) {

        Map<String, String> errors = (Map<String, String>) model.getAttribute("errors");
        BrandForm brandForm = (BrandForm) model.getAttribute("brandForm");
        if (!CollectionUtils.isEmpty(errors)) {
            model.addAttribute("errors", errors);
        }
        if (!Objects.isNull(brandForm)) {
            model.addAttribute("brandForm", brandForm);
        } else {
            brandForm = new BrandForm();
            Brand brand = brandService.getById(brandId);
            BeanUtils.copyProperties(brandService.getById(brandId), brandForm);
            brandForm.setBrandId(String.valueOf(brand.getBrandId()));
            if (!Objects.isNull(brand.getShowStatus())) {
                brandForm.setShowStatus(String.valueOf(brand.getShowStatus()));
            }
            model.addAttribute("brandForm", brandForm);
        }
        return "Brand/add";
    }

    @PostMapping
    public String add(@Validated(AddGroup.class) BrandForm brandForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                String message = fieldError.getDefaultMessage();
                String field = fieldError.getField();
                errors.put(field, message);
            });
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://iosoftmall.com/product/brand";
        }

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandForm, brand);
        brand.setShowStatus(0);
        if ("on".equals(brandForm.getShowStatus())) {
            brand.setShowStatus(1);
        }
        brandService.save(brand);
        return "redirect:http://iosoftmall.com/product/brand/list";
    }

    @PutMapping
    public String update(@Validated(UpdateGroup.class) BrandForm brandForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                String message = fieldError.getDefaultMessage();
                String field = fieldError.getField();
                errors.put(field, message);
            });
            redirectAttributes.addFlashAttribute("brandForm", brandForm);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://iosoftmall.com/product/brand/" + brandForm.getBrandId();
        }
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandForm, brand);
        brand.setBrandId(Long.parseLong(brandForm.getBrandId()));
        brand.setShowStatus(0);
        if ("on".equals(brandForm.getShowStatus())) {
            brand.setShowStatus(1);
        }
        brandService.updateById(brand);
        return "redirect:http://iosoftmall.com/product/brand/list";
    }

    @DeleteMapping("/{brandId}")
    public String deleteEmployee(@PathVariable int brandId) {
        brandService.delete(brandId);
        return "redirect:http://iosoftmall.com/product/brand/list";
    }

}
