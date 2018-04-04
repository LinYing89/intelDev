package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.CtrlCodeHelper;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.IThreeStateDev;
import com.bairock.iot.intelDev.device.OrderHelper;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("SubDevThreeState")
public class SubDevThreeState extends SubDev implements IThreeStateDev {

	public SubDevThreeState() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param mcId main code identify
	 * @param sc sub code
	 */
	public SubDevThreeState(String mcId, String sc){
		super(mcId, sc);
	}
	
	@Override
	public void turnStop() {
		setDevStateId(DevStateHelper.DS_TING);
	}

	@Override
	public String getStopOrder() {
		return getDevOrder(OrderHelper.CTRL_HEAD, CtrlCodeHelper.DCT_KAIGUAN_TING);
	}
}
