package com.bt.service.impl;

import com.bt.mapper.DtsCategoryMapper;
import com.bt.service.DtsCategoryService;
import com.bt.vo.CategorySellAmts;
import com.bt.vo.CategorySellVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 15:37
 **/
@Service
public class DtsCategoryServiceImpl implements DtsCategoryService {
    @Autowired
    private DtsCategoryMapper dtsCategoryMapper;
    @Override
    public CategorySellVo findCategorySellVo() {
        List<CategorySellAmts> categorySellAmts = dtsCategoryMapper.findCategorySellAmts();
          CategorySellVo categorySellVo = new CategorySellVo();
          categorySellVo.setCategoryNames(categorySellAmts.stream().map(CategorySellAmts::getName).toArray(String[]::new));
          categorySellVo.setCategorySellData(categorySellAmts);
          return categorySellVo;
    }
}
