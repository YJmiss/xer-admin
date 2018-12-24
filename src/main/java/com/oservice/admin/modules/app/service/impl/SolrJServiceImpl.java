package com.oservice.admin.modules.app.service.impl;

import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.SearchResult;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.app.service.SolrJService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    public SearchResult findItemsByKeywords(String keyword, Integer page, Integer rows, Integer sortData) throws Exception {
        //1.构建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //2.添加查询条件
        //2.1)设置查询条件
        query.setQuery(keyword);
        //2.2)设置查询的默认字段列表
        query.set("df", ConfigConstant.DEFAULT_FIELD_LIST);
        // query.set("df", "item_nickname");
        //设置默认排序 TODO：添加一个综合排排序的字段 and 人气排序的字段，and 好评率字段，到索引库  query.set("sort","对应字段名称：desc");
        if (sortData == 0) {
            query.set("defType", "dismax");
            query.set("qf", "item_title^10 item_category_name^6 item_nickname^6 item_course_desc 0.8"); //设置权重，标题>类别=作者>内容
        }
        if (sortData == 1) {      //好评升序
            query.setSort("feedback", SolrQuery.ORDER.asc);
        }
        if (sortData == 2) {      //好评降序
            query.setSort("feedback", SolrQuery.ORDER.desc);
        }
        if (sortData == 3) {      //人气升序
            query.setSort("applicantCount", SolrQuery.ORDER.asc);
        }
        if (sortData == 4) {      //人气降序
            query.setSort("applicantCount", SolrQuery.ORDER.desc);
        }
        if (sortData == 6) {        //价格降序
            query.setSort("item_price", SolrQuery.ORDER.desc);
        }
        if (sortData == 5) {        //价格升序
            query.setSort("item_price", SolrQuery.ORDER.asc);
        }
        //    query.set("sort","item_price:desc");
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

    @Override
    public Boolean addIndexById(Long id) {
        return solrJDao.addIndexById(id);
    }

    @Override
    public void deleteIndexById(Long id) {
        try {
            solrJDao.deleteIndexById(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}
