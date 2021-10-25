package com.iosoft.mall.product.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.common.constant.ProductConstant;
import com.iosoft.mall.common.to.es.SkuEsModel;
import com.iosoft.mall.common.to.es.SkuHasStockTo;
import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.product.feign.SearchFeignService;
import com.iosoft.mall.product.feign.WareFeignService;
import com.iosoft.mall.product.mapper.SpuInfoMapper;
import com.iosoft.mall.product.pojo.Brand;
import com.iosoft.mall.product.pojo.Category;
import com.iosoft.mall.product.pojo.ProductAttrValue;
import com.iosoft.mall.product.pojo.SkuInfo;
import com.iosoft.mall.product.pojo.SpuInfo;
import com.iosoft.mall.product.service.AttrService;
import com.iosoft.mall.product.service.BrandService;
import com.iosoft.mall.product.service.CategoryService;
import com.iosoft.mall.product.service.ProductAttrValueService;
import com.iosoft.mall.product.service.SkuInfoService;
import com.iosoft.mall.product.service.SpuInfoService;

/**
 * <p>
 * spu信息 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public void up(Long spuId) {

        //1、查出当前spuId对应的所有sku信息,品牌的名字
        List<SkuInfo> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);
        //TODO 4、查出当前sku的所有可以被用来检索的规格属性
        List<ProductAttrValue> productAttrValueEntities = productAttrValueService
                .list(new QueryWrapper<ProductAttrValue>().eq("spu_id", spuId));
        List<Long> attrIds = productAttrValueEntities.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        List<Long> searchIds = attrService.selectSearchAttrIds(attrIds);
        Set<Long> ids = new HashSet<>(searchIds);
        List<SkuEsModel.Attr> searchAttrs = productAttrValueEntities.stream().filter(entity -> {
            return ids.contains(entity.getAttrId());
        }).map(entity -> {
            SkuEsModel.Attr attr = new SkuEsModel.Attr();
            BeanUtils.copyProperties(entity, attr);
            return attr;
        }).collect(Collectors.toList());

        //TODO 1、发送远程调用，库存系统查询是否有库存
        Map<Long, Boolean> stockMap = null;
        try {
            List<Long> longList = skuInfoEntities.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());
            R<List<SkuHasStockTo>> skuHasStocks = wareFeignService.getSkuHasStocks(longList);
            stockMap = skuHasStocks.getData().stream()
                    .collect(Collectors.toMap(SkuHasStockTo::getSkuId, SkuHasStockTo::getHasStock));
        } catch (Exception e) {
            log.error("远程调用库存服务失败,原因{}", e);
        }

        //2、封装每个sku的信息
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> skuEsModels = skuInfoEntities.stream().map(sku -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skuEsModel);
            skuEsModel.setSkuPrice(sku.getPrice());
            skuEsModel.setSkuImg(sku.getSkuDefaultImg());
            //TODO 2、热度评分。0
            skuEsModel.setHotScore(0L);
            //TODO 3、查询品牌和分类的名字信息
            Brand brandEntity = brandService.getById(sku.getBrandId());
            skuEsModel.setBrandName(brandEntity.getName());
            skuEsModel.setBrandImg(brandEntity.getLogo());
            Category categoryEntity = categoryService.getById(sku.getCatalogId());
            skuEsModel.setCatalogName(categoryEntity.getName());
            //设置可搜索属性
            skuEsModel.setAttrs(searchAttrs);
            //设置是否有库存
            skuEsModel.setHasStock(finalStockMap == null ? false : finalStockMap.get(sku.getSkuId()));
            return skuEsModel;
        }).collect(Collectors.toList());

        //TODO 5、将数据发给es进行保存：gulimall-search
        R r = searchFeignService.saveProducts2Es(skuEsModels);
        if (r.getCode() == 0) {
            this.baseMapper.upSpuStatus(spuId, ProductConstant.ProductStatusEnum.SPU_UP.getCode());
        } else {
            log.error("商品远程es保存失败");
        }
    }
}
