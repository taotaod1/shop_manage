package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.pojo.DtsRole;
import com.bt.vo.CatVo;

import java.util.List;
import java.util.Set;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 8:49
 **/
public interface DtsRoleService {
    Set<String> findRoleNameByIds(Integer[] roleIds);


    List<CatVo> findeCatVos();

    IPage<DtsRole> findByPage(Integer page, Integer limit, String sort, String order, String rolename);

    Integer updateRole(DtsRole dtsRole);

    Integer createRole(DtsRole dtsRole);
}
