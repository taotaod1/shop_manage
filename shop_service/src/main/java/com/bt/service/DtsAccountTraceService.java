package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.pojo.DtsAccountTrace;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 19:39
 **/
public interface DtsAccountTraceService {
    IPage<DtsAccountTrace> findDtsAccountTraceByPage(Page<DtsAccountTrace> page, String sort, String order, String username, String mobile, Integer[] statusArray);


    boolean updateById(DtsAccountTrace dtsAccountTrace);
}
