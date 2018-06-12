package com.ltkj.template.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ltkj.template.utility.ConstMap;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Controller
public class TokenErrorController extends BasicErrorController {

	public TokenErrorController() {
		super(new DefaultErrorAttributes(), new ErrorProperties());
	}

	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		String exceptionStr = (String) body.get("exception");
		if (null != exceptionStr && !"".equals(exceptionStr)) {
			if (exceptionStr.equals(ExpiredJwtException.class.getName())) {
				body.put(ConstMap.STATUS, HttpStatus.FORBIDDEN.value());
				status = HttpStatus.FORBIDDEN;
				body.put(ConstMap.ERRORCODE, "error");
				body.put(ConstMap.ERRORMESSAGE, "权限已过期");
			} else if (exceptionStr.equals(SignatureException.class.getName())) {
				body.put(ConstMap.STATUS, HttpStatus.FORBIDDEN.value());
				status = HttpStatus.FORBIDDEN;
				body.put(ConstMap.ERRORCODE, "error");
				body.put(ConstMap.ERRORMESSAGE, "权限无效");
			}
			body.remove("timestamp");
			body.remove("error");
			body.remove("exception");
			body.remove("message");
			body.remove("path");
		}
		return new ResponseEntity<>(body, status);
	}

	@Override
	public String getErrorPath() {
		return ConstMap.PATH;
	}
}
