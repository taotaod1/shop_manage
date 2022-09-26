package com.bt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.annotation.RequiresPermissionsDesc;
import com.bt.pojo.DtsStorage;
import com.bt.service.DtsStorageService;
import com.bt.util.ResponseUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/22 14:58
 **/
@RestController
@CrossOrigin
@RequestMapping("/admin/storage")
@MultipartConfig
public class StorageController {
    @Autowired
    private DtsStorageService dtsStorageServiceImpl;
    /**
     * http://localhost:8080/admin/storage/create
     * 上传图片
     */

    @PostMapping("/create")
    public Object create(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        DtsStorage dtsStorage=dtsStorageServiceImpl.uploadImg(inputStream,filename,size);
        return ResponseUtil.ok(dtsStorage);
    }
    /**
     * http://localhost:8080/admin/storage/list?page=1&limit=20&key=22&name=11&sort=add_time&order=desc
     * get
     */
    @RequiresPermissions("admin:storage:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer page,Integer limit,String sort,String order,String key,String name){
        IPage<DtsStorage> Ipage=dtsStorageServiceImpl.findDtsStorageByPage(page,limit,sort,order,key,name);
        Map data=new HashMap();
        data.put("total",Ipage.getTotal());
        data.put("items",Ipage.getRecords());
        return ResponseUtil.ok(data);
    }
    /**
     * http://localhost:8080/admin/storage/update
     * post
     */
    @RequiresPermissions("admin:storage:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody DtsStorage dtsStorage){
        dtsStorageServiceImpl.updateDtsStorage(dtsStorage);
        return ResponseUtil.ok(dtsStorage);
    }
    /**
     * http://localhost:8080/admin/storage/delete
     * post
     */
    @RequiresPermissions("admin:storage:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody DtsStorage dtsStorage){
        dtsStorageServiceImpl.deleteDtsStorage(dtsStorage);
        return ResponseUtil.ok();
    }
}
