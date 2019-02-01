package com.bairock.iot.intelDev.order;

/**
 * 报文类型
 * @author 44489
 *
 */
public enum OrderType {

	/**
	 * 未知
	 */
	UNKNOW,
	/**
	 * 心跳, 用户信息,原h2
	 */
	HEAD_USER_INFO,
	/**
	 * 心跳, 同步(上传到服务器)设备信息
	 */
	HEAD_SYN,
	/**
	 * 心跳, 不同步设备信息
	 */
	HEAD_NOT_SYN,
	
	/**
	 * 控制设备指令
	 */
	CTRL_DEV,
	/**
	 * 设备返回指令
	 */
	DEV_FEEDBACK,
	/**
	 * 设备档位
	 */
	GEAR,
	/**
	 * 设备状态
	 */
	STATE,
	/**
	 * 设备值, 采集设备
	 */
	VALUE,
	/**
	 * 设置控制模式
	 */
	CHANGE_CTRL_MODEL
	
}
