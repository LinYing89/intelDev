package com.bairock.iot.intelDev.device;

/**
 * 
 * @author LinQiang
 *
 */
public interface IThreeStateDev extends IStateDev {

	/**
	 * stop the device
	 */
	void turnStop();
	
	/**
	 * 
	 * @return
	 */
	String getStopOrder();
}
