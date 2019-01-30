package com.bairock.iot.intelDev.order;

/**
 * 自定义指令格式
 * @author 44489
 *
 */
public class OrderBase {

	//指令类型
	private OrderType orderType;
	//指令体
	private String data;
	private String username;
	private String devGroupName;
	
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
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
