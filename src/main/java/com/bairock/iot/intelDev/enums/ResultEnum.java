package com.bairock.iot.intelDev.enums;

public enum ResultEnum {

	UBKNOW_ERROR(-1, "未知错误"),
	SUCCESS(0, "成功"),
	USER_UPLOAD_NULL(1, "上传的用户信息对象为null"),
	ERR_USERNAME(2, "用户名错误"),
	DEVGROUP_UPLOAD_NULL(3, "上传的组信息对象为null"),
	DEVGROUP_NULL(4, "设备组不存在"),
	ERR_PASSWORD(5, "密码错误"),
	LOCAL_LOGIN_HAVED(6, "已有本地登录");
	
	private int code;
	private String message;
	
	private ResultEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
