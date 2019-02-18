package com.oservice.admin.modules.wechat.share.controller;

import com.oservice.admin.modules.wechat.share.utils.ServletContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 分享的页面请求的后台，生成签名
 * <p>Title:InitAccessTokenController</p>
 * <p>Description:</p>
 *
 * @author Shr
 * @date 2018年10月16日上午1:07:00
 */
@Controller
@Scope("prototype")
public class InitAccessTokenController extends HttpServlet {

    @Value("${WX_APPID}")
    private String WX_APPID;

    @RequestMapping("/initWXJSInterface")
    public @ResponseBody
    Map<String, String> init(String url) {
        System.out.println(WX_APPID);
        // 从全局缓存中取出jsapi_ticket
        ServletContext servletContext = ServletContextUtil.getServletContext();
        String jsapi_ticket = (String) servletContext.getAttribute("jsapi_ticket");

        Map<String, String> ret = sign(jsapi_ticket, url);

        System.out.println("currurl = " + url);

        // 注意 URL 一定要动态获取，不能 hardcode
//		for (Map.Entry entry : ret.entrySet()) {
//			System.out.println(entry.getKey() + ", " + entry.getValue());
//		}
        System.out.println("signature = " + ret.get("signature"));
        return ret;
    }

    public Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("appId", WX_APPID);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
