package com.bt.vo;

import com.bt.pojo.DtsGoods;
import com.bt.pojo.DtsGoodsAttribute;
import com.bt.pojo.DtsGoodsProduct;
import com.bt.pojo.DtsGoodsSpecification;
import lombok.Data;

import java.util.List;

@Data
public class GoodsAllinone {
	private List<Integer> categoryIds;
	private DtsGoods goods;
	private List<DtsGoodsSpecification> specifications;
	private List<DtsGoodsAttribute> attributes;
	// 这里采用 Product 再转换到 DtsGoodsProduct
	private List<DtsGoodsProduct> products;
}
