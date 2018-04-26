package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitchTwoRoad")
public class DevSwitchTwoRoad extends DevSwitch {

	public DevSwitchTwoRoad() {
		this("", "");
	}

	public DevSwitchTwoRoad(String mcId, String sc) {
		super(mcId, sc);
		addChildDev(new SubDev("smc_w", "1"));
		addChildDev(new SubDev("smc_w", "3"));
	}

	protected void handle7(String[] msgs) {
		if(msgs.length != 3) {
			return;
		}
		int iHexState = Integer.parseInt(msgs[2], 16);
		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf("1"));
		String strState = getEnsureState(iHexState, 0);
		DevStateHelper.getIns().setDsId(sd1, strState);
		
		SubDev sd2 = (SubDev) getSubDevBySc(String.valueOf("3"));
		strState = getEnsureState(iHexState, 2);
		DevStateHelper.getIns().setDsId(sd2, strState);
	}
	
	@Override
	public String createStateStr() {
		int state = 0;
		Device subDev3 = getSubDevBySc("3");
		if (!subDev3.isKaiState()) {
			state |= 1;
			state <<= 2;
		}
		Device subDev1 = getSubDevBySc("1");
		if (!subDev1.isKaiState()) {
			state |= 1;
		}
		return String.valueOf(state);
	}
}
