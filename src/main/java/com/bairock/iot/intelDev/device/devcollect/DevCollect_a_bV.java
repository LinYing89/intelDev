package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollect_a_bV")
public class DevCollect_a_bV extends DevCollectSimulate{

	public DevCollect_a_bV() {
		init();
	}

	public DevCollect_a_bV(String mcId, String sc) {
		super(mcId, sc);
		init();
	}

	private void init() {
		getCollectProperty().setCrestValue(5f);
		getCollectProperty().setLeastValue(0f);
		getCollectProperty().setCrestReferValue(100f);
		getCollectProperty().setLeastReferValue(0f);
	}
}
