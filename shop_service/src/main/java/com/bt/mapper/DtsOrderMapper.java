package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsOrder;
import com.bt.vo.DayStatis;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsOrderMapper extends BaseMapper<DtsOrder> {


    List<DayStatis> findDayStatis(int statisDaysRang);
}
