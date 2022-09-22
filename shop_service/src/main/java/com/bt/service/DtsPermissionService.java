package com.bt.service;

import com.bt.pojo.DtsPermission;

import java.util.List;
import java.util.Set;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 8:49
 **/
public interface DtsPermissionService {
    Set<String> findPermissionByIds(Integer[] roleIds);

    Integer saveBatchPerms(List<DtsPermission> permissionss, List<DtsPermission> detDtsPermission, Integer roleId);
}
