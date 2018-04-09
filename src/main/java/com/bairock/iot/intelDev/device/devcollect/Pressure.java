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
public class Pressure extends DevCollectSignal {

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

	private void setRatio() {
		CollectProperty cp = getCollectProperty();
		if (null == cp.getCrestValue() || null == cp.getLeastValue() || null == cp.getPercent()) {
			return;
		}
		float ratio = cp.getPercent() / 100;
		ratio = ratio * (cp.getCrestValue() - cp.getLeastValue()) + cp.getLeastValue();
		cp.setCurrentValue(ratio);
	}

	@Override
	public boolean handle(String state) {
		if (null != state) {
			if (state.length() < 2) {
				return false;
			}

			if (state.startsWith(CtrlCodeHelper.getIns().getDct(CtrlCodeHelper.DCT_PRESSURE_PER_VALUE))) {
				String strState = state.substring(1);
				getCollectProperty().setPercent(Float.valueOf(strState));
				setRatio();
			}
		}
		return super.handle(state);
	}

}
