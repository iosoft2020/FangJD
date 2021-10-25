package com.iosoft.mall.search.service;

import com.iosoft.mall.search.vo.SearchParam;
import com.iosoft.mall.search.vo.SearchResult;

public interface SearchService {
    SearchResult getSearchResult(SearchParam searchParam);
}
