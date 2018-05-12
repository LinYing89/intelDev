package com.bairock.iot.intelDev.device.devswitch;

import com.bairock.iot.intelDev.device.DevStateHelper;

public class DevSocket extends DevSwitch {
	/**
	 * 
	 */
	public DevSocket(){
		this("","");
	}
	
	/**
	 * 
	 * @param mcId main code identify
	 * @param sc sub code
	 */
	public DevSocket(String mcId, String sc){
		super(mcId, sc);
		addChildDev(new SubDev("smc_w", "1"));
	}
	
	@Override
	protected void handle7(String[] msgs) {
		if(msgs.length != 3) {
			return;
		}
		byte bHexState = Byte.parseByte(msgs[2], 16);
		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf("1"));
		String strState = getEnsureState(bHexState, 0);
		DevStateHelper.getIns().setDsId(sd1, strState);
	}
}
