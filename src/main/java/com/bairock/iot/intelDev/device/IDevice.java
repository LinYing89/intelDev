package com.bairock.iot.intelDev.device;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDevice {

	/**
	 * get super parent
	 * if the device have no parent, return this;
	 * if the device have parent, and the parent also have parent, 
	 * return parent's parent till the root parent
	 * @return
	 */
	@JsonIgnore
	Device findSuperParent();
	
	/**
	 * get main code and sub code
	 * if device is main device,return main code + sub code of main device
	 * then if device is sub device, return main code + sub code of main device + 
	 * "_" + main code + sub code of sub device
	 * @return main code + cub code, like B10001 of main device or B10001_101 of sub device
	 */
	@JsonIgnore
	String getCoding();
	
	void turnOn();
	
	void turnOff();
	/**
	 * turn device gear to on
	 */
	void turnGearKai();
	
	/**
	 * turn device gear to off
	 */
	void turnGearGuan();
	
	/**
	 * turn device gear to auto
	 */
	void turnGearAuto();
	
	/**
	 * get device order by device control code identity
	 * @param dctId device control code identity
	 * @return order of device
	 */
	String getDevOrder(String orderHead,String dctId);
	
	String createQueueOrder();
	String createTurnLocalModelOrder(String ip, int port);
	String createTurnRemoteModelOrder(String ip, int port);
	String createAbnormalOrder();
	String createInitOrder();
	/**
	 * get device state
	 * @return
	 */
	@JsonIgnore
	String getDevState();
	
	/**
	 * if the state is kai
	 * @return
	 */
	@JsonIgnore
	boolean isKaiState();
	
	/**
	 * is the device is normal
	 * @return
	 */
	@JsonIgnore
	boolean isNormal();
	
	/**
	 * handle state
	 * @param state
	 * @return
	 */
	boolean handle(String state);
}

