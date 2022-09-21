package com.bt.service;

import com.bt.pojo.DtsAdmin;
import com.bt.pojo.DtsRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/20 16:29
 **/
@Service
public interface DtsAdminService {
    DtsAdmin findDtsAdminByUserName(String username);

}
