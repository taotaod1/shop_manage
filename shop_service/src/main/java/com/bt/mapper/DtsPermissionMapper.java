package com.bt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bt.pojo.DtsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author zhaojian
 * @since 2021-05-12
 */
public interface DtsPermissionMapper extends BaseMapper<DtsPermission> {

    Integer insetBatch(@Param("permissionss") List<DtsPermission> permissionss,@Param("roleId") Integer roleId);

    Integer deleteBatchByRoleIdAndPermission(@Param("detDtsPermission") List<DtsPermission> detDtsPermission,@Param("roleId") Integer roleId);
}
