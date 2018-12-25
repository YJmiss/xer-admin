package com.oservice.admin.modules.wechat.bean;

import java.util.TreeMap;

/**
 * @ClassName: WechatTemplateMsg
 * @Description: 消息模板
 * @Author: xiewl
 * @Date: 2018/9/5 14:22
 * @Version 1.0
 */
public class TemplateMsg {
    private String touser; //接收者openid
    private String template_id; //模板ID
    private String url; //模板跳转链接

    private TreeMap<String, TreeMap<String, String>> data; //data数据

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TreeMap<String, TreeMap<String, String>> getData() {
        return data;
    }

    public void setData(TreeMap<String, TreeMap<String, String>> data) {
        this.data = data;
    }

    /**
     * 参数
     *
     * @param value
     * @param color 可不填
     * @return
     */
    public static TreeMap<String, String> item(String value, String color) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("value", value);
        params.put("color", color);
        return params;
    }

}
