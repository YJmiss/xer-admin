package com.oservice.admin.common.utils;

import com.oservice.admin.common.solr.SearcherItem;

import java.util.List;

/**
 * @program: oservice
 * @description: 搜索结果
 * @author: YJmiss
 * @create: 2018-12-04 09:46
 **/
public class SearchResult {
    private long totalPages;                //总页数
    private long recordCount;                //总记录数
    private List<SearcherItem> itemList;    //每一页的商品集合

    public long getTotalPages() {
        return totalPages;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public List<SearcherItem> getItemList() {
        return itemList;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public void setItemList(List<SearcherItem> itemList) {
        this.itemList = itemList;
    }

}
