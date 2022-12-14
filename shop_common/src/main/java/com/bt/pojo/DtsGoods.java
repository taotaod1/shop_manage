package com.bt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bt.handler.JsonStringArrayTypeHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品基本信息表
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DtsGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品编号
     */
    private String goodsSn;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品所属类目ID
     */
    private Integer categoryId;

    private Integer brandId;

    /**
     * 商品宣传图片列表，采用JSON数组格式
     */
    @TableField(typeHandler = JsonStringArrayTypeHandler.class)
    private String[] gallery;

    /**
     * 商品关键字，采用逗号间隔
     */
    private String keywords;

    /**
     * 商品简介
     */
    private String brief;

    /**
     * 是否上架
     */
    private Boolean isOnSale;

    private Integer sortOrder;

    /**
     * 商品页面商品图片
     */
    @JsonDeserialize(using = com.bt.util.JsonStringArrayDeserializer.class)
    private String picUrl;

    /**
     * 商品分享朋友圈图片
     */
    private String shareUrl;

    /**
     * 是否新品首发，如果设置则可以在新品首发页面展示
     */
    private Boolean isNew;

    /**
     * 是否人气推荐，如果设置则可以在人气推荐页面展示
     */
    private Boolean isHot;

    /**
     * 商品单位，例如件、盒
     */
    private String unit;

    /**
     * 专柜价格
     */
    private BigDecimal counterPrice;

    /**
     * 零售价格
     */
    private BigDecimal retailPrice;

    /**
     * 商品详细介绍，是富文本格式
     */
    private String detail;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 浏览量
     */
    private Integer browse;

    /**
     * 已销售总量
     */
    private Integer sales;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 供货单位
     */
    private String commpany;

    /**
     * 批发价格
     */
    private BigDecimal wholesalePrice;

    /**
     * 审批状态 ：4 未提交  0 待审批  1 审批通过 2 审批拒绝
     */
    private Integer approveStatus;

    /**
     * 审批内容
     */
    private String approveMsg;

    /**
     * 佣金类型 ： 0 无推广佣金  1 自定义佣金 2 代理审批比例
     */
    private Integer brokerageType;

    /**
     * 推广佣金金额
     */
    private BigDecimal brokeragePrice;


}
