package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.pojo.DtsGoods;
import com.bt.vo.GoodsAllinone;

import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 13:58
 **/
public interface DtsGoodsService {
    long findDtsGoodsCount();

    IPage<DtsGoods> findDTsGoodsByPage(Integer page, Integer limit, String goodsSn, String name, String sort, String order);


    List<Integer> findCategoryIdsById(Integer id);

    DtsGoods findDtsGoodById(Integer id);

    void updateGoods(GoodsAllinone goodsAllinone);

    void createGoods(GoodsAllinone goodsAllinone);

    void deleteGoodsById(Integer id);
}
