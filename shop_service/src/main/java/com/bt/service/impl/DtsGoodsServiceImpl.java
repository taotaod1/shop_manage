package com.bt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.*;
import com.bt.pojo.DtsGoods;
import com.bt.pojo.DtsGoodsAttribute;
import com.bt.pojo.DtsGoodsProduct;
import com.bt.pojo.DtsGoodsSpecification;
import com.bt.service.*;
import com.bt.vo.GoodsAllinone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 13:58
 **/
@Service
public class DtsGoodsServiceImpl extends ServiceImpl<BaseMapper<DtsGoods>, DtsGoods> implements DtsGoodsService {
    @Autowired
    private DtsGoodsMapper dtsGoodsMapper;
    @Autowired
    private DtsCategoryMapper dtsCategoryMapper;
    @Autowired
    private DtsCategoryService dtsCategoryServiceImpl;
    @Autowired
    private DtsGoodsProductService dtsGoodsProductService;
    @Autowired
    private DtsGoodsAttributeService dtsGoodsAttributeService;
    @Autowired
    private DtsGoodsSpecificationService dtsGoodsSpecificationService;

    @Override
    public long findDtsGoodsCount() {
       return getBaseMapper().selectCount(null);
    }

    @Override
    public IPage<DtsGoods> findDTsGoodsByPage(Integer page, Integer limit, String goodsSn, String name, String sort, String order) {
        if (page==null||page<1){
            page=1;
        }
        if (limit==null||limit<1){
            limit=10;
        }
       return  getBaseMapper().selectPage(new Page<DtsGoods>(page,limit),new QueryWrapper<DtsGoods>().eq(!StringUtils.isEmpty(goodsSn),"goods_sn",goodsSn).like(!StringUtils.isEmpty(name),"name",name).orderBy(!StringUtils.isEmpty(sort),"asc".equalsIgnoreCase(order),sort));
    }

    @Override
    public List<Integer> findCategoryIdsById(Integer id) {
        List<Integer> categoryIds=new ArrayList<>();
        Integer categoryid=dtsGoodsMapper.selectCategoryIdById(id);
        Integer parentId=dtsCategoryMapper.selectParentIdById(categoryid);
        categoryIds.add(parentId);
        categoryIds.add(categoryid);
        return categoryIds;
    }

    @Override
    public DtsGoods findDtsGoodById(Integer id) {
        return getBaseMapper().selectOne(new QueryWrapper<DtsGoods>().eq("id",id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public void updateGoods(GoodsAllinone goodsAllinone) {
        DtsGoods goods = goodsAllinone.getGoods();
        List<DtsGoodsAttribute> attributes = goodsAllinone.getAttributes();
        List<DtsGoodsSpecification> specifications = goodsAllinone.getSpecifications();
        List<DtsGoodsProduct> products = goodsAllinone.getProducts();
//        检验参数
        if (goods.getId()!=null){
            throw new RuntimeException("商品id必须为空");
        }
        if (getBaseMapper().selectOne(new QueryWrapper<DtsGoods>().eq("goods_sn",goods.getGoodsSn()))!=null){
            throw new RuntimeException("商品编号已存在");
        }
        if (StringUtils.isEmpty(goods.getName())){
            throw new RuntimeException("商品名称不能为空");
        }
        attributes.forEach(attribute -> {
            if (attribute.getAddTime()==null){
                attribute.setAddTime(new Date());
            }
            attribute.setGoodsId(goods.getId());
            attribute.setUpdateTime(new Date());
        });
        specifications.stream().forEach(specification -> {
            if (specification.getAddTime() == null) {
                specification.setAddTime(new Date());
            }
            specification.setGoodsId(goods.getId());
            specification.setUpdateTime(new Date());
        });
        products.stream().forEach(product -> {
            if (product.getAddTime() == null) {
                product.setAddTime(new Date());
            }
            product.setGoodsId(goods.getId());
            product.setUpdateTime(new Date());
        });
        //更新商品信息
        getBaseMapper().updateById(goods);
        //更新商品属性
        dtsGoodsAttributeService.updateGoodsAttribute(attributes,goods.getId());
        //更新商品规格
        dtsGoodsSpecificationService.updateGoodsSpecification(specifications,goods.getId());
          //更新商品货品
        dtsGoodsProductService.updateGoodsProduct(products,goods.getId());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public void createGoods(GoodsAllinone goodsAllinone) {
        DtsGoods goods = goodsAllinone.getGoods();
        List<DtsGoodsAttribute> attributes = goodsAllinone.getAttributes();
        List<Integer> categoryIds = goodsAllinone.getCategoryIds();
        List<DtsGoodsSpecification> specifications = goodsAllinone.getSpecifications();
        List<DtsGoodsProduct> products = goodsAllinone.getProducts();
//        检验参数
        if (goods.getGoodsSn()==null){
            throw new RuntimeException("商品id不能为空");

        }
        if (getBaseMapper().selectOne(new QueryWrapper<DtsGoods>().eq("goods_sn",goods.getGoodsSn()))!=null){
            throw new RuntimeException("商品编号已存在");

        }
        if (StringUtils.isEmpty(goods.getName())){
            throw new RuntimeException("商品名称不能为空");
        }
        if (attributes==null||attributes.size()==0){
            throw new RuntimeException("商品参数不能为空");
        }
        if (specifications==null||specifications.size()==0){
            throw new RuntimeException("商品规格不能为空");
        }
      if (products==null||products.size()==0){
            throw new RuntimeException("商品货品不能为空");
        }
        goods.setAddTime(new Date());
        goods.setUpdateTime(new Date());
        getBaseMapper().insert(goods);
        attributes.forEach(attribute -> {
            attribute.setAddTime(new Date());
            attribute.setGoodsId(Integer.valueOf(goods.getId()));
            attribute.setUpdateTime(new Date());
        });
        specifications.stream().forEach(specification -> {
            if (specification.getAddTime() == null) {
                specification.setAddTime(new Date());
            }
            specification.setGoodsId(Integer.valueOf(goods.getId()));
            specification.setUpdateTime(new Date());
        });
        products.stream().forEach(product -> {
            if (product.getAddTime() == null) {
                product.setAddTime(new Date());
            }
            product.setGoodsId(Integer.valueOf(goods.getId()));
            product.setUpdateTime(new Date());
        });
        //更新商品属性
        dtsGoodsAttributeService.insertGoodsAttribute(attributes);
        //更新商品规格
        dtsGoodsSpecificationService.insertGoodsSpecification(specifications);
        //更新商品货品
        dtsGoodsProductService.insertGoodsProduct(products);
    }

    @Override
    public void deleteGoodsById(Integer id) {
        getBaseMapper().deleteById(id);
        dtsGoodsAttributeService.deleteGoodsAttributeByGoodsId(id);
        dtsGoodsSpecificationService.deleteGoodsSpecificationByGoodsId(id);
        dtsGoodsProductService.deleteGoodsProductByGoodsId(id);
    }
}
