package com.iosoft.mall.search.service;

import java.io.IOException;
import java.util.List;

import com.iosoft.mall.common.to.es.SkuEsModel;


public interface ProductSaveService {

    boolean saveProducts2Es(List<SkuEsModel> skuEsModels) throws IOException;
}
