package com.bt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsPermissionMapper;
import com.bt.mapper.DtsRoleMapper;
import com.bt.pojo.DtsAdmin;
import com.bt.pojo.DtsPermission;
import com.bt.pojo.DtsRole;
import com.bt.service.DtsAdminService;
import com.bt.util.bcrypt.BCrypt;
import com.bt.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/20 16:30
 **/
@Service
public class DtsAdminServiceImpl extends ServiceImpl<BaseMapper<DtsAdmin>,DtsAdmin> implements DtsAdminService {

    @Autowired
    private DtsRoleMapper dtsRoleMapper;
    @Autowired
    private DtsPermissionMapper dtsPermissionMapper;
    @Override
    public DtsAdmin findDtsAdminByUserName(String username) {
        return getBaseMapper().selectOne(new QueryWrapper<DtsAdmin>().eq("username",username));
    }

    @Override
    public IPage<DtsAdmin> findDtsAdminByPage(Integer page, Integer limit, String sort, String order, String username) {
     if (page==null||page<1) {
         page = 1;
     }
     if (limit==null||limit<1) {
            limit = 20;
     }
        return getBaseMapper().selectPage(new Page<DtsAdmin>(page,limit),new QueryWrapper<DtsAdmin>().like(StringUtils.isEmpty(username)?false:true,"username",username).orderBy(StringUtils.isEmpty(sort)?false:true,"asc".equalsIgnoreCase(order),sort ));
    }

    @Override
    public Integer updateDtsAdmin(DtsAdmin dtsAdmin) {
        dtsAdmin.setPassword(new BCryptPasswordEncoder().encode(dtsAdmin.getPassword()));
        dtsAdmin.setUpdateTime(new Date());
        return getBaseMapper().updateById(dtsAdmin);
    }

    @Override
    public Integer insertDtsAdmin(DtsAdmin dtsAdmin) {
        dtsAdmin.setPassword(new BCryptPasswordEncoder().encode(dtsAdmin.getPassword()));
        dtsAdmin.setUpdateTime(new Date());
        dtsAdmin.setAddTime(new Date());
        return getBaseMapper().insert(dtsAdmin);
    }

    @Override
    public Integer deleteDtsAdmin(DtsAdmin dtsAdmin) {
        return getBaseMapper().deleteById(dtsAdmin);
    }

}

