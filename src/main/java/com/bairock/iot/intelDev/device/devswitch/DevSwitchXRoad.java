package com.bairock.iot.intelDev.device.devswitch;

public class DevSwitchXRoad extends DevSwitch {

	public DevSwitchXRoad() {
		this("", "");
	}

	public DevSwitchXRoad(String mcId, String sc) {
		super(mcId, sc);
		for(int i = 1; i <= 16; i++) {
			SubDev sd = new SubDev("smc_w", String.valueOf(i));
			if(i <= 4) {
				sd.setVisibility(true);
			}else {
				sd.setVisibility(false);
			}
			addChildDev(sd);
		}
	}
	
//	@Override
//	public String createStateStr() {
//		int state = 0;
//		for(int i = 1; i <= 16; i++) {
//			Device sunDev = getSubDevBySc(String.valueOf(i));
//			if (!sunDev.isKaiState()) {
//				state |= 1;
//				state <<= 2;
//			}
//		}
//		return Integer.toHexString(state);
//	}

}
