package com.iosoft.mall.product.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.product.mapper.CategoryMapper;
import com.iosoft.mall.product.pojo.Category;
import com.iosoft.mall.product.service.CategoryService;
import com.iosoft.mall.product.vo.Catalog2Vo;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CategoryBrandRelationServiceImpl categoryBrandRelationService;

    @Override
    public List<Category> listWithTree() {
        List<Category> entities = baseMapper.selectList(null);
        List<Category> collect = entities.stream()
                .filter(item->item.getParentCid()==0)
                .map(menu->{
                    menu.setChildren(getChildrens(menu,entities));
                    return menu;
                })
                .sorted((menu1,menu2)->{
                    return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }

	private List<Category> getChildrens(Category categoryEntity, List<Category> entities) {
        List<Category> collect = entities.stream()
                .filter(item -> item.getParentCid() == categoryEntity.getCatId())
                .map(menu -> {
                    menu.setChildren(getChildrens(menu, entities));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }

	@Cacheable(value="catagory", key="'level1Catagory'")
//	@Cacheable(value="catagory", key="#root.method.name")
    @Override
    public List<Category> getLevel1Catagories() {
	    System.err.println("getLevel1Catagories,,,");
//        long start = System.currentTimeMillis();
        List<Category> parent_cid = this.list(new QueryWrapper<Category>().eq("parent_cid", 0));
//        System.out.println("查询一级菜单时间:"+(System.currentTimeMillis()-start));
        return parent_cid;
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCategoryMap() {
//        List<CategoryEntity> categoryEntities = this.list(new QueryWrapper<CategoryEntity>().eq("cat_level", 2));
//
//        List<Catalog2Vo> catalog2Vos = categoryEntities.stream().map(categoryEntity -> {
//            List<CategoryEntity> level3 = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", categoryEntity.getCatId()));
//            List<Catalog2Vo.Catalog3Vo> catalog3Vos = level3.stream().map(cat -> {
//                return new Catalog2Vo.Catalog3Vo(cat.getParentCid().toString(), cat.getCatId().toString(), cat.getName());
//            }).collect(Collectors.toList());
//            Catalog2Vo catalog2Vo = new Catalog2Vo(categoryEntity.getParentCid().toString(), categoryEntity.getCatId().toString(), categoryEntity.getName(), catalog3Vos);
//            return catalog2Vo;
//        }).collect(Collectors.toList());
//        Map<String, List<Catalog2Vo>> catalogMap = new HashMap<>();
//        for (Catalog2Vo catalog2Vo : catalog2Vos) {
//            List<Catalog2Vo> list = catalogMap.getOrDefault(catalog2Vo.getCatalog1Id(), new LinkedList<>());
//            list.add(catalog2Vo);
//            catalogMap.put(catalog2Vo.getCatalog1Id(),list);
//        }
//        return catalogMap;

//        //缓存改写1：使用map作为本地缓存
//        Map<String, List<Catalog2Vo>> catalogMap = (Map<String, List<Catalog2Vo>>) cache.get("catalogMap");
//        if (catalogMap == null) {
//            catalogMap = getCategoriesDb();
//            cache.put("catalogMap",catalogMap);
//        }
//        return catalogMap;

        //缓存改写2：使用redis作为本地缓存
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            Map<String, List<Catalog2Vo>> categoriesDb = getCategoriesDb();
            String toJSONString = JSON.toJSONString(categoriesDb);
            ops.set("catalogJson",toJSONString);
            return categoriesDb;
        }
        Map<String, List<Catalog2Vo>> listMap = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {});
        return listMap;

//        //缓存改写3：加锁解决缓存穿透问题
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String catalogJson = ops.get("catalogJson");
//        if (StringUtils.isEmpty(catalogJson)) {
//            System.out.println("缓存不命中，准备查询数据库。。。");
////            synchronized (this) {
////                String synCatalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
////                if (StringUtils.isEmpty(synCatalogJson)) {
//                    Map<String, List<Catalog2Vo>> categoriesDb= getCategoriesDb();
//                    String toJSONString = JSON.toJSONString(categoriesDb);
//                    ops.set("catalogJson", toJSONString);
//                    return categoriesDb;
////                }else {
////                    Map<String, List<Catalog2Vo>> listMap = JSON.parseObject(synCatalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {});
////                    return listMap;
////                }
////            }
//
//        }
//        System.out.println("缓存命中。。。。");
//        Map<String, List<Catalog2Vo>> listMap = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {});
//        return listMap;
    }


    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJsonDbWithSpringCache() {
        return getCategoriesDb();
    }

    //从数据库中查出三级分类
    private  Map<String, List<Catalog2Vo>> getCategoriesDb() {
            System.out.println("查询了数据库");
            //优化业务逻辑，仅查询一次数据库
            List<Category> categoryEntities = this.list();
            //查出所有一级分类
            List<Category> level1Categories = getCategoryByParentCid(categoryEntities, 0L);
            Map<String, List<Catalog2Vo>> listMap = level1Categories.stream().collect(Collectors.toMap(k->k.getCatId().toString(), v -> {
                //遍历查找出二级分类
                List<Category> level2Categories = getCategoryByParentCid(categoryEntities, v.getCatId());
                List<Catalog2Vo> catalog2Vos=null;
                if (level2Categories!=null){
                    //封装二级分类到vo并且查出其中的三级分类
                    catalog2Vos = level2Categories.stream().map(cat -> {
                        //遍历查出三级分类并封装
                        List<Category> level3Catagories = getCategoryByParentCid(categoryEntities, cat.getCatId());
                        List<Catalog2Vo.Catalog3Vo> catalog3Vos = null;
                        if (level3Catagories != null) {
                            catalog3Vos = level3Catagories.stream()
                                    .map(level3 -> new Catalog2Vo.Catalog3Vo(level3.getParentCid().toString(), level3.getCatId().toString(), level3.getName()))
                                    .collect(Collectors.toList());
                        }
                        Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), cat.getCatId().toString(), cat.getName(), catalog3Vos);
                        return catalog2Vo;
                    }).collect(Collectors.toList());
                }
                return catalog2Vos;
            }));
            return listMap;
    }

    private List<Category> getCategoryByParentCid(List<Category> categoryEntities, long l) {
        List<Category> collect = categoryEntities.stream().filter(cat -> cat.getParentCid() == l).collect(Collectors.toList());
        return collect;
    }

    public Map<String, List<Catalog2Vo>> getCatalogJsonDbWithRedisson() {
        Map<String, List<Catalog2Vo>> categoryMap=null;
        RLock lock = redissonClient.getLock("CatalogJson-Lock");
        lock.lock(10,TimeUnit.SECONDS);
        try {
            Thread.sleep(15000);
            categoryMap = getCategoryMap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
            return categoryMap;
        }
    }

    /**
     * 级联更新所有数据
     * @param category
     */
    @Transactional  //因为涉及到多次修改，因此要开启事务
    @Override
    @CacheEvict(value = {"category"}, allEntries = true)
    public void updateCascade(Category category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category);
        }
    }
}
