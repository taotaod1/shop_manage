package com.bt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.annotation.RequiresPermissionsDesc;
import com.bt.pojo.DtsPermission;
import com.bt.pojo.DtsRole;
import com.bt.service.DtsPermissionService;
import com.bt.service.DtsRoleService;
import com.bt.util.JacksonUtil;
import com.bt.util.Permission;
import com.bt.util.PermissionUtil;
import com.bt.util.ResponseUtil;
import com.bt.vo.CatVo;
import com.bt.vo.PermVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/22 10:42
 **/
@RestController
@CrossOrigin
@RequestMapping("/admin/role")
public class AdminRoleController {
    @Autowired
    private DtsRoleService dtsRoleServiceImpl;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private DtsPermissionService dtsPermissionServiceImpl;
    /**
     * 接口地址 : http://localhost:8083/admin/role/options
     * GET请求
     */
    @RequiresPermissions("admin:role:options")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "查询2")
    @GetMapping("/options")
    public Object options(){
        List<CatVo> catVos=dtsRoleServiceImpl.findeCatVos();
        return ResponseUtil.ok(catVos);
    }
    /**
     * http://localhost:8080/admin/role/list?page=1&limit=20&sort=add_time&order=desc&rolename=11
     * GET请求
     */
    @RequiresPermissions("admin:role:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer page,Integer limit,String sort,String order,String rolename){
        IPage<DtsRole> IPage=dtsRoleServiceImpl.findByPage(page,limit,sort,order,rolename);
        Map map=new HashMap<>();
        map.put("total",IPage.getTotal());
        map.put("items",IPage.getRecords());
        return ResponseUtil.ok(map);
    }
    /**
     * http://localhost:8080/admin/role/permissions?roleId=1
     * get
     */
    @RequiresPermissions("admin:role:getpermissions")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "查询权限")
    @GetMapping("/permissions")
    public Object permissions(Integer roleId){
        Set<String> permissionByIds = dtsPermissionServiceImpl.findPermissionByIds(new Integer[]{roleId});
        List<Permission> permissions = PermissionUtil.listPermission(context, "com.bt.controller");
        List<PermVo> permVos = PermissionUtil.listPermVo(permissions);
        if (permissionByIds.contains("*")){
            permissionByIds=permissions.stream().map(Permission->{
                return Permission.getRequiresPermissions().value()[0];
            }).collect(Collectors.toSet());
        }
        Map map=new HashMap<>();
        map.put("systemPermissions",permVos);
        map.put("assignedPermissions",permissionByIds);
        return ResponseUtil.ok(map);
    }
    /**
     * http://localhost:8080/admin/role/update
     * post
     */
    @RequiresPermissions("admin:role:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody DtsRole dtsRole){
        Integer i=dtsRoleServiceImpl.updateRole(dtsRole);
        if (i>0){
            return ResponseUtil.ok(dtsRole);
        }else {
            return ResponseUtil.fail();
        }
    }
    /**
     * http://localhost:8080/admin/role/create
     * post
     */
    @RequiresPermissions("admin:role:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody DtsRole dtsRole){
        Integer i=dtsRoleServiceImpl.createRole(dtsRole);
        if (i>0){
            return ResponseUtil.ok(dtsRole);
        }else {
            return ResponseUtil.fail();
        }
    }
    /**
     * http://localhost:8080/admin/role/permissions
     * post
     */
    @RequiresPermissions("admin:role:permissions")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "授权")
    @PostMapping("/permissions")
    public Object permissions(@RequestBody String json) {
        Integer roleId = JacksonUtil.parseInteger(json, "roleId");
        List<String> permissions = JacksonUtil.parseStringList(json, "permissions");
        if (roleId == null) {
            return ResponseUtil.badArgument();
        }
        Set<String> permissionhave = dtsPermissionServiceImpl.findPermissionByIds(new Integer[]{roleId});
        List<DtsPermission> addDtsPermissions = permissions.stream().filter(permission -> {
            if (permissionhave.contains(permission)) {
                permissionhave.remove(permission);
                return false;
            } else {
                return true;
            }
        }).map(permission -> {
            DtsPermission dtsPermission = new DtsPermission();
            dtsPermission.setRoleId(roleId);
            dtsPermission.setPermission(permission);
            dtsPermission.setAddTime(new Date());
            dtsPermission.setUpdateTime(new Date());
            return dtsPermission;
        }).collect(Collectors.toList());
        List<DtsPermission> detDtsPermission = null;
        detDtsPermission = permissionhave.stream().map(permission -> {
                DtsPermission dtsPermission = new DtsPermission();
                dtsPermission.setRoleId(roleId);
                dtsPermission.setPermission(permission);
                return dtsPermission;
            }).collect(Collectors.toList());
         Integer i = dtsPermissionServiceImpl.saveBatchPerms(addDtsPermissions, detDtsPermission,roleId);
        if (i > 0) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail();
        }
    }

}
