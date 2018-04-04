package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

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
