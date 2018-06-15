package com.ltkj.template.utility;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {
	private String resultCode;
	private String errorCode = StringUtils.EMPTY;
	private String errorMessage = StringUtils.EMPTY;
	private String resultMessage;
	private int total;
	private T result;

	public RestResponse() {
	}

	public RestResponse(String resultCode) {
		this.resultCode = resultCode;
	}

	public RestResponse(String resultCode, T result, int total) {
		this.result = result;
		this.total = total;
	}

	public RestResponse(String resultCode, T result) {
		this.resultCode = resultCode;
		this.result = result;
	}

	public RestResponse(String resultCode, String resultMessage, T result) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.result = result;
	}

	public RestResponse(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public RestResponse(String resultCode, String errorCode, String errorMessage) {
		this.resultCode = resultCode;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
