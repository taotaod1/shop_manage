package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsGoodsSpecification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品规格表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsGoodsSpecificationMapper extends BaseMapper<DtsGoodsSpecification> {

    void insertBatch(@Param("specifications") List<DtsGoodsSpecification> specifications);
}
