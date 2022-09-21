package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsCategory;
import com.bt.vo.CategorySellAmts;

import java.util.List;

/**
 * <p>
 * 类目表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsCategoryMapper extends BaseMapper<DtsCategory> {

    List<CategorySellAmts> findCategorySellAmts();
}
