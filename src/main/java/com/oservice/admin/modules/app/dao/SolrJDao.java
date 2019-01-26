package com.oservice.admin.modules.app.dao;

import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.SearchResult;
import com.oservice.admin.modules.app.information.TallyOrderService;
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

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Resource
    private TallyOrderService tallyOrderService;

    //将数据库内容添加到索引库中
    public Result addIndex() {
        try {
            //1.查询数据库中所有的记录
            List<SearcherItem> allItems = courseService.findAllItems();
            //2.遍历所有的记录并同时添加到文档中
            for (SearcherItem item : allItems) {
                //2.1）构建一个文档对象
                SolrInputDocument doc = new SolrInputDocument();
                //TODO:获取好评百分数
                Integer feedback = courseService.getFeedback(Long.parseLong(item.getId()));
                //2.2)在文档中添加各个域
                doc.addField("id", item.getId());
                doc.addField("item_title", item.getTitle());
                doc.addField("item_price", item.getPrice());
                doc.addField("item_image", item.getImage());
                doc.addField("item_category_name", item.getCategoryName());
                doc.addField("item_nickname", item.getRealName());
                doc.addField("item_course_desc", item.getCourseDesc());
                doc.addField("applicantCount", item.getApplicantCount());
                doc.addField("feedback", feedback);
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
            //TODO:获得好评百分数
            Integer feedback = courseService.getFeedback(Long.parseLong(item.getId()));
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
            doc.addField("item_nickname", item.getRealName());
            doc.addField("item_course_desc", item.getCourseDesc());
            doc.addField("applicantCount", item.getApplicantCount());
            doc.addField("feedback", feedback);
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
            String realName = (String) sdoc.get("item_nickname"); //讲师名称
            String courseDesc = (String) sdoc.get("item_course_desc"); //课程详情
            Integer applicantCount = (Integer) sdoc.get("applicantCount");//报名人数
            Integer feedback = (Integer) sdoc.get("feedback");//好评百分数
            //构建对象
            SearcherItem sItem = new SearcherItem(id, title, price, image, categoryName, realName, courseDesc, applicantCount, feedback);
            itemList.add(sItem);
        }
        result.setItemList(itemList);
        return result;
    }

    //完成搜索功能
    public List<Map<String, Object>> findall() throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        SearchResult result = new SearchResult();
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        //Adding the field to be retrieved{responseHeader={status=0,QTime=1,params={q=*:*,fl=*,wt=javabin,version=2}},response={numFound=27,start=0,docs=[SolrDocument{id=46, item_title=精品】初中英语七年级上册（初一）人教版教材同步知识精讲---（推拿）, item_price=380.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/6513d2295fee4c2aa9c90781dffa8196.png, item_category_name=初中, item_nickname=王老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/7b3bd239b36a46aba6ad3b55bcda3fc3.png"></p>, applicantCount=0, feedback=0, _version_=1621821099468652544}, SolrDocument{id=30, item_title=新生儿常见疾病锦囊-肠胃呵护, item_price=298.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/4b557ff3e2d542cbb81c76b7cf3e8647.jpg, item_category_name=新生儿护理, item_nickname=王老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/7b3bd239b36a46aba6ad3b55bcda3fc3.png"></p>, applicantCount=3, feedback=0, _version_=1621815231455952896}, SolrDocument{id=31, item_title=新生儿抚触按摩实操, item_price=5980.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/a86fd40564b8493ba2d22f499455d89e.png, item_category_name=新生儿护理, item_nickname=李老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/7b3bd239b36a46aba6ad3b55bcda3fc3.png"></p>, applicantCount=5, feedback=0, _version_=1621815245035012096}, SolrDocument{id=20190105015, item_title=催乳师市场运营课程, item_price=345600.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/d92b0ea561394979a911720bca2e6f08.jpg, item_category_name=催乳类培训, item_nickname=李老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/e0c36deea61d46d18c2abc8b3692945d2019010513244827143d2.png"></p>, applicantCount=0, feedback=0, _version_=1621821119427248128}, SolrDocument{id=43, item_title=2019年初级会计师职称协议通关【VIP全程私教辅导每日计划任务】---（母婴）, item_price=399.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/9968ac7e93c6466ea4514ab0bef56688.jpg, item_category_name=职业考证, item_nickname=王老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/7b3bd239b36a46aba6ad3b55bcda3fc3.png"></p>, applicantCount=0, feedback=0, _version_=1621965728018071552}, SolrDocument{id=38, item_title=标准韩国语教学视频（第一册精讲）思密达韩语网校韩语乐乐老师---（母婴）, item_price=99.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/9968ac7e93c6466ea4514ab0bef56688.jpg, item_category_name=语言留学, item_nickname=王老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/90f9e4928a1c42e4910fc9f2b89a2142.png"></p>, applicantCount=0, feedback=0, _version_=1621965724766437376}, SolrDocument{id=42, item_title=【元旦特讲】韩语学习高雅思考基础班【蓝轨迹】---（母婴）, item_price=0.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190102/c20f925e2fa149659cabfe27122ba026.jpg, item_category_name=小语种, item_nickname=王老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/59cfbd673f6d4bdb993d3cab0ec57960.jpg"></p>, applicantCount=0, feedback=0, _version_=1621965727413043200}, SolrDocument{id=20190105017, item_title=小儿推拿穴位揭秘, item_price=28800.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/0423d868c01940eeb88bf4892cbb1094.jpg, item_category_name=育儿护理, item_nickname=李老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/bf5f435d142b44d8a9639e883e50bb122019010513245042267f9.jpg"></p>, applicantCount=0, feedback=0, _version_=1621821121230798848}, SolrDocument{id=20190105016, item_title=母乳喂养指导, item_price=2300.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/dacee38da9d449339d3afa566bdb02b6.jpg, item_category_name=育儿护理, item_nickname=李老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20190105/f336d0a6591d42e0876e306cf6175685201901051324494282cb6.jpg"></p>, applicantCount=0, feedback=0, _version_=1621821120081559552}, SolrDocument{id=39, item_title=TOEFL零基础入门小白班（公益课）---（母婴）, item_price=0.0, item_image=https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/6513d2295fee4c2aa9c90781dffa8196.png, item_category_name=托福, item_nickname=王老师, item_course_desc=<p><img src="https://xey-file.oss-cn-shenzhen.aliyuncs.com/20181226/59cfbd673f6d4bdb993d3cab0ec57960.jpg"></p>, applicantCount=0, feedback=0, _version_=1621965725916725248}]}}
        query.addField("*");
        query.setRows(100000);
        query.setStart(1);
        //1.使用solrServer结合查询条件得到查询结果
        QueryResponse response = solrServer.query(query);
        //1.1)获取查询对象对应的结果集
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        //1.2)将查询得到的总记录数赋值给result对象
        result.setRecordCount(numFound);
        for (SolrDocument sdoc : results) {
            Map<String, Object> map = new HashMap<>();
            String title = (String) sdoc.get("item_title");                //课程title
            long pic = Float.valueOf((Float) sdoc.get("item_price")).intValue();//课程价格
            String price = new BigDecimal(pic).divide(new BigDecimal(100)).setScale(2).toString();//课程价格（元）
            String image = (String) sdoc.get("item_image");                //课程图像
            Integer feedback = (Integer) sdoc.get("feedback");//好评百分数
            Integer applicantCount = (Integer) sdoc.get("applicantCount");//人气
            long coi = tallyOrderService.getBrokerage(pic);//分销佣金
            String commission = new BigDecimal(coi).divide(new BigDecimal(100)).setScale(2).toString();//分销佣金(元)
            map.put("title", title);
            map.put("price", price);
            map.put("image", image);
            map.put("feedback", feedback);
            map.put("applicantCount", applicantCount);
            map.put("commission", commission);
            list.add(map);
        }
        return list;
    }
    /**
     * @Description: 修改索引字段信息
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/24
     */
    public void update(Long id, Object fieldValue, int state) throws IOException, SolrServerException {
        HashMap<String, Object> oper = new HashMap<String, Object>();
        oper.put("set", fieldValue);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        if (state == 1) {
            doc.addField("applicantCount", oper);
        }
        if (state == 0) {
            doc.addField("feedback", oper);
        }
        // HttpSolrClient client = new HttpSolrClient("");
        solrServer.add(doc);
        solrServer.commit();
    }
}
