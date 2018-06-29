package com.bairock.iot.intelDev.device.alarm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 可燃气体
 * @author 44489
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevGas")
public class DevGas extends DevAlarm {

	public DevGas() {
		this("","");
	}

	public DevGas(String mcId, String sc) {
		super(mcId, sc);
	}

}
