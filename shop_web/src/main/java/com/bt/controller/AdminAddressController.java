package com.bt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.pojo.DtsAddress;
import com.bt.service.DtsAddressService;
import com.bt.util.ResponseUtil;
import com.bt.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 21:00
 **/
@RestController
@CrossOrigin
@RequestMapping("/admin/address")
public class AdminAddressController {
    @Autowired
    private DtsAddressService dtsAddressServiceImpl;
    /**
     * http://localhost:8080/admin/address/list?page=1&limit=20&name=12&userId=11&sort=add_time&order=desc
     */
    @GetMapping("/list")
    public Object list(Integer page,Integer limit,String sort,String order,String name,Integer userId){
        if (page==null||page<1){
            page=1;
        }
        if (limit==null||limit<1){
            limit=20;
        }
        Page<AddressVo> page1=new Page<>(page,limit);
        IPage<AddressVo> iPage=dtsAddressServiceImpl.findDtsAddressByPage(page1,sort,order,name,userId);
        Map data=new HashMap<>();
        data.put("total",iPage.getTotal());
        data.put("items",iPage.getRecords());
        return ResponseUtil.ok(data);
    }

}
