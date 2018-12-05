package com.oservice.admin.modules.app.dao;

import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.SearchResult;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: 添加索引and搜索功能
 * @author: YJmiss
 * @create: 2018-12-04 09:40
 **/
@Repository
public class SolrJDao {
    @Autowired
    private SolrClient solrServer;
    @Autowired
    private XryCourseService courseService;

    //将数据库内容添加到索引库中
    public Result addIndex() {
        try {
            //1.查询数据库中所有的记录
            List<SearcherItem> allItems = courseService.findAllItems();
            //2.遍历所有的记录并同时添加到文档中
            for (SearcherItem item : allItems) {
                //2.1）构建一个文档对象
                SolrInputDocument doc = new SolrInputDocument();
                //2.2)在文档中添加各个域
                doc.addField("id", item.getId());
                doc.addField("item_title", item.getTitle());
                doc.addField("item_price", item.getPrice());
                doc.addField("item_image", item.getImage());
                doc.addField("item_category_name", item.getCategoryName());
                doc.addField("item_nickname", item.getNickname());
                doc.addField("item_course_desc", item.getCourseDesc());
                //2.3)将文档添加到solrServer中
                solrServer.add(doc);
            }
            //3.提交
            solrServer.commit();
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "添加到索引库失败！");
        }
    }

    //课程上架索引同步
    public Boolean addIndexById(Long id) {
        try {
            //1.查询数据库中相关信息
            SearcherItem item = courseService.findItemsById(id);
            //          System.out.println(item.getId());
            //2.遍历所有的记录并同时添加到文档中
            //2.1）构建一个文档对象
            SolrInputDocument doc = new SolrInputDocument();
            //2.2)在文档中添加各个域
            doc.addField("id", item.getId());
            doc.addField("item_title", item.getTitle());
            doc.addField("item_price", item.getPrice());
            doc.addField("item_image", item.getImage());
            doc.addField("item_category_name", item.getCategoryName());
            doc.addField("item_nickname", item.getNickname());
            doc.addField("item_course_desc", item.getCourseDesc());
            //2.3)将文档添加到solrServer中
            solrServer.add(doc);
            //3.提交
            solrServer.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //根据ID删除索引库
    public void deleteIndexById(Long id) throws IOException, SolrServerException {
        solrServer.deleteById("" + id);
        solrServer.commit();
    }
    //完成搜索功能
    public SearchResult findItemsByKeywords(SolrQuery query) throws Exception {
        SearchResult result = new SearchResult();
        //1.使用solrServer结合查询条件得到查询结果
        QueryResponse response = solrServer.query(query);
        //1.1)获取查询对象对应的结果集
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        //1.2)将查询得到的总记录数赋值给result对象
        result.setRecordCount(numFound);
        //2.遍历结果集results，
        List<SearcherItem> itemList = new ArrayList<>();
        //获取高亮集合
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument sdoc : results) {
            String id = (String) sdoc.get("id");                        //商品id
            String title = "";
            //根据主键id获取高亮集合
            Map<String, List<String>> map = highlighting.get(id);
            List<String> list = map.get("item_title");
            //获取标题思路（加上高亮显示）
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) sdoc.get("item_title");                //商品title
            }
            long price = Float.valueOf((Float) sdoc.get("item_price")).intValue();//商品价格
            String image = (String) sdoc.get("item_image");                //商品图像
            String categoryName = (String) sdoc.get("item_category_name"); //商品类别名称
            String nickname = (String) sdoc.get("item_nickname"); //讲师名称
            String courseDesc = (String) sdoc.get("item_course_desc"); //课程详情
            //构建对象
            SearcherItem sItem = new SearcherItem(id, title, price, image, categoryName, nickname, courseDesc);
            itemList.add(sItem);
        }
        result.setItemList(itemList);
        return result;
    }
}
