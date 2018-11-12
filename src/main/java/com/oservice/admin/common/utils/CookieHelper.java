package com.oservice.admin.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieHelper {
    /**
     * 读取所有cookie
     *
     * @return
     */
    public static Map<String, String> showCookies() {
        Map<String, String> map = new HashMap<>();
        Cookie[] cookies = getRequest().getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }
        return map;
    }

    /**
     * 创建cookie,并将新cookie添加
     *
     * @param cookieName
     * @param cookieValue
     */
    public static void addCookie(String cookieName, String cookieValue) {
        addCookie(cookieName, cookieValue, true);
    }

    public static void addCookie(String cookieName, String cookieValue, Boolean rememberLogin) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (rememberLogin) {
            cookie.setMaxAge(30 * 24 * 60 * 60);
        } else {
            cookie.setMaxAge(-1);
        }
        cookie.setPath("/");
        getResponse().addCookie(cookie);
    }

    public static void addCookie(String cookieName, String cookieValue,int maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
       cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        getResponse().addCookie(cookie);
    }


    /**
     * 修改cookie
     *
     * @param cookieName  cookie名称
     * @param cookieValue cookie值
     */
    public static void editCookie(String cookieName, String cookieValue) {
        Cookie[] cookies = getRequest().getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setValue(cookieValue);
                    cookie.setPath("/");
                    cookie.setMaxAge(3 * 24 * 60 * 60);
                    getResponse().addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * 删除cookie
     *
     * @param cookieName cookie的名字
     */
    public static void delCookie(String cookieName) {
        Cookie[] cookies = getRequest().getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    getResponse().addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * 读取cookie
     *
     * @param cookieName cookie的名字
     */
    public static String getCookie(String cookieName) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            Cookie[] cookies = getRequest().getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(cookieName)) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }


    public static HttpServletRequest getRequest() {
        return RequestContextHolder.getRequestAttributes() == null ? null : ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return RequestContextHolder.getRequestAttributes() == null ? null : ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void clearCookie() {
        Cookie[] cookies = getRequest().getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                getResponse().addCookie(cookie);
            }
        }
    }
}
