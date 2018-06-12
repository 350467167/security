package com.ltkj.template.exception;

import org.springframework.security.access.AccessDeniedException;


public class ErrorTokenException extends AccessDeniedException{

	private static final long serialVersionUID = -4121431142885420551L;

	public ErrorTokenException(String msg) {
		super(msg);
	}

}
