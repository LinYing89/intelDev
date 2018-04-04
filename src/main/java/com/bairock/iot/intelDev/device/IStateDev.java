package com.bairock.iot.intelDev.device;

/**
 * state category device
 * @author LinQiang
 *
 */
public interface IStateDev{

	/**
	 * turn on device
	 */
	void turnOn();
	
	/**
	 * turn off device
	 */
	void turnOff();
	
	/**
	 * get order of turn on device
	 * @return
	 */
	String getTurnOnOrder();
	
	/**
	 * get order of turn off device
	 * @return
	 */
	String getTurnOffOrder();
	
	/**
	 * get device state identify
	 * @return
	 */
	String getDevStateId();
	
	/**
	 * get device state code
	 * @return
	 */
	String getDevState();
	
	CtrlModel getCtrlModel();
}
