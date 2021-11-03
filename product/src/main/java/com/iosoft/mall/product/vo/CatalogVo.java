package com.iosoft.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogVo {

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 分类id
     */
    private String catelogId;

    private String brandName;

    private String catelogName;
}
