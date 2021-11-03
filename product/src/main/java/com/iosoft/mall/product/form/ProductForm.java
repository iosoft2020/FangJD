package com.iosoft.mall.product.form;


import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.iosoft.mall.product.valid.AddGroup;
import com.iosoft.mall.product.valid.ListValue;
import com.iosoft.mall.product.valid.UpdateGroup;

import lombok.Data;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Data
public class ProductForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotEmpty(message = "品牌id不能为空", groups = UpdateGroup.class)
    private Long brandId;

    /**
     * 品牌名
     */
    @NotEmpty(message = "品牌名不能为空", groups = { AddGroup.class, UpdateGroup.class })
    private String name;

    /**
     * 品牌logo地址
     */
    private String logo;

    /**
     * 介绍
     */
    private String descript;

    /**
     * 显示状态[0-不显示；1-显示]
     */
    @ListValue
    private String showStatus;

}
