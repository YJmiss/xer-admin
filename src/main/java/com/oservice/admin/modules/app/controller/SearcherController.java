package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.SearchResult;
import com.oservice.admin.modules.app.service.SolrJService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: oservice
 * @description: 搜索控制器
 * @author: YJmiss
 * @create: 2018-12-04 16:21
 **/
@RestController
@RequestMapping("/api/appSearcher")
@Api(description = "搜索api")
public class SearcherController {
    @Autowired
    private SolrJService solrJService;

    //搜索功能
    @ApiOperation(value = "课程搜索接口", notes = "keyword：关键字 每页显示20条数据")
    @GetMapping("/search")
    public Result searchItems(String keyword, @RequestParam(defaultValue = "1")
            Integer page) {
        //对 get 提交的字符串转换转码处理
        //  keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
        SearchResult result = null;
        try {
            result = solrJService.findItemsByKeywords(keyword, page, ConfigConstant.SEARCHER_ITEM_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "搜索系统错误/联系管理员处理");
        }
        if (result.getRecordCount() < 1) {
            return Result.error(204, "没有搜索到该关键字相应结果，请更换查询关键字");
        }
        return Result.ok().put("result", result);
    }
}
