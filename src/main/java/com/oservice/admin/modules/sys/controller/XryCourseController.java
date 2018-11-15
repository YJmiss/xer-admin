package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import com.oservice.admin.modules.sys.service.XryCourseDescService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        for (Long id:ids) {
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
     * @return
     */
    @GetMapping("/treeCourse")
    @RequiresPermissions("xry:course:treeCourse")
    public Result treeCourse(){
        List<XryCourseEntity> courseList = xryCourserService.treeCourse();
        return Result.ok().put("courseList", courseList);
    }

    /**
     * 上传图片
     * @param formData
     * @param config
     * @return
     */
    @PostMapping("/upload/img")
    @RequiresPermissions("xry:course:upload:img")
    public Result uploadImg(@RequestBody(required=false)HttpServletRequest request, @RequestParam("formData") String[] formData, String type, String config) throws FileUploadException {
        Map<String, String> uri = new HashMap<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(new Date());
        String tempPathDir = "";
        File tempPathDirFile = new File(tempPathDir);
        String realDir = "product/" + type + "/" + time + "/" + "516513650132032023";
        try {
            //文件上传的三部曲
            //创建工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置缓冲区大小，这里是400kb
            factory.setSizeThreshold(4096 * 100);
            // 设置缓冲区目录
            factory.setRepository(tempPathDirFile);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置上传文件的大小 12M
            upload.setSizeMax(4194304 * 3);
            //创建解析器 // 得到所有的文件
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> i = items.iterator();
            while (i.hasNext()) {
                FileItem fi = i.next();
                // false表示文件 否则字段
                if (!fi.isFormField()) {
                    String fileName = fi.getName();
                    if (fileName != null) {
                        // 这里加一个限制，如果不是图片格式，则提示错误. (gif,jpg,jpeg,bmp,png)
                        String suffixName = FilenameUtils.getExtension(fileName);
                        if ("jpg".equalsIgnoreCase(suffixName) || "jpeg".equalsIgnoreCase(suffixName) || "png".equalsIgnoreCase(suffixName)) {
                            // 文件名
                            DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
                            String baseName = FilenameUtils.getBaseName(fileName);
                            String dirFileUri = realDir + "/" + baseName + format2.format(new Date()) + "." + suffixName;
                            return Result.ok(dirFileUri);
                        } else {
                            return Result.ok("");
                        }
                    }
                } else {
                    fi.getString("UTF-8");
                    uri.put(new String(fi.getFieldName().getBytes("iso-8859-1"), "utf-8"),
                            new String(fi.getString().getBytes("iso-8859-1"), "utf-8"));
                }
            }
//            return Result.ok(uri);
        } catch (FileUploadException e) {
            return Result.error(404,"request文件接收失败");
        } catch (UnsupportedEncodingException e) {
            return Result.error(404,"乱码解决失败");
        }
        return Result.ok("");
    }

}
