package com.bt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.mapper.DtsCategoryMapper;
import com.bt.pojo.DtsCategory;
import com.bt.service.DtsCategoryService;
import com.bt.vo.CatVo;
import com.bt.vo.CategorySellAmts;
import com.bt.vo.CategorySellVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<CatVo> findCatVos() {

        List<DtsCategory> DtsCategorys = dtsCategoryMapper.selectList(new QueryWrapper<DtsCategory>().select("id","name","pid","level"));
//        先获得一级分类
        Map<Integer,CatVo> catVos = DtsCategorys.stream().filter(dtsCategory -> {
            if (dtsCategory.getLevel().equals("L1")){
                return true;
            }else {
                return false;
            }
        }).map(dtsCategory -> {
            CatVo catVo = new CatVo();
            catVo.setValue(dtsCategory.getId());
            catVo.setLabel(dtsCategory.getName());
            catVo.setChildren(new ArrayList<CatVo>());
            return catVo;
        }).collect(Collectors.toMap(CatVo::getValue, catVo -> catVo));
//        设置二级分类
        DtsCategorys.stream().forEach(dtsCategory -> {
            if (catVos.containsKey(dtsCategory.getPid())) {
                CatVo catVo = new CatVo();
                catVo.setValue(dtsCategory.getId());
                catVo.setLabel(dtsCategory.getName());
                catVos.get(dtsCategory.getPid()).getChildren().add(catVo);
            }
        });
        return  catVos.values().stream().collect(Collectors.toList());

    }

    @Override
    public IPage<DtsCategory> findCategoryByPage(Integer page, Integer limit, Integer id, String name, String sort, String order) {
        return dtsCategoryMapper.selectPage(new Page<DtsCategory>(page,limit),new QueryWrapper<DtsCategory>().eq(id!=null,"id",id).like(name!=null,"name",name).orderBy(!StringUtils.isEmpty(sort),"asc".equalsIgnoreCase(order),sort));
    }

    @Override
    public List<CatVo> findL1CatVos() {
        return dtsCategoryMapper.selectList(new QueryWrapper<DtsCategory>().eq("level","L1")).stream().map(dtsCategory -> {
            CatVo catVo = new CatVo();
            catVo.setValue(dtsCategory.getId());
            catVo.setLabel(dtsCategory.getName());
            return catVo;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateDtsCategory(DtsCategory dtsCategory) {
        dtsCategory.setUpdateTime(new Date());
        dtsCategoryMapper.updateById(dtsCategory);
    }

    @Override
    public void createDtsCategory(DtsCategory dtsCategory) {
        dtsCategory.setUpdateTime(new Date());
        dtsCategory.setAddTime(new Date());
        dtsCategoryMapper.insert(dtsCategory);
    }

    @Override
    public void deleteDtsCategory(DtsCategory dtsCategory) {
        dtsCategoryMapper.deleteById(dtsCategory);
    }
}
