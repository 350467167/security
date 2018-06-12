package com.ltkj.template.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ltkj.template.exception.BusinessException;
import com.ltkj.template.exception.ErrorTokenException;
import com.ltkj.template.utility.RestGenerator;
import com.ltkj.template.utility.RestResponse;

/**
 * @author Administrator
 * 全局异常处理
 */
//配置进入controller后发生的异常拦截
@RestControllerAdvice
//如果返回的为json数据或其它对象，添加该注解  
//@ResponseBody  
public class GlobalExceptionHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler
	@ResponseStatus(HttpStatus.OK)
	public RestResponse<?> defaultErrorHandler(HttpServletRequest req, Exception e) {
		e.printStackTrace();
		System.out.println(e);
		// Bad credentials 
		if (e instanceof BadCredentialsException) {
			LOGGER.error("---------> Bad credentials!", e);
			return RestGenerator.successResult("error", "用户名或密码不正确！");
		}else if(e instanceof AccessDeniedException){
			LOGGER.error("---------> AccessDeniedException!", e);
			return RestGenerator.errorResult( "error", "权限不足，请联系管理员！");
		}
		
		return RestGenerator.errorResult(e.getClass().getSimpleName(), e.getMessage());
	}

	@ExceptionHandler(value = NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RestResponse<?> noHandlerFoundExceptionHandler(HttpServletRequest req, NoHandlerFoundException e) {
		LOGGER.error("---------> Not Found!", e);
		return RestGenerator.errorResult(e.getClass().getSimpleName(), e.getMessage());
	}

	@ExceptionHandler(value = BusinessException.class)
	@ResponseStatus(HttpStatus.OK)
	public Object customException(HttpServletRequest req, BusinessException e) {
		LOGGER.error("---------> Business Exception!", e);
		return RestGenerator.errorResult(e.getClass().getSimpleName(), e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private RestResponse<?> illegalParamsExceptionHandler(MethodArgumentNotValidException e) {
        LOGGER.error("---------> invalid request!", e);
        return RestGenerator.errorResult("error", "无效的请求！");
    }
	
	@ExceptionHandler(ErrorTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private RestResponse<?> errorTokenExceptionHandler(ErrorTokenException e) {
        LOGGER.error("---------> errorTokenExceptionHandler!", e);
        return RestGenerator.errorResult("error", "token丢失，请重新登录！");
    }
}
