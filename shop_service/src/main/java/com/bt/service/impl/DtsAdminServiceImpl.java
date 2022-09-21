package com.bt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsPermissionMapper;
import com.bt.mapper.DtsRoleMapper;
import com.bt.pojo.DtsAdmin;
import com.bt.pojo.DtsPermission;
import com.bt.pojo.DtsRole;
import com.bt.service.DtsAdminService;
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

}

