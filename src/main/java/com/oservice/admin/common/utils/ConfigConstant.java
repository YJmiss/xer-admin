package com.oservice.admin.common.utils;

/**
 * 系统参数相关Key
 * @author LingDu
 * @version 1.0
 */
public class ConfigConstant {
    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";
    /**
     * 短信验证信息
     */
    public final static String SMSURL ="http://gw.api.taobao.com/router/rest";
    public final static String APPKEY ="23277221";;
    public final static String SECRET="ee7b5c5e5dc7bb82f27d382d1c5fb67a";
    public final static String TEMPLATECODE="SMS_2695297";
    /**
     * JWT配置
     * SECRET_KEY:保存在服务端的密钥
     * EXPSECOND:jwt令牌的有效时间
     * REFRESHSECOND:刷新令牌的有效时间
     */
    public final static String SECRET_KEY = "JYmiss";
    public final static int EXPSECOND = 60 * 60 * 5 * 1000;
    public final static Long REFRESHSECOND = 60 * 60 * 24 * 15 * 1000L;


}
