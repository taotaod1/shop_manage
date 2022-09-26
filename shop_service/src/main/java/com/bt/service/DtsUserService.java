package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.pojo.DtsUser;
import com.bt.pojo.DtsUserAccount;
import com.bt.vo.DayStatis;
import com.bt.vo.UserDayVo;

import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/20 19:36
 **/
public interface DtsUserService {

    IPage<DtsUser> findDtsUserByPage(Page<DtsUser> objectPage, String sort, String order, String username, String mobile);

    Integer updateDtsUserByUserId(DtsUser dtsUser, String settlementRate);

    DtsUser findDtsUserById(Integer userId);

    DtsUserAccount findDtsUserAccountByUserId(Integer id);

    long findDtsUserCount();

    List<DayStatis> findUserOrderCntVo(int statisDaysRang);

    List<UserDayVo> findUserDayVos();
}
