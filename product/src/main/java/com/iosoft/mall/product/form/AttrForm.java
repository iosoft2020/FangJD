package com.iosoft.mall.product.form;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Data
public class AttrForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private String searchType;

    /**
     * 属性图标
     */
    private String icon;

    /**
     * 所属分类
     */
    private String catelogId;

}
