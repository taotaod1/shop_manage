package com.bt.service;

import com.bt.pojo.DtsGoodsProduct;

import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 14:01
 **/
public interface DtsGoodsProductService {
    long findDtsGoodsProductCount();

    List<DtsGoodsProduct> findDtsGoodsProductByGoodsId(Integer id);

    void updateGoodsProduct(List<DtsGoodsProduct> products, Integer id);

    void insertGoodsProduct(List<DtsGoodsProduct> products);

    void deleteGoodsProductByGoodsId(Integer id);
}
