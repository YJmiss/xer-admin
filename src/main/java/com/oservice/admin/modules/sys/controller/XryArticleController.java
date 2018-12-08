package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.app.service.SolrJService;
import com.oservice.admin.modules.sys.entity.XryArticleEntity;
import com.oservice.admin.modules.sys.service.XryArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * 文章表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/article")
public class XryArticleController extends AbstractController {
    /** 发布文章 */
    private static final Integer PUBLISH_ARTICLE = 1;
    /** 取消发布文章 */
    private static final Integer CANCEL_PUBLISH = 0;
    /** 推荐文章 */
    private static final Integer RECOMMEND_ARTICLE = 1;
    /** 取消推荐文章 */
    private static final Integer CANCEL_RECOMMEND = 0;
    @Resource
    private XryArticleService xryArticleService;
    /**
     * 搜索服务
     */
    @Autowired
    private SolrJService solrJService;
    /**
     * 查询文章列表
     * @param params
     * @return
     */
    @SysLog("查询文章列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:article:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryArticleService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存文章
     * @param article
     * @return
     */
    @SysLog("保存文章")
    @PostMapping("/save")
    @RequiresPermissions("xry:article:save")
    public Result save(@RequestBody XryArticleEntity article){
        ValidatorUtils.validateEntity(article, AddGroup.class);
        article.setCreated(new Date());
        article.setCreateUser(getUserId().toString());
        xryArticleService.save(article);
        return Result.ok();
    }

    /**
     * 文章信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:article:info")
    public Result info(@PathVariable("id") Long id){
        XryArticleEntity article = xryArticleService.queryById(id);
        return Result.ok().put("article", article);
    }

    /**
     * 修改文章
     * @param article
     * @return
     */
    @SysLog("修改文章")
    @PostMapping("/update")
    @RequiresPermissions("xry:article:update")
    public Result update(@RequestBody XryArticleEntity article){
        ValidatorUtils.validateEntity(article, UpdateGroup.class);
        xryArticleService.update(article);
        return Result.ok();
    }

    /**
     * 删除文章
     * @param ids
     * @return
     */
    @SysLog("删除文章")
    @PostMapping("/delete")
    @RequiresPermissions("xry:article:delete")
    public Result delete(@RequestBody Long[] ids){

        //solrJService.deleteIndexById(id);
        xryArticleService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 发布文章
     * @param ids
     * @return
     */
    @SysLog("发布文章")
    @PostMapping("/publishArticle")
    @RequiresPermissions("xry:article:publishArticle")
    public Result publishArticle(@RequestBody Long[] ids){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("status",PUBLISH_ARTICLE);
        params.put("publishTime",new Date());
        xryArticleService.updateArticleStatus(params);
        return Result.ok();
    }

    /**
     * 取消发布文章
     * @param ids
     * @return
     */
    @SysLog("取消发布文章")
    @PostMapping("/cancelPublish")
    @RequiresPermissions("xry:article:cancelPublish")
    public Result cancelPublish(@RequestBody Long[] ids){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("status",CANCEL_PUBLISH);
        xryArticleService.updateArticleStatus(params);
        return Result.ok();
    }

    /**
     * 推荐文章
     * @param ids
     * @return
     */
    @SysLog("推荐文章")
    @PostMapping("/recommendArticle")
    @RequiresPermissions("xry:article:recommendArticle")
    public Result recommendArticle(@RequestBody Long[] ids){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("recommend",RECOMMEND_ARTICLE);
        xryArticleService.updateArticleRecommend(params);
        return Result.ok();
    }

    /**
     * 取消推荐文章
     * @param ids
     * @return
     */
    @SysLog("取消推荐文章")
    @PostMapping("/cancelRecommend")
    @RequiresPermissions("xry:article:cancelRecommend")
    public Result cancelRecommend(@RequestBody Long[] ids){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("recommend",CANCEL_RECOMMEND);
        xryArticleService.updateArticleRecommend(params);
        return Result.ok();
    }
    
}
