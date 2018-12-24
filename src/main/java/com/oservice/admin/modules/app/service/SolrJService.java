package com.oservice.admin.modules.app.service;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.SearchResult;

/**
 * @program: oservice
 * @description: 搜索服务
 * @author: YJmiss
 * @create: 2018-12-04 09:43
 **/
public interface SolrJService {
    public Result addIndex();

    public SearchResult findItemsByKeywords(String keyword, Integer page, Integer rows, Integer sortData) throws Exception;

    public Boolean addIndexById(Long id);

    public void deleteIndexById(Long id);
}
