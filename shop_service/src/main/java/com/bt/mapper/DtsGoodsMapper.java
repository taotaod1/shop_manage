package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsGoods;

/**
 * <p>
 * 商品基本信息表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsGoodsMapper extends BaseMapper<DtsGoods> {

    Integer selectCategoryIdById(Integer id);
}
