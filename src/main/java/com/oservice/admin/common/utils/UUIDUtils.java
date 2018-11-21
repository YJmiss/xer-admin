package com.oservice.admin.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * ID生成工具
 */
public class UUIDUtils {
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
