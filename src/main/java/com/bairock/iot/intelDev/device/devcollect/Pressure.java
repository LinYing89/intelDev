package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.CtrlCodeHelper;

/**
 * 
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Pressure")
public class Pressure extends DevCollect {

	/**
	 * 
	 */
	public Pressure() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param mcId
	 * @param sc
	 */
	public Pressure(String mcId, String sc) {
		super(mcId, sc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleSingleMsg(String state) {
		if (null != state) {
			if (state.length() < 2) {
				return;
			}

			super.handleSingleMsg(state);
			if (state.startsWith(CtrlCodeHelper.getIns().getDct(CtrlCodeHelper.DCT_PRESSURE_PER_VALUE))) {
				String strState = state.substring(1);
				getCollectProperty().setPercent(Float.valueOf(strState));
				setRatio();
			}
		}
	}

}
