package com.iosoft.mall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iosoft.mall.product.form.AttrForm;
import com.iosoft.mall.product.pojo.Attr;
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

    @GetMapping("/init")
    public String init(@ModelAttribute AttrVo attrVo) {
        return "Attr/list";
    }

    @PostMapping("/save")
    @ResponseBody
    public void save(AttrForm attrForm) {
        Attr attr = new Attr();
        attr.setAttrName(attrForm.getAttrName());
        attr.setSearchType(Integer.parseInt(attrForm.getSearchType()));
        attr.setIcon(attrForm.getIcon());
        attr.setCatelogId(Long.parseLong(attrForm.getCatelogId()));
        attrService.save(attr);
    }
}
