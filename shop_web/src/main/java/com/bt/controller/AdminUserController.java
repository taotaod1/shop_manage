package com.bt.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.annotation.RequiresPermissionsDesc;
import com.bt.pojo.DtsUser;
import com.bt.pojo.DtsUserAccount;
import com.bt.service.DtsUserService;
import com.bt.type.UserTypeEnum;
import com.bt.util.JacksonUtil;
import com.bt.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/20 19:28
 **/
@Slf4j
@RestController
@RequestMapping("/admin/user")
@CrossOrigin
public class AdminUserController {
    @Autowired
     private DtsUserService dtsUserServiceImpl;
    @RequiresPermissionsDesc(menu = {"用户管理", "会员管理"}, button = "查询")
    @RequiresPermissions("admin:user:list")
    @GetMapping("/list")
    public Object list(Integer page,Integer limit,String sort,String order,String username,String mobile){
//        检验
        if(page==null||page<1){
            page=1;
        }
        if (limit==null||limit<1){
            limit=20;
        }
        IPage<DtsUser> iPage=dtsUserServiceImpl.findDtsUserByPage(new Page<DtsUser>(page,limit),sort,order,username,mobile);
        Map map=new HashMap<>();
        map.put("total",iPage.getTotal());
        map.put("items",iPage.getRecords());
        return ResponseUtil.ok(map);
    }

    /**
     *
     * @param
     * @return
     */

    @PostMapping  ("/approveAgency")
    public Object approveAgency(@RequestBody String body){
        Integer userId = JacksonUtil.parseInteger(body, "userId");
        String settlementRate = JacksonUtil.parseString(body, "settlementRate");
//       检验是否为空
        if(userId==null){
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(settlementRate)){
            return ResponseUtil.badArgument();
        }
        if(Integer.parseInt(settlementRate)<0||Integer.parseInt(settlementRate)>100){
            return ResponseUtil.badArgument();
        }
//        查询会员是否存在
        DtsUser dtsUser=dtsUserServiceImpl.findDtsUserById(userId);
        if (dtsUser==null){
            return ResponseUtil.badArgument();
        }
        dtsUser.setUserLevel(Integer.valueOf(UserTypeEnum.REGIONAL_AGENCY.getLevel()));
        dtsUser.setUpdateTime(new Date());
        dtsUser.setStatus(0);
         Integer i= dtsUserServiceImpl.updateDtsUserByUserId(dtsUser,settlementRate);
       if(i>0) {
           return ResponseUtil.ok();
       }else {
           return ResponseUtil.fail();
       }
    }
    /**
     * 会员推广代理详情
     * http://localhost:8083/admin/user/detailApprove?id=71
     */
    @GetMapping("/detailApprove")
    public Object detailApprove(Integer id){
        if(id==null){
            return ResponseUtil.badArgument();
        }
        DtsUserAccount dtsUserAccount=dtsUserServiceImpl.findDtsUserAccountByUserId(id);

        return ResponseUtil.ok(dtsUserAccount);
    }

}
