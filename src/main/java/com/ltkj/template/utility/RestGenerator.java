package com.ltkj.template.utility;

public class RestGenerator {
	private final static String SUCCESS_RESULT_CODE = "OK";
	private final static String ERROR_RESULT_CODE = "ERROR";

	public static <T> RestResponse<T> successResult() {
		return new RestResponse<T>(SUCCESS_RESULT_CODE);
	}

	public static <T> RestResponse<T> successResult(T data) {
		return new RestResponse<T>(SUCCESS_RESULT_CODE, data);
	}

	public static <T> RestResponse<T> successResult(String sucessMessage) {
		return new RestResponse<T>(SUCCESS_RESULT_CODE, sucessMessage);
	}

	public static <T> RestResponse<T> successResult(T data, String sucessMessage) {
		return new RestResponse<T>(SUCCESS_RESULT_CODE, sucessMessage, data);
	}

	public static <T> RestResponse<T> successResult(T data, int totoal) {
		return new RestResponse<T>(SUCCESS_RESULT_CODE, data, totoal);
	}

	public static <T> RestResponse<T> errorResult(String errorCode, String errorMessage) {
		return new RestResponse<T>(ERROR_RESULT_CODE, errorCode, errorMessage);
	}

}
