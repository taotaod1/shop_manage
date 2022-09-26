package com.bt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsOrderMapper;
import com.bt.pojo.DtsOrder;
import com.bt.service.DtsOrderService;
import com.bt.vo.DayStatis;
import com.bt.vo.OrderAmtsVo;
import com.bt.vo.OrderStmtVo;
import com.bt.vo.ShipChannelVo;
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

    @Override
    public IPage<DtsOrder> findDtsOrderByPage(Integer page, Integer limit, Integer[] orderStatusArray, String sort, String order, Integer userId, String orderSn) {
        return getBaseMapper().selectPage(new Page<DtsOrder>(page,limit),new QueryWrapper<DtsOrder>().eq(userId!=null,"user_id",userId).eq(orderSn!=null,"order_sn",orderSn).in(orderStatusArray!=null,"order_status",orderStatusArray).orderByDesc(sort!=null,sort));
    }

    @Override
    public List<ShipChannelVo> findShipChannelVos() {
        return null;
    }

    @Override
    public DtsOrder findDtsOrderById(Integer oid) {

        return getBaseMapper().selectById(oid);
    }

    @Override
    public List<OrderStmtVo> findOrderStmtVos() {
        return orderMapper.findOrderStmtVos();
    }
}
