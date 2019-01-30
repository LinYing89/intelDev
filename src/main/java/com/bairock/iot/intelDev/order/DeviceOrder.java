package com.bairock.iot.intelDev.order;

public class DeviceOrder extends OrderBase {

	private String devId;
	//编码, 设备就是设备长编码
	private String longCoding;
	
	public DeviceOrder() {}
	
	public DeviceOrder(OrderType orderType, String devId, String longCoding, String data) {
		setOrderType(orderType);
		this.devId = devId;
		this.longCoding = longCoding;
		setData(data);
	}
	
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	public String getLongCoding() {
		return longCoding;
	}
	public void setLongCoding(String longCoding) {
		this.longCoding = longCoding;
	}
	
}
