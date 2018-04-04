package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * switch collect device, value is only 0 or 1
 * @author 44489
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollectSwitch")
public class DevCollectSwitch extends DevCollect {

	public DevCollectSwitch() {
		init();
	}

	public DevCollectSwitch(String mcId, String sc) {
		super(mcId, sc);
		init();
	}

	private void init() {
		getCollectProperty().setCrestValue(1f);
		getCollectProperty().setLeastValue(0f);
		getCollectProperty().setCrestReferValue(1f);
		getCollectProperty().setLeastReferValue(0f);
	}

	@Override
	public boolean handle(String state) {
		if (null != state) {
			if (state.length() < 2) {
				return false;
			}

			if (state.startsWith("8")) {
				try {
					String strState = state.substring(1);
					if(strState.equals("0")) {
						getCollectProperty().setCurrentValue(0f);
					}else {
						getCollectProperty().setCurrentValue(1f);
					}
				} catch (Exception e) {
				}
			}
		}
		return super.handle(state);
	}
}
