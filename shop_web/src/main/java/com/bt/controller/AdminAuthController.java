package com.bt.controller;

import com.bt.captcha.CaptchaCodeManager;
import com.bt.util.Base64;
import com.bt.util.ResponseUtil;
import com.bt.util.UUID;
import com.bt.util.VerifyCodeUtils;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
}
