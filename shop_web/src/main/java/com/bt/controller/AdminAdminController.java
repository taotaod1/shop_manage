package com.bt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.annotation.RequiresPermissionsDesc;
import com.bt.pojo.DtsAdmin;
import com.bt.service.DtsAdminService;
import com.bt.util.ResponseUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/22 9:41
 **/
@RestController
@CrossOrigin
@RequestMapping("/admin/admin")
public class AdminAdminController {
    @Autowired
    private DtsAdminService dtsAdminServiceImpl;

    /**
     * 接口地址 :http://localhost:8080/admin/admin/list?page=1&limit=20&username=11&sort=add_time&order=desc
     * GET请求
     *
     */
    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer page,Integer limit,String sort,String order,String username){
        IPage<DtsAdmin> Ipage=dtsAdminServiceImpl.findDtsAdminByPage(page,limit,sort,order,username);
        Map data=new HashMap<>();
        data.put("total",Ipage.getTotal());
        data.put("items",Ipage.getRecords());
        return ResponseUtil.ok(data);
    }
    /**
     * http://localhost:8080/admin/admin/update
     * post
     */
    @RequiresPermissions("admin:admin:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody DtsAdmin dtsAdmin){
        Integer  i=  dtsAdminServiceImpl.updateDtsAdmin(dtsAdmin);
        if (i>0) {
            return ResponseUtil.ok();
        }else  return ResponseUtil.fail();
    }
    /**
     * http://localhost:8080/admin/admin/create
     * post
     */
    @PostMapping("/create")
    @RequiresPermissions("admin:admin:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员"}, button = "添加")
    public Object create(@RequestBody DtsAdmin dtsAdmin){
        Integer  i=  dtsAdminServiceImpl.insertDtsAdmin(dtsAdmin);
        if (i>0) {
            return ResponseUtil.ok(dtsAdmin);
        }else  return ResponseUtil.fail();
    }
    /**
     * http://localhost:8080/admin/admin/delete
     * post
     */
    @PostMapping("/delete")
    @RequiresPermissions("admin:admin:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员"}, button = "删除")
    public Object delete(@RequestBody DtsAdmin dtsAdmin){
        Integer  i=  dtsAdminServiceImpl.deleteDtsAdmin(dtsAdmin);
        if (i>0) {
            return ResponseUtil.ok();
        }else  return ResponseUtil.fail();
    }


}
