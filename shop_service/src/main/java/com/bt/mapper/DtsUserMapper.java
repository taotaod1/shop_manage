package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsUser;
import com.bt.vo.DayStatis;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsUserMapper extends BaseMapper<DtsUser> {
    List<DayStatis> selectByDayStatis(int statisDaysRang);
}
