package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsGoodsAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品参数表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsGoodsAttributeMapper extends BaseMapper<DtsGoodsAttribute> {

    void insertBatch(@Param("attributes") List<DtsGoodsAttribute> attributes);
}
