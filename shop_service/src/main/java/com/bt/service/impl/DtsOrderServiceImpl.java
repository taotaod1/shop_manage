package com.bt.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsOrderMapper;
import com.bt.pojo.DtsOrder;
import com.bt.service.DtsOrderService;
import com.bt.vo.DayStatis;
import com.bt.vo.OrderAmtsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 14:02
 **/
@Service
public class DtsOrderServiceImpl extends ServiceImpl<BaseMapper<DtsOrder>,DtsOrder> implements DtsOrderService {
    @Autowired
    private DtsOrderMapper orderMapper;
    @Override
    public long findDtsOrderCount() {
        return getBaseMapper().selectCount(null);
    }

    @Override
    public List<DayStatis> findOrderAmtsVo(int statisDaysRang) {
        List<DayStatis> dayStatisList=orderMapper.findDayStatis(statisDaysRang);

        return dayStatisList;
    }
}
