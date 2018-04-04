package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitchThreeState")
public class DevSwitchThreeState extends DevSwitch {

	/**
	 * 
	 */
	public DevSwitchThreeState() {
		this("","");
	}

	/**
	 * 
	 * @param mcId
	 * @param sc
	 */
	public DevSwitchThreeState(String mcId, String sc) {
		super(mcId, sc);
		addChildDev(new SubDevThreeState("smc_w", "1"));
	}
	
}
