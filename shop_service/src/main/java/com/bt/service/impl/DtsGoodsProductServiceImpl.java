package com.bt.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.pojo.DtsGoodsProduct;
import com.bt.service.DtsGoodsProductService;
import org.springframework.stereotype.Service;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 14:01
 **/
@Service
public class DtsGoodsProductServiceImpl  extends ServiceImpl<BaseMapper<DtsGoodsProduct>, DtsGoodsProduct> implements DtsGoodsProductService {
    @Override
    public long findDtsGoodsProductCount() {
        return getBaseMapper().selectCount(null);
    }
}
