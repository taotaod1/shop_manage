package com.bt.vo;

import com.bt.pojo.DtsGoods;
import com.bt.pojo.DtsGoodsAttribute;
import com.bt.pojo.DtsGoodsProduct;
import com.bt.pojo.DtsGoodsSpecification;
import lombok.Data;

@Data
public class GoodsAllinone {

	private DtsGoods goods;
	private DtsGoodsSpecification[] specifications;
	private DtsGoodsAttribute[] attributes;
	// 这里采用 Product 再转换到 DtsGoodsProduct
	private DtsGoodsProduct[] products;



}
