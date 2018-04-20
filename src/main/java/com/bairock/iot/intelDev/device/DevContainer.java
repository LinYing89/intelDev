package com.bairock.iot.intelDev.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevContainer")
public class DevContainer extends DevHaveChild {

	public DevContainer() {
		this("","");
	}

	public DevContainer(String mcId, String sc) {
		super(mcId, sc);
	}

}
