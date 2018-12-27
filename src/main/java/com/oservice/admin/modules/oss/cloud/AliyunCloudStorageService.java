package com.oservice.admin.modules.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.oservice.admin.common.exception.GlobalException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 * @author LingDu
 * @version 1.0
 */
public class AliyunCloudStorageService extends CloudStorageService {
    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config){
        this.config = config;

        //初始化
        init();
    }

    private void init(){
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e){
            throw new GlobalException("上传文件失败，请检查配置信息", e);
        }
        String aliyunBucketName = config.getAliyunBucketName() + ".";
        String aliyunDomain = config.getAliyunDomain();
        char[] stringArr = aliyunDomain.toCharArray();
        String aliyunUp = "";
        for (int i = 0; i < 8; i++) {
            aliyunUp += stringArr[i];
        }
        aliyunUp += aliyunBucketName;
        for (int i = 0; i < stringArr.length; i++) {
            if (i > 7) {
                aliyunUp += stringArr[i];
            }
        }
        return aliyunUp + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }
}
