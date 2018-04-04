package com.bairock.iot.intelDev.device.devcollect;

import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.VirTualDevice;

/**
 * counter device
 * @author 44489
 *
 */
public class Counter extends DevCollect implements VirTualDevice {

	public Counter() {
		this(MainCodeHelper.SMC_WU, "");
	}

	public Counter(String mcId, String sc) {
		super(mcId, sc);
	}
	
	/**
	 * and 1 number
	 */
	public void and1() {
		Float currentValue = getCollectProperty().getCurrentValue();
		if(null == currentValue) {
			getCollectProperty().setCurrentValue(1f);
		}else {
			getCollectProperty().setCurrentValue(currentValue + 1);
		}
	}
	
	/**
	 * minus 1 number
	 */
	public void minus1() {
		Float currentValue = getCollectProperty().getCurrentValue();
		if(null == currentValue) {
			getCollectProperty().setCurrentValue(-1f);
		}else {
			getCollectProperty().setCurrentValue(currentValue - 1);
		}
	}

}
