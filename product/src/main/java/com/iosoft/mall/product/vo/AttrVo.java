package com.iosoft.mall.product.vo;

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
public class AttrVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属分类Id
     */
    private String catelogId;

    /**
     * 所属分类名
     */
    private String catelogName;

    /**
     * 所属分组Id
     */
    private String attrGroupId;

    /**
     * 所属分组名
     */
    private String attrGroupName;

}
