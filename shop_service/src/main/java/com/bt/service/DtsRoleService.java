package com.bt.service;

import java.util.Set;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/21 8:49
 **/
public interface DtsRoleService {
    Set<String> findRoleNameByIds(Integer[] roleIds);


}
