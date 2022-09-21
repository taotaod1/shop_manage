package com.bt.service;

import com.bt.vo.DayStatis;

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
}
