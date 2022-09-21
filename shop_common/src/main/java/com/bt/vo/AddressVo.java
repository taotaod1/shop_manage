package com.bt.vo;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 21:36
 **/

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *               "area": "隆回县",
 * 				"isDefault": false,
 * 				"areaId": 2019,
 * 				"address": "湖南省隆回县xxxxxx xxx",
 * 				"province": "湖南省",
 * 				"city": "邵阳市",
 * 				"name": "廖**",
 * 				"mobile": "15973******",
 * 				"id": 47,
 * 				"cityId": 219,
 * 				"userId": 43,
 * 				"provinceId": 18
 */
@Data
public class AddressVo implements Serializable {

    private Integer id;
    /**
     * 收货人名称
     */
    private String name;

    /**
     * 用户表的用户ID
     */
    private Integer userId;

    /**
     * 行政区域表的省ID
     */
    private Integer provinceId;

    /**
     * 行政区域表的市ID
     */
    private Integer cityId;

    /**
     * 行政区域表的区县ID
     */
    private Integer areaId;

    /**
     * 具体收货地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 是否默认地址
     */
    private Boolean isDefault;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;

}
