package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * signal collector
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
		if (null != state) {
			if (state.length() < 2) {
				return false;
			}

			if (state.startsWith("8")) {
				String strState = state.substring(1);
				try {
					float srcValue = Float.valueOf(strState);
					srcValue = srcValue * 0.01f;
					CollectProperty cp = getCollectProperty();

					switch (getCollectProperty().getCollectSrc()) {
					case ELECTRIC_CURRENT:
					case VOLTAGE:
						float currentValue = cp.getLeastReferValue()
								+ (srcValue - cp.getLeastValue()) / (cp.getCrestValue() - cp.getLeastValue())
										* (cp.getCrestReferValue() - cp.getLeastReferValue());
						currentValue = IntelDevHelper.scale(currentValue);
						cp.setCurrentValue(currentValue);

						float percent = currentValue * 100 / cp.getCrestReferValue();
						cp.setPercent(IntelDevHelper.scale(percent));
						break;
					case DIGIT:
						cp.setCurrentValue(srcValue);
						float percent2 = srcValue * 100 / cp.getCrestReferValue();
						cp.setPercent(IntelDevHelper.scale(percent2));
						break;
					case SWITCH:
						if(srcValue == 0f) {
							cp.setCurrentValue(0f);
						}else {
							cp.setCurrentValue(1f);
						}
						break;
					default:
						break;
					}
					// f(A) = Aa + (A - a) / (b - a) * (Ab - Aa)
					// a is min voltage, b is max voltage
					// Aa is min voltage refer used value
					// Ab is max voltage refer used value

				} catch (Exception e) {

				}
				//getCollectProperty().setPercent(Float.valueOf(strState));
			}
		}
		return super.handle(state);
	}
}
