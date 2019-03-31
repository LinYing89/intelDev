package com.bairock.iot.intelDev.order;

/**
 * 与服务器传输指令格式
 * @author 44489
 *
 */
public class JsonOrder {

	// 指令类型
	private OrderType orderType;
	// 指令体, 根据指令类型不同而不同
	private Object data;
	//用户名
	private String username;
	//组名
	private String devGroupName;
	
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDevGroupName() {
		return devGroupName;
	}
	public void setDevGroupName(String devGroupName) {
		this.devGroupName = devGroupName;
	}
	
	
}
