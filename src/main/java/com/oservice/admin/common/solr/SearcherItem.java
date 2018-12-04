package com.oservice.admin.common.solr;

import java.io.Serializable;

/**
 * @program: oservice
 * @description: 搜索展示
 * @author: YJmiss
 * @create: 2018-12-04 09:52
 **/
public class SearcherItem implements Serializable {
    private String id;
    private String title;
    private long price;
    private String image;
    private String cid;
    private String category_name;

    public SearcherItem() {
        super();
    }

    public SearcherItem(String id, String title, long price, String image, String category_name) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.category_name = category_name;
    }

    public SearcherItem(String id, String title, long price, String image, String cid,
                        String category_name) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.cid = cid;
        this.category_name = category_name;
    }

    public String[] getImages() {
        return this.image.split(",");

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCid() {
        return cid;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
