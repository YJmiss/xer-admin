package com.oservice.admin.common.utils;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program: oservice
 * @description: 获取视频时长
 * @author: YJmiss
 * @create: 2018-12-28 18:30
 **/

public class ReadVideoTime {
    public static String ReadVideoTime(MultipartFile file, String prefix) throws IOException {
        final File excelFile = File.createTempFile(UUIDUtils.getUUID(), prefix);
        // MultipartFile to File
        file.transferTo(excelFile);
        Encoder encoder = new Encoder();
        String length = "";
        try {
            MultimediaInfo m = encoder.getInfo(excelFile);
            long ls = m.getDuration() / 1000;
            int hour = (int) (ls / 3600);
            int minute = (int) (ls % 3600) / 60;
            int second = (int) (ls - hour * 3600 - minute * 60);
            length = hour + "'" + minute + "''" + second + "'''";
        } catch (Exception e) {
            e.printStackTrace();
        }
        //程序结束时，删除临时文件
        deleteFile(excelFile);
        return length;
    }

    /**
     * 删除
     *
     * @param files
     */
    private static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
