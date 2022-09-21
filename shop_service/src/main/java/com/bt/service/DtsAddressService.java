package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.pojo.DtsAddress;
import com.bt.vo.AddressVo;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 21:07
 **/
public interface DtsAddressService {
    IPage<AddressVo> findDtsAddressByPage(Page<AddressVo> page, String sort, String order, String name, Integer userId);
}
