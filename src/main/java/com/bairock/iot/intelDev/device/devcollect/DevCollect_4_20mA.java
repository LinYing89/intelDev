package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollect_4_20mA")
public class DevCollect_4_20mA extends DevCollectSimulate {

	public DevCollect_4_20mA() {
		init();
	}

	public DevCollect_4_20mA(String mcId, String sc) {
		super(mcId, sc);
		init();
	}
	
	private void init() {
		getCollectProperty().setCrestValue(20f);
		getCollectProperty().setLeastValue(4f);
		getCollectProperty().setCrestReferValue(100f);
		getCollectProperty().setLeastReferValue(0f);
	}
}
