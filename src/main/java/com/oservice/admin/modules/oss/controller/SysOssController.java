package com.oservice.admin.modules.oss.controller;

import com.google.gson.Gson;
import com.oservice.admin.common.exception.GlobalException;
import com.oservice.admin.common.utils.*;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AliyunGroup;
import com.oservice.admin.common.validator.group.QcloudGroup;
import com.oservice.admin.common.validator.group.QiniuGroup;
import com.oservice.admin.modules.oss.cloud.CloudStorageConfig;
import com.oservice.admin.modules.oss.service.SysOssService;
import com.oservice.admin.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @author LingDu
 * @version 1.0
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {

	@Resource
	private SysOssService sysOssService;

    @Resource
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public Result list(@RequestParam Map<String, Object> params){
		PageUtils page = sysOssService.queryPage(params);

		return Result.ok().put("page", page);
	}


    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public Result config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return Result.ok().put("config", config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@PostMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public Result saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return Result.ok();
	}
	

	/**
	 * 上传文件
	 */
	@PostMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public Result upload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
		if (uploadFile.isEmpty()) {
			throw new GlobalException("上传文件不能为空");
		}

		/*//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.insert(ossEntity);

		return Result.ok().put("url", url);*/

		Map map = new HashMap<>();
		try {
			//1.第一步：定义上传工具类对象
			FastDFSClient dfsClient = new FastDFSClient("classpath:fastdfs/client.conf");
			//2.根据上传文件分析出基本后缀名
			//2.1)得到原始文件名
			String originalFilename = uploadFile.getOriginalFilename();
			//2.2)得到文件后缀名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//3.得到文件的内容（二进制数据）
			byte[] fileContent = uploadFile.getBytes();
			String url = dfsClient.uploadFile(fileContent, extName);
			url = "http://192.168.1.30:1025" + "/" + url;
			System.out.println("url:" + url);
			//上传成功的map
			return Result.ok().put("url", url);

		} catch (Exception e) {
			e.printStackTrace();
			//上传失败的map
			map.put("error", 1);
			map.put("message", "图片上传失败！");
		}
		return Result.ok(map);
	}


	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public Result delete(@RequestBody Long[] ids){
		sysOssService.deleteBatchIds(Arrays.asList(ids));

		return Result.ok();
	}

}
