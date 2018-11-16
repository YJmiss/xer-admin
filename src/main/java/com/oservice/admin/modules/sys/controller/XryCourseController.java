package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/course")
public class XryCourseController extends AbstractController {
    @Resource
    private XryCourseService xryCourserService;
    @Resource
    private XryCourseCatalogService xryCourseCatalogService;

    /**
     * 查询课程列表
     * @param params
     * @return
     */
    @SysLog("查询课程列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:course:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryCourserService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存课程
     * @param course
     * @return
     */
    @SysLog("保存课程")
    @PostMapping("/save")
    @RequiresPermissions("xry:course:save")
    public Result save(@RequestBody XryCourseEntity course){
        ValidatorUtils.validateEntity(course, AddGroup.class);
        xryCourserService.save(course);
        return Result.ok();
    }

    /**
     * 课程信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:course:info")
    public Result info(@PathVariable("id") Long id){
        XryCourseEntity course = xryCourserService.queryById(id);
        return Result.ok().put("course", course);
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @SysLog("修改课程")
    @PostMapping("/update")
    @RequiresPermissions("xry:course:update")
    public Result update(@RequestBody XryCourseEntity course){
        ValidatorUtils.validateEntity(course, UpdateGroup.class);
        xryCourserService.update(course);
        return Result.ok();
    }

    /**
     * 删除课程
     * @param ids
     * @return
     */
    @SysLog("删除课程")
    @PostMapping("/delete")
    @RequiresPermissions("xry:course:delete")
    public Result delete(@RequestBody Long[] ids){
        // 删除课程，同事删除课程对应的课程描述和课程目录
        for (Long id : ids) {
            XryCourseCatalogEntity xryCourseCatalogEntity = xryCourserService.queryCourseCatalogByCourseId(id);
            if (null != xryCourseCatalogEntity) {
                return Result.error("请先删除该课程下面的课程目录");
            }
            XryCourseDescEntity xryCourseDescEntity = xryCourserService.queryCourseDescById(id);
            if (null != xryCourseDescEntity) {
                return Result.error("请先删除该课程下面的课程描述");
            }
        }
        xryCourserService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 构造课程树
     *
     * @return
     */
    @GetMapping("/treeCourse")
    @RequiresPermissions("xry:course:treeCourse")
    public Result treeCourse() {
        List<XryCourseEntity> courseList = xryCourserService.treeCourse();
        return Result.ok().put("courseList", courseList);
    }

    /**
     * 上传图片
     *
     * @param imgName
     * @param config
     * @return
     */
    @PostMapping("/upload/img")
    @RequiresPermissions("xry:course:upload:img")
    public Result uploadImg(@RequestBody(required = false) @RequestParam("imgName") String imgName, String imgUrl, String type, String config) throws FileUploadException {
        if (null != imgName && null != imgUrl) {
            // 把图片写入到服务器的upload/image/文件夹下面
            BufferedOutputStream out = null;
            File file = null;
            try {
                file = new File(imgUrl + "/" + imgName);
                out = new BufferedOutputStream(new FileOutputStream(file));
                //out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 如果不是图片格式，则提示错误. (gif,jpg,jpeg,bmp,png)
            String suffixName = FilenameUtils.getExtension(imgName);
            if ("jpg".equalsIgnoreCase(suffixName) || "jpeg".equalsIgnoreCase(suffixName) || "png".equalsIgnoreCase(suffixName)) {
                String tempPathDir = "";
                File tempPathDirFile = new File(tempPathDir);
                String realDir = "upload/" + type;
                //创建工厂
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // 设置缓冲区大小，这里是400kb
                factory.setSizeThreshold(4096 * 100);
                // 设置缓冲区目录
                factory.setRepository(tempPathDirFile);
                ServletFileUpload upload = new ServletFileUpload(factory);
                //设置上传文件的大小 12M
                upload.setSizeMax(4194304 * 3);
                // 生成图片路径+图片名   upload/course/20181115142427303c789.jpg
                String imageUrl = realDir + "/" + UUIDUtils.generateFileName(imgName);
                return Result.ok(imageUrl);
            } else {
                return Result.error(404, "图片格式不正确");
            }
        } else {
            return Result.error(404, "图片名为空");
        }
    }

}
