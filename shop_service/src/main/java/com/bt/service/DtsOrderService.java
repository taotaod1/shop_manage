package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.pojo.DtsOrder;
import com.bt.vo.DayStatis;
import com.bt.vo.OrderStmtVo;
import com.bt.vo.ShipChannelVo;

import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 13:58
 **/
public interface DtsOrderService {
    long findDtsOrderCount();

    List<DayStatis> findOrderAmtsVo(int statisDaysRang);

    IPage<DtsOrder> findDtsOrderByPage(Integer page, Integer limit, Integer[] orderStatusArray, String sort, String order, Integer userId, String orderSn);

    List<ShipChannelVo> findShipChannelVos();

    DtsOrder findDtsOrderById(Integer oid);

    List<OrderStmtVo> findOrderStmtVos();
}
