package com.bt.controller;

import com.alibaba.druid.util.StringUtils;
import com.bt.captcha.CaptchaCodeManager;
import com.bt.pojo.DtsAdmin;
import com.bt.service.DtsPermissionService;
import com.bt.service.DtsRoleService;
import com.bt.shiro.AdminAuthorizingRealm;
import com.bt.util.*;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 后端管理--认证管理
 *
 * 获取验证码
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/20 11:52
 **/
@Slf4j
@RestController
@RequestMapping("/admin/auth")
@CrossOrigin
public class AdminAuthController {
    @Autowired
    private DtsRoleService dtsRoleServiceImpl;
    @Autowired
    private DtsPermissionService dtsPermissionServiceImpl;

//    获取验证码
    @GetMapping("/captchaImage")
    public Object captchaImage() throws IOException {
//        1验证码数字
        String code = VerifyCodeUtils.generateVerifyCode(4);
//        2缓存验证码的数字
        String uuid = UUID.randomUUID().toString();
        boolean isSuccess = CaptchaCodeManager.addToCache(uuid, code, null);
        if(!isSuccess){
            log.error("【登录模块】生成验证码保存失败！验证码是:{}",code);
            return ResponseUtil.fail();//-1 错误
        }
//        3将验证码的数字生成图片
        ByteOutputStream os=new ByteOutputStream();
        //宽 高 流或者二进制 验证码数字
        VerifyCodeUtils.outputImage(120,40,os,code);
//        4 将流转成二进制 使用base64编码
        String encode = Base64.encode(os.getBytes());
        Map data=new HashMap<>();
        data.put("img",encode);
        data.put("uuid",uuid);
        return ResponseUtil.ok(data);
    }
    /*
    - 登录请求地址 : http://localhost:8083/admin/auth/login
    - POST请求
    - 上行数据:
{
	"code": "66e8",
	"password": "123456",
	"username": "qianfeng",
	"uuid": "cab317c6-1bf1-436d-96ac-fda9f8d37dde"
}
- 下行数据 :
{
    "errno":0,
 	"data":"568f6dd8-97c7-450d-a00b-0a01c3b930c7", //是当前用户Session的ID
 	"errmsg":"成功"
}
方案一： AdminDTO
方案二：直接接受JSON串
            JacksonUtils 获取四个属性
    * */
    @PostMapping("/login")
    public Object login(@RequestBody String body){
//      1 接受页面传递过来的参数
        String code = JacksonUtil.parseString(body, "code");
        String password = JacksonUtil.parseString(body, "password");
        String username = JacksonUtil.parseString(body, "username");
        String uuid = JacksonUtil.parseString(body, "uuid");
        if(StringUtils.isEmpty(code)){
            return AdminResponseUtil.fail(807,"验证码不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return AdminResponseUtil.fail(808,"密码不能为空");
        }
        if(StringUtils.isEmpty(username)){
            return AdminResponseUtil.fail(809,"用户名不能为空");
        }
        if(StringUtils.isEmpty(uuid)){
            return AdminResponseUtil.fail(810,"uuid不能为空");
        }
//        2 校验验证码
        String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(uuid);
        if(StringUtils.isEmpty(cachedCaptcha)){
            return AdminResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_EXPIRED);
        }
//        3 校验验证码是否正确
//        if(!cachedCaptcha.equalsIgnoreCase(code)){
//            return AdminResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_ERROR);
//        }
//        3 用户名和密码校验
        Subject subject = SecurityUtils.getSubject();
//        密码加密 明文和密文可以直接比较
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            return AdminResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_AUTH);
        }catch (IncorrectCredentialsException e) {
            return AdminResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT_OR_PASSWORD);
        }catch (LockedAccountException e) {
            return AdminResponseUtil.fail(AdminResponseCode.ADMIN_LOCK_ACCOUNT);
        }
        return AdminResponseUtil.ok(subject.getSession().getId());
    }

    /**
    * @Author: wbt
    * @Description: - 登录后获取用户名, 角色, 权限详细信息地址 : http://localhost:8083/admin/auth/info
    * @DateTime: 17:10 2022/9/20
    * @Params:
    * @Return
    */
    @GetMapping("/info")
    public Object info(){
        Subject subject = SecurityUtils.getSubject();
        DtsAdmin admin = (DtsAdmin) subject.getPrincipal();
        Integer[] roleIds = admin.getRoleIds();
        Set<String> rolenames=dtsRoleServiceImpl.findRoleNameByIds(roleIds);
        Set<String> permission = dtsPermissionServiceImpl.findPermissionByIds(roleIds);
        Map data=new HashMap<>();
        data.put("roles",new String[]{"超级管理员"});
        data.put("name",admin.getUsername());
        data.put("perms",new String[]{"*"});
        data.put("avatar",admin.getAvatar());
        return AdminResponseUtil.ok(data);
    }
    @PostMapping("/logout")
    public Object logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return AdminResponseUtil.ok();
    }
}
