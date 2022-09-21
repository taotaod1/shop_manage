package com.bt.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.pojo.DtsGoods;
import com.bt.service.DtsGoodsService;
import org.springframework.stereotype.Service;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 13:58
 **/
@Service
public class DtsGoodsServiceImpl extends ServiceImpl<BaseMapper<DtsGoods>, DtsGoods> implements DtsGoodsService {
    @Override
    public long findDtsGoodsCount() {
       return getBaseMapper().selectCount(null);
    }
}
