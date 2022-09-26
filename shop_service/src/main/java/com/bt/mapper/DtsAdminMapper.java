package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsAdmin;
import com.bt.vo.AdminVo;

import java.util.List;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsAdminMapper extends BaseMapper<DtsAdmin> {

    List<AdminVo> selectAdminVo();

}
