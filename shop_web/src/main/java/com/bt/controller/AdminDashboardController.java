package com.bt.controller;

import com.bt.pojo.DtsCategory;
import com.bt.service.*;
import com.bt.util.ResponseUtil;
import com.bt.vo.CategorySellVo;
import com.bt.vo.DayStatis;
import com.bt.vo.OrderAmtsVo;
import com.bt.vo.UserOrderCntVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 13:52
 **/
@RestController
@CrossOrigin
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    @Autowired
    private DtsGoodsService dtsGoodsServiceImpl;
    @Autowired
    private DtsUserService dtsUserServiceImpl;
    @Autowired
    private DtsGoodsProductService dtsGoodsProductServiceImpl;
    @Autowired
    private DtsOrderService dtsOrderServiceImpl;
    @Autowired
    private DtsCategoryService dtsCategoryServiceImpl;

    private static final int STATIS_DAYS_RANG = 30;// 统计的天数范围，一个月数据
    /**
     * - 首页统计用户数, 商品数, 库存货品数,订单数地址 : http://localhost:8083/admin/dashboard
     * - GET请求
     * - 下行数据 :
     * - goodsTotal : 商品数量, userTotal : 用户数量 , productTotal : 货品数量 , orderTotal : 订单数量
     * {
     *     "errno":0,
     *     "data":{
     *         "goodsTotal":426,
     *         "userTotal":197,
     *         "productTotal":8939,
     *         "orderTotal":107
     *     },
     *     "errmsg":"成功"
     * }
     */
    @GetMapping
    public Object dashboard(){
        long goodsTotal=dtsGoodsServiceImpl.findDtsGoodsCount();
        long userTotal=dtsUserServiceImpl.findDtsUserCount();
        long productTotal=dtsGoodsProductServiceImpl.findDtsGoodsProductCount();
        long orderTotal=dtsOrderServiceImpl.findDtsOrderCount();
        Map data=new HashMap<>();
        data.put("goodsTotal",goodsTotal);
        data.put("userTotal",userTotal);
        data.put("productTotal",productTotal);
        data.put("orderTotal",orderTotal);
        return ResponseUtil.ok(data);
    }
    /**
     * - 首页数据可视化图表地址 : http://localhost:8083/admin/dashboard/chart
     *
     * - GET请求
     * categorySell : 商品分类  和对应分类的销售额
     * orderAmts : 日期和对应的订单总额, 订单笔数
     * userOrderCnt : 日期对应的用户数和订单数
     *
     */
    @GetMapping("/chart")
    public Object chart(){
        CategorySellVo categorySellVo=dtsCategoryServiceImpl.findCategorySellVo();
        List<DayStatis> dayStatisByorder=dtsOrderServiceImpl.findOrderAmtsVo(STATIS_DAYS_RANG);
        OrderAmtsVo orderAmtsVo=new OrderAmtsVo();
        orderAmtsVo.setDayData(dayStatisByorder.stream().map(DayStatis::getDayStr).toArray(String[]::new));
        orderAmtsVo.setOrderCntData(dayStatisByorder.stream().map(DayStatis::getCnts).toArray(Integer[]::new));
        orderAmtsVo.setOrderAmtData(dayStatisByorder.stream().map(DayStatis::getAmts).toArray(BigDecimal[]::new));
        List<DayStatis> dayStatisByUser=dtsUserServiceImpl.findUserOrderCntVo(STATIS_DAYS_RANG);
        UserOrderCntVo userOrderCntVo=MergeUserOrderCntVO(dayStatisByorder,dayStatisByUser);
        Map data=new HashMap<>();
        data.put("categorySell",categorySellVo);
        data.put("orderAmts",orderAmtsVo);
        data.put("userOrderCnt",userOrderCntVo);

        return ResponseUtil.ok(data);
    }

    private UserOrderCntVo MergeUserOrderCntVO(List<DayStatis> dayStatisByorder, List<DayStatis> dayStatisByUser) {
//        获取所有天数的集合
        Set<String> day=new HashSet();
        dayStatisByorder.stream().forEach(dayStatis->day.add(dayStatis.getDayStr()));
        dayStatisByUser.stream().forEach(dayStatis->day.add(dayStatis.getDayStr()));
        List<String> dayList=new ArrayList(day);
//        重新排序
        Collections.sort(dayList);
//        遍历集合 将不存在的数据补0
        Map<String, Integer> ordermap = dayStatisByorder.stream().collect(Collectors.toMap(DayStatis::getDayStr, DayStatis::getCnts));
        Map<String, Integer> usermap = dayStatisByUser.stream().collect(Collectors.toMap(DayStatis::getDayStr, DayStatis::getCnts));
        List<Integer> orderCnts=new ArrayList<>();
        List<Integer> userCnts=new ArrayList<>();
         dayList.stream().forEach(daystr->{
             if (ordermap.containsKey(daystr)){
                 orderCnts.add(ordermap.get(daystr));
             }else {
                 orderCnts.add(0);
             }
             if (usermap.containsKey(daystr)){
                 userCnts.add(usermap.get(daystr));
             }else {
                 userCnts.add(0);
             }
         });
        UserOrderCntVo userOrderCntVo=new UserOrderCntVo();
        userOrderCntVo.setOrderCnt( orderCnts.toArray(new Integer[orderCnts.size()]));
        userOrderCntVo.setUserCnt( userCnts.toArray(new Integer[userCnts.size()]));
        userOrderCntVo.setDayData( dayList.toArray(new String[dayList.size()]));
        return userOrderCntVo;
    }
}
