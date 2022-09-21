package com.bt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.pojo.DtsRole;
import com.bt.service.DtsRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
public class DtsRoleServiceImpl extends ServiceImpl<BaseMapper<DtsRole>, DtsRole> implements DtsRoleService {
    @Override
    public Set<String> findRoleNameByIds(Integer[] roleIds) {
        List<DtsRole> dtsRoles = getBaseMapper().selectList(new LambdaQueryWrapper<DtsRole>().in(DtsRole::getId, Arrays.asList(roleIds)).select(DtsRole::getName));
        return dtsRoles.stream().map(DtsRole::getName).collect(Collectors.toSet());
    }
}
