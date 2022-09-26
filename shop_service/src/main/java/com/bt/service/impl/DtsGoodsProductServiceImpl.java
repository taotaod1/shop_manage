package com.bt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsGoodsProductMapper;
import com.bt.pojo.DtsGoodsProduct;
import com.bt.service.DtsGoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 14:01
 **/
@Service
public class DtsGoodsProductServiceImpl  extends ServiceImpl<BaseMapper<DtsGoodsProduct>, DtsGoodsProduct> implements DtsGoodsProductService {
    @Autowired
    private DtsGoodsProductMapper dtsGoodsProductMapper;
    @Override
    public long findDtsGoodsProductCount() {
        return getBaseMapper().selectCount(null);
    }

    @Override
    public List<DtsGoodsProduct> findDtsGoodsProductByGoodsId(Integer id) {
        return dtsGoodsProductMapper.selectList(new QueryWrapper<DtsGoodsProduct>().eq(id!=null?true:false,"goods_id",id));
    }

    @Override
    public void updateGoodsProduct(List<DtsGoodsProduct> products, Integer id) {
        products.stream().forEach(product -> {
            if(product.getAddTime()==null){
                product.setAddTime(new Date());
            }
            product.setGoodsId(id);
            product.setUpdateTime(new Date());
        });
        dtsGoodsProductMapper.delete(new QueryWrapper<DtsGoodsProduct>().eq("goods_id",id));
        dtsGoodsProductMapper.insertBatch(products);
    }
    @Override
    public void insertGoodsProduct(List<DtsGoodsProduct> products) {
        dtsGoodsProductMapper.insertBatch(products);
    }

    @Override
    public void deleteGoodsProductByGoodsId(Integer id) {
        dtsGoodsProductMapper.delete(new QueryWrapper<DtsGoodsProduct>().eq("goods_id",id));
    }
}
