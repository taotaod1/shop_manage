package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bt.pojo.DtsAddress;
import com.bt.vo.AddressVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 收货地址表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsAddressMapper extends BaseMapper<DtsAddress> {

    IPage<AddressVo> selectAddressVoByPage(Page<AddressVo> page, @Param("sort") String sort,@Param("order") String order,@Param("name") String name,@Param("userId") Integer userId);
}
