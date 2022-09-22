package com.bt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsPermissionMapper;
import com.bt.pojo.DtsPermission;
import com.bt.service.DtsPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 8:50
 **/
@Service
public class DtsPermissionServiceImpl extends ServiceImpl<BaseMapper<DtsPermission>, DtsPermission> implements DtsPermissionService {
    @Autowired
    private DtsPermissionMapper dtsPermissionMapper;
    @Override
    public Set<String> findPermissionByIds(Integer[] roleIds) {
        List<DtsPermission> permissions = getBaseMapper().selectList(new LambdaQueryWrapper<DtsPermission>().in(DtsPermission::getRoleId, roleIds).select(DtsPermission::getPermission));

        return permissions.stream().map(DtsPermission::getPermission).collect(Collectors.toSet());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Integer saveBatchPerms(List<DtsPermission> permissionss, List<DtsPermission> detDtsPermission, Integer roleId) {
        if(detDtsPermission!=null&&detDtsPermission.size()>0){
            dtsPermissionMapper.deleteBatchByRoleIdAndPermission(detDtsPermission,roleId);
        }
        if (permissionss!=null&&permissionss.size()>0){
            dtsPermissionMapper.insetBatch(permissionss,roleId);
        }
        return 1;
    }
}
