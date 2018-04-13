package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * signal collector
 * 
 * @author 44489
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollectSignal")
public class DevCollectSignal extends DevCollect {

	public DevCollectSignal() {
		// TODO Auto-generated constructor stub
	}

	public DevCollectSignal(String mcId, String sc) {
		super(mcId, sc);
		// TODO Auto-generated constructor stub
	}

	public String createCalibrationOrder(float calibrationValue) {
		calibrationValue = IntelDevHelper.scale(calibrationValue);
		calibrationValue = calibrationValue * 100f;
		return OrderHelper.getOrderMsg(getCoding() + OrderHelper.SEPARATOR + "c" + calibrationValue);
	}

	@Override
	public boolean handle(String state) {
		super.handle(state);
		if (null != state) {
			if (state.length() < 2) {
				return false;
			}

			String msgId = state.substring(0, 1);
			String strState = state.substring(1);
			float srcValue = 0;
			try {
				srcValue = Float.valueOf(strState);
				if (getCollectProperty().getCollectSrc() != CollectSignalSource.SWITCH) {
					srcValue = srcValue * 0.01f;
				}
			} catch (Exception e) {
				return false;
			}

			CollectProperty cp = getCollectProperty();
			try {
				switch (getCollectProperty().getCollectSrc()) {
				case ELECTRIC_CURRENT:
				case VOLTAGE:
					simulatorHandler(srcValue, cp, msgId);
					break;
				case DIGIT:
					digitHandler(srcValue, cp, msgId);
					break;
				case SWITCH:
					switchHandler(srcValue, cp, msgId);
					break;
				}
			} catch (Exception e) {

			}
		}
		return true;
	}
	
	private void simulatorHandler(float srcValue, CollectProperty cp, String msgId) {
		switch (msgId) {
		case "8":
			// f(A) = Aa + (A - a) / (b - a) * (Ab - Aa)
			// a is min voltage, b is max voltage
			// Aa is min voltage refer used value
			// Ab is max voltage refer used value
			float currentValue = cp.getLeastReferValue()
					+ (srcValue - cp.getLeastValue()) / (cp.getCrestValue() - cp.getLeastValue())
							* (cp.getCrestReferValue() - cp.getLeastReferValue());
			currentValue = IntelDevHelper.scale(currentValue);

			float percent = currentValue * 100 / cp.getCrestReferValue();
			cp.setPercent(IntelDevHelper.scale(percent));
			cp.setCurrentValue(currentValue);
			break;
		case "p":
			cp.setPercent(srcValue);
			setRatio();
			break;
		}
	}
	
	private void digitHandler(float srcValue, CollectProperty cp, String msgId) {
		switch (msgId) {
		case "8":
			float percent = srcValue * 100 / cp.getCrestReferValue();
			cp.setPercent(IntelDevHelper.scale(percent));
			cp.setCurrentValue(IntelDevHelper.scale(srcValue));
			break;
		case "p":
			float percent2 = srcValue * 100 / cp.getCrestReferValue();
			cp.setPercent(IntelDevHelper.scale(percent2));
			setRatio();
			break;
		}
	}
	
	private void switchHandler(float srcValue, CollectProperty cp, String msgId) {
		switch (msgId) {
		case "8":
			if (srcValue == 0f) {
				cp.setCurrentValue(0f);
			} else {
				cp.setCurrentValue(1f);
			}
			break;
		case "p":
			if (srcValue == 0f) {
				cp.setPercent(0f);
				cp.setCurrentValue(0f);
			} else {
				cp.setPercent(100f);
				cp.setCurrentValue(1f);
			}
			break;
		}
	}
}
