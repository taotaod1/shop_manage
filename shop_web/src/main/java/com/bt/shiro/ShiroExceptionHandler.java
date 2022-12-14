package com.bt.shiro;

import com.bt.util.ResponseUtil;
import com.bt.util.ResponseUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * shiro权限异常处理
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)  //大量处理器的时候  order：值越小 执行优先级越高
public class ShiroExceptionHandler {

	/**
	 * 501, "请登录"
	 */
	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public Object unauthenticatedHandler(AuthenticationException e) {
		e.printStackTrace();
		return ResponseUtil.unlogin();
	}

	/**
	 * 506, "无操作权限"
	 */
	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public Object unauthorizedHandler(AuthorizationException e) {
		e.printStackTrace();
		return ResponseUtil.unauthz();
	}

}
