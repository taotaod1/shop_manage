package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsGoodsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品货品表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsGoodsProductMapper extends BaseMapper<DtsGoodsProduct> {


    void insertBatch(@Param("products") List<DtsGoodsProduct> products);
}
