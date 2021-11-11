package com.iosoft.mall.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.product.pojo.AttrGroup;
import com.iosoft.mall.product.service.AttrGroupService;
import com.iosoft.mall.product.service.CategoryService;
import com.iosoft.mall.product.vo.AttrGroupVo;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Controller
@RequestMapping("/product/attrGroup")
public class AttrGroupController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrGroupService attrGroupService;

    @GetMapping("/init")
    public String init(Model model) {

        model.addAttribute("categorys", categoryService.listWithTree());
        return "AttrGroup/list";
    }

    @GetMapping("/list/{categoryId}")
    @ResponseBody
    public List<AttrGroup> list(@PathVariable int categoryId) {

        return attrGroupService.list(new QueryWrapper<AttrGroup>().eq("catelog_id", categoryId));

    }

    @PostMapping("/save")
    @ResponseBody
    public R save(AttrGroupVo catalogVo) {
        AttrGroup attrGroup = new AttrGroup();
        attrGroup.setAttrGroupName(catalogVo.getAttrGroupName());
        attrGroup.setSort(catalogVo.getSort());
        attrGroup.setDescript(catalogVo.getDescript());
        attrGroup.setIcon(catalogVo.getIcon());
        attrGroup.setCatelogId(catalogVo.getCatelogId());
        attrGroupService.save(attrGroup);
        return R.ok();
    }

}
