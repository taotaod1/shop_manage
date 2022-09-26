package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsOrderGoods;
import com.bt.vo.GoodsStmtVo;

import java.util.List;

/**
 * <p>
 * 订单商品表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsOrderGoodsMapper extends BaseMapper<DtsOrderGoods> {

    List<GoodsStmtVo> findStmtVos();
}
