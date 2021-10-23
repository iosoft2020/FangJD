package com.iosoft.mall.product.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.product.pojo.Category;
import com.iosoft.mall.product.vo.Catalog2Vo;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface CategoryService extends IService<Category> {

    List<Category> listWithTree();

    List<Category> getLevel1Catagories();

    Map<String, List<Catalog2Vo>> getCategoryMap();

    Map<String, List<Catalog2Vo>> getCatalogJsonDbWithSpringCache();

    Map<String, List<Catalog2Vo>> getCatalogJsonDbWithRedisson();

    void updateCascade(Category category);

}
