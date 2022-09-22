package com.bt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.pojo.DtsRole;
import com.bt.service.DtsRoleService;
import com.bt.vo.CatVo;
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

    @Override
    public List<CatVo> findeCatVos() {
        List<DtsRole> dtsRoles = getBaseMapper().selectList(new QueryWrapper<DtsRole>().select("id", "name"));
       return  dtsRoles.stream().map(dtsRole ->{
            CatVo catVo = new CatVo();
            catVo.setLabel(dtsRole.getName());
            catVo.setValue(dtsRole.getId());
            return catVo;
          }).collect(Collectors.toList());
    }

    @Override
    public IPage<DtsRole> findByPage(Integer page, Integer limit, String sort, String order, String rolename) {
        if (page==null||page<1){
            page=1;
        }
        if (limit==null||limit<1){
            limit=20;
        }
         return getBaseMapper().selectPage(new Page<DtsRole>(page,limit),new QueryWrapper<DtsRole>().like(!StringUtils.isEmpty(rolename), "name",rolename).orderBy(!StringUtils.isEmpty(sort), "asc".equals(order), sort));
    }

    @Override
    public Integer updateRole(DtsRole dtsRole) {
        return getBaseMapper().updateById(dtsRole);
    }

    @Override
    public Integer createRole(DtsRole dtsRole) {
        return getBaseMapper().insert(dtsRole);
    }
}
