package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 广告内容表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_content")
public class XryContentEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 广告内容id
     */
    private Long id;
    /**
     * 广告内容标题
     */
    private String title;

    /**
     * 广告类别
     */
    private Integer category;

    /**
     * 广告内容描述
     */
    private String titleDesc;
    /**
     * 链接
     */
    private String url;
    /**
     * 图片绝对路径
     */
    private String pic;
    /**
     * 图片2
     */
    private String pic2;
    
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
