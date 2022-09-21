package com.bt.shiro;

import com.bt.mapper.DtsAdminMapper;
import com.bt.mapper.DtsRoleMapper;
import com.bt.pojo.DtsAdmin;
import com.bt.pojo.DtsPermission;
import com.bt.pojo.DtsRole;
import com.bt.service.DtsAdminService;
import com.bt.service.DtsPermissionService;
import com.bt.service.DtsRoleService;
import com.bt.util.AdminResponseCode;
import com.bt.util.bcrypt.BCryptPasswordEncoder;
import io.swagger.models.auth.In;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/20 11:38
 **/
public class AdminAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private DtsAdminService dtsAdminServiceImpl;
    @Autowired
    private DtsRoleService dtsRoleServiceImpl;
    @Autowired
    private DtsPermissionService dtsPermissionServiceImpl;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        DtsAdmin admin = (DtsAdmin) principals.getPrimaryPrincipal();
        Integer[] roleIds = admin.getRoleIds();
        Set<String> rolenames=dtsRoleServiceImpl.findRoleNameByIds(roleIds);
        Set<String> permissions=dtsPermissionServiceImpl.findPermissionByIds(roleIds);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(rolenames);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        1 获取用户名和密码
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        String username = token1.getUsername();
        String password = String.valueOf(token1.getPassword());
//        2 查询管理员对象
        DtsAdmin dtsAdmin=dtsAdminServiceImpl.findDtsAdminByUserName(username);
//        3 判断
        if(dtsAdmin==null){
            throw  new UnknownAccountException();
        }
        if(!new BCryptPasswordEncoder().matches(password,dtsAdmin.getPassword())){
            throw  new IncorrectCredentialsException();
        }
        if (dtsAdmin.getDeleted()){
//            账号被禁用
            throw  new LockedAccountException();
        }
//        4 返回值
        AuthenticationInfo info=new SimpleAuthenticationInfo(dtsAdmin,password,getName());
        return info;
    }
}
