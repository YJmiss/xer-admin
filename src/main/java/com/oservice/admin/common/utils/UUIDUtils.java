package com.oservice.admin.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * ID生成工具
 */
public class UUIDUtils {


    private static Date date = new Date();
    private static StringBuilder buf = new StringBuilder();
    private static int seq = 0;
    private static final int ROTATION = 99999;

    public static synchronized long next() {
        if (seq > ROTATION) seq = 0;
        buf.delete(0, buf.length());
        date.setTime(System.currentTimeMillis());
        String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
        return Long.parseLong(str);
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] retArray = new String[number];
        for (int i = 0; i < number; i++) {
            retArray[i] = getUUID();
        }
        return retArray;
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

 /*     public static void main(String[] args) {
        System.out.println(getUUID());
    }*/

    /**
     * 生成图片名字
     *
     * @param oldName
     * @return
     */
    public static String generateFileName(String oldName) {
        String randomStr = new Random().nextInt(10) + UUID.randomUUID().toString().substring(0, 4);// 随机数+UUID的前4位
        String timeStr = new SimpleDateFormat("yyyyMMddHH24mmss").format(new Date());// 时间字符串
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        System.out.println(suffix);
        return timeStr + randomStr + suffix;
    }
}
