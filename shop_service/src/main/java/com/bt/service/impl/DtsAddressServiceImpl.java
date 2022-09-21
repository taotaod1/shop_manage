package com.bt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsAddressMapper;
import com.bt.pojo.DtsAddress;
import com.bt.service.DtsAddressService;
import com.bt.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 21:08
 **/
@Service
public class DtsAddressServiceImpl extends ServiceImpl<BaseMapper<DtsAddress>,DtsAddress> implements DtsAddressService {
    @Autowired
    private DtsAddressMapper dtsAddressMapper;
    @Override
    public IPage<AddressVo> findDtsAddressByPage(Page<AddressVo> page, String sort, String order, String name, Integer userId) {

        return dtsAddressMapper.selectAddressVoByPage(page,sort,order,name,userId);
    }
}
