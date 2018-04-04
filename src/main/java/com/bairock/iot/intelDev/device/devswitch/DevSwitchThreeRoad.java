package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitchThreeRoad")
public class DevSwitchThreeRoad extends DevSwitch {

	public DevSwitchThreeRoad() {
		this("", "");
	}

	public DevSwitchThreeRoad(String mcId, String sc) {
		super(mcId, sc);
		SubDev sd1 = new SubDev("smc_w", "1");
//		sd1.setSortIndex(1);
		SubDev sd2 = new SubDev("smc_w", "2");
//		sd2.setSortIndex(2);
		SubDev sd3 = new SubDev("smc_w", "3");
//		sd3.setSortIndex(3);
		addChildDev(sd1);
		addChildDev(sd2);
		addChildDev(sd3);
	}

}
