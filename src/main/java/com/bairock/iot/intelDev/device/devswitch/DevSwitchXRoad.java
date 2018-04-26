package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevStateHelper;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitchXRoad")
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
	
	@Override
	protected void handle7(String[] msgs) {
		if(msgs.length != 4) {
			return;
		}
		
		int subCode = Integer.parseInt(msgs[1] + msgs[2], 16);
		String strState = msgs[3].equals("0") ? "1" : "0";
		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf(subCode));
		if(null == sd1) {
			return;
		}
		DevStateHelper.getIns().setDsId(sd1, strState);
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
