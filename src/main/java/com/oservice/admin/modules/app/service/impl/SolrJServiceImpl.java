package com.oservice.admin.modules.app.service.impl;

import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.SearchResult;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.app.service.SolrJService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: oservice
 * @description: 搜索服务实现
 * @author: YJmiss
 * @create: 2018-12-04 09:56
 **/
@Service("solrServer")
public class SolrJServiceImpl implements SolrJService {
    @Autowired
    private SolrJDao solrJDao;

    @Override
    public Result addIndex() {
        return solrJDao.addIndex();
    }

    @Override
    public SearchResult findItemsByKeywords(String keyword, Integer page, Integer rows) throws Exception {
        //1.构建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //2.添加查询条件
        //2.1)设置查询条件
        query.setQuery(keyword);
        //2.2)设置查询的默认字段列表
        query.set("df", ConfigConstant.DEFAULT_FIELD_LIST);
        //2.3)设置分页查询
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        //3.设置高亮
        query.setHighlight(true);                                 //启用高亮
        query.setHighlightSimplePre("<span style='color:red'>"); //设置高亮前缀
        query.setHighlightSimplePost("</span>");                 //设置高亮后缀
        query.addHighlightField("item_title");                     //设置高亮字段
        //4.调用dao方法
        SearchResult result = solrJDao.findItemsByKeywords(query);
        //计算总页数
        long totalPages = (long) Math.ceil(1.0 * result.getRecordCount() / rows);
        //5.向result添加其它属性
        result.setTotalPages(totalPages);

        return result;
    }
}
