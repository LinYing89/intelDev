package com.bairock.iot.intelDev.order;

/**
 * 登录指令
 * @author 44489
 *
 */
public class LoginOrder {

	//登录组密码
	private String devGroupPassword;
	
	//登录模式, local本地登录, remote远程登录
	private String loginModel;

	public String getDevGroupPassword() {
		return devGroupPassword;
	}

	public void setDevGroupPassword(String devGroupPassword) {
		this.devGroupPassword = devGroupPassword;
	}

	public String getLoginModel() {
		return loginModel;
	}

	public void setLoginModel(String loginModel) {
		this.loginModel = loginModel;
	}
	
}
