package com.bt.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.pojo.DtsAccountTrace;
import com.bt.service.DtsAccountTraceService;
import com.bt.service.impl.DtsAccountTraceServiceImpl;
import com.bt.util.JacksonUtil;
import com.bt.util.ResponseUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 19:30
 **/
@RestController
@RequestMapping("/admin/brokerage")
@CrossOrigin
public class AdminUserBrokerageController {
    @Autowired
    private DtsAccountTraceService dtsAccountTraceServiceImpl;
    /**
     * http://localhost:8080/admin/brokerage/list?page=1&limit=20&username=11&mobile=22&statusArray=1&sort=add_time&order=desc
     * @return
     */
    @GetMapping("/list")
    public Object list(Integer page,Integer limit,String sort,String order,String username,String mobile,Integer[] statusArray){
        if(page==null||page<1){
            page=1;
        }
        if (limit==null||limit<1){
            limit=20;
        }
        Page<DtsAccountTrace> page1=new Page<>(page,limit);
        IPage<DtsAccountTrace> Page=dtsAccountTraceServiceImpl.findDtsAccountTraceByPage(page1,sort,order,username,mobile,statusArray);
        Map data=new HashMap<>();
        data.put("total",Page.getTotal());
        data.put("traceList",Page.getRecords());
        return ResponseUtil.ok(data);
    }
    /**
     * http://localhost:8080/admin/brokerage/approve?id=14&status=2
     * 佣金审批
     * {
     * 	"errno": 0,
     * 	"errmsg": "成功"
     * }
     */
    @PostMapping("/approve")
     public Object approve(@RequestBody String body) {
        Integer id = JacksonUtil.parseInteger(body, "id");
        Integer status=JacksonUtil.parseInteger(body,"status");
        String traceMsg = JacksonUtil.parseString(body, "traceMsg");
//        检验是否为空
        if(id==null){
            return ResponseUtil.badArgument();
        }
        if(status==null){
            return ResponseUtil.badArgument();
        }
        DtsAccountTrace dtsAccountTrace=new DtsAccountTrace();
        dtsAccountTrace.setId(id);
        dtsAccountTrace.setStatus(status);
        if(!StringUtils.isEmpty(traceMsg)) {
            dtsAccountTrace.setTraceMsg(traceMsg);
        }
        dtsAccountTrace.setUpdateTime(new Date());
        boolean flage=dtsAccountTraceServiceImpl.updateById(dtsAccountTrace);
        if(flage) {
            return ResponseUtil.ok();
        }else return ResponseUtil.fail();
    }


}
