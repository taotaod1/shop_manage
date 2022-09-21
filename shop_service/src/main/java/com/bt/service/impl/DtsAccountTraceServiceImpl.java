package com.bt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsUserMapper;
import com.bt.pojo.DtsAccountTrace;
import com.bt.service.DtsAccountTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 19:39
 **/
@Service
public class DtsAccountTraceServiceImpl extends ServiceImpl<BaseMapper<DtsAccountTrace>,DtsAccountTrace> implements DtsAccountTraceService {
    @Autowired
    private DtsUserMapper userMapper;
    @Override
    public IPage<DtsAccountTrace> findDtsAccountTraceByPage(Page<DtsAccountTrace> page, String sort, String order, String username, String mobile, Integer[] statusArray) {
        return getBaseMapper().selectPage(page,new QueryWrapper<DtsAccountTrace>().like(StringUtils.isEmpty(mobile)?false:true,"mobile" ,mobile ).in(statusArray!=null?true:false,"status",statusArray).orderByDesc(sort!=null?true:false,sort));
    }

    @Override
    public boolean updateById(DtsAccountTrace entity) {
        return super.updateById(entity);
    }
}
