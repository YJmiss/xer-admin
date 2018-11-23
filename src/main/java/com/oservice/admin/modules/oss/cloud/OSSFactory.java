package com.oservice.admin.modules.oss.cloud;

import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.common.utils.Constant;
import com.oservice.admin.common.utils.SpringContextUtils;
import com.oservice.admin.modules.sys.service.SysConfigService;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 文件上传Factory
 *
 * @author LingDu
 * @version 1.0
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

    /**
     * 读取图片
     * @param file
     * @throws Exception
     */
    public static void saveFileToService(MultipartFile file, String filePath, String fileName) throws Exception {
        InputStream ins = file.getInputStream();
        try {
            OutputStream os = new FileOutputStream(file.getOriginalFilename());
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成文件在数据库的存储路径
     * @param file
     * @param realDirPath
     * @return
     */
    public static String createFileUrl(MultipartFile file, String realDirPath, String fileName) {
        //获取文件名
        File tempPathDirFile = new File(file.getOriginalFilename());
        //创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置缓冲区大小，这里是400kb
        factory.setSizeThreshold(4096 * 100);
        // 设置缓冲区目录
        factory.setRepository(tempPathDirFile);
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置上传文件的大小 12M
        upload.setSizeMax(4194304 * 3);
        // 生成图片路径+图片名   upload/image/course/20181115142427303c789.jpg
        String imageUrl = realDirPath + "/" + fileName;
        return imageUrl;
    }

    /**
     * 生成文件在数据库的存储路径
     * @param inputStream
     * @param realDirPath
     * @return
     */
    public static String createFileUrl2(InputStream inputStream, String realDirPath, String fileName) {
        //获取文件名
        File tempPathDirFile = new File(fileName);
        //创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置缓冲区大小，这里是400kb
        factory.setSizeThreshold(4096 * 100);
        // 设置缓冲区目录
        factory.setRepository(tempPathDirFile);
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置上传文件的大小 12M
        upload.setSizeMax(4194304 * 3);
        // 生成图片路径+图片名   upload/image/course/20181115142427303c789.jpg
        String imageUrl = realDirPath + "/" + fileName;
        return imageUrl;
    }

    /**
     * 生成图片名字
     * @param oldName
     * @return
     */
    public static String generateFileName(String oldName, String ImgType) {
        String randomStr = new Random().nextInt(10) + UUID.randomUUID().toString().substring(0, 4);// 随机数+UUID的前4位
        String timeStr = new SimpleDateFormat("yyyyMMddHH24mmss").format(new Date());// 时间字符串
//        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String suffix = "";
        if (StringUtils.isNotBlank(ImgType)) {
            String[] suffixs =  ImgType.split("/");
            if ("jpeg".equals(suffixs[1]) || "jpg".equals(suffixs[1])) {
                suffix = ".jpg";
            } else if ("png".equals(suffixs[1])) {
                suffix = ".png";
            }
        }

        System.out.println(suffix);
        return timeStr + randomStr + suffix;
    }

    /**
     * 上传图片到服务器
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 生成在数据库的存储的路径
     * @return
     */
    public static String createSQLPath(String filePath) {
        if (StringUtils.isNotBlank(filePath)) {
            String[] sqlPath = filePath.split("classes/");
            return sqlPath[1];
        } else {
            return "";
        }
    }

    /**
     * 将InputStream转换为byte数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToByteArr(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
