package com.iosoft.mall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.product.form.AttrForm;
import com.iosoft.mall.product.pojo.Attr;
import com.iosoft.mall.product.pojo.AttrAttrgroupRelation;
import com.iosoft.mall.product.service.AttrAttrgroupRelationService;
import com.iosoft.mall.product.service.AttrService;
import com.iosoft.mall.product.vo.AttrVo;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Controller
@RequestMapping("/product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @GetMapping("/init")
    public String init(@ModelAttribute AttrVo attrVo, Model model) {

        model.addAttribute("attrs",
                attrService.list(new QueryWrapper<Attr>().eq("catelog_id", Long.parseLong(attrVo.getCatelogId()))));
        return "Attr/list";
    }

    @DeleteMapping("/delete/{attrId}")
    public String delete(@PathVariable int attrId) {
        attrService.removeById(attrId);
        return "Attr/list";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(AttrForm attrForm) {
        Attr attr = new Attr();
        attr.setAttrName(attrForm.getAttrName());
        attr.setSearchType(Integer.parseInt(attrForm.getSearchType()));
        attr.setIcon(attrForm.getIcon());
        attr.setCatelogId(Long.parseLong(attrForm.getCatelogId()));
        attrService.save(attr);

        AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
        attrAttrgroupRelation.setAttrId(attr.getAttrId());
        attrAttrgroupRelation.setAttrGroupId(attrForm.getAttrGroupId());
        attrAttrgroupRelationService.save(attrAttrgroupRelation);
        return R.ok();
    }
}
