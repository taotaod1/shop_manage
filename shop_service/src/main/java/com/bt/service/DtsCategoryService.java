package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.pojo.DtsCategory;
import com.bt.vo.CatVo;
import com.bt.vo.CategorySellVo;

import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 15:37
 **/
public interface DtsCategoryService {
    CategorySellVo findCategorySellVo();

    List<CatVo> findCatVos();

    IPage<DtsCategory> findCategoryByPage(Integer page, Integer limit, Integer id, String name, String sort, String order);

    List<CatVo> findL1CatVos();

    void updateDtsCategory(DtsCategory dtsCategory);

    void createDtsCategory(DtsCategory dtsCategory);

    void deleteDtsCategory(DtsCategory dtsCategory);
}
