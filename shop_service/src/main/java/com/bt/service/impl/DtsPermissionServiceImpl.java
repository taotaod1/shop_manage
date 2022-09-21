package com.bt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsRoleMapper;
import com.bt.pojo.DtsPermission;
import com.bt.service.DtsPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    @Override
    public Set<String> findPermissionByIds(Integer[] roleIds) {
        List<DtsPermission> permissions = getBaseMapper().selectList(new LambdaQueryWrapper<DtsPermission>().in(DtsPermission::getRoleId, roleIds).select(DtsPermission::getPermission));

        return permissions.stream().map(DtsPermission::getPermission).collect(Collectors.toSet());
    }
}
