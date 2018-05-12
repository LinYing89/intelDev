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
	public void handleSingleMsg(String state) {
		if (null != state) {
			if (state.length() < 2) {
				return;
			}

			String msgId = state.substring(0, 1);
			float srcValue = 0;
			try {
				String strState = state.substring(1);
				int iHexValue = Integer.parseInt(strState, 16);
				srcValue = (float) iHexValue;
				// if (getCollectProperty().getCollectSrc() != CollectSignalSource.SWITCH) {
				// srcValue = srcValue * 0.01f;
				// }
			} catch (Exception e) {
				return;
			}

			CollectProperty cp = getCollectProperty();
			try {
				switch (getCollectProperty().getCollectSrc()) {
				case ELECTRIC_CURRENT:
					float simulator = (srcValue) / 4095f * (20);
					cp.setSimulatorValue(IntelDevHelper.scale(simulator));
					simulatorHandler(srcValue, cp, msgId, simulator);
					break;
				case VOLTAGE:
					float simulator1 = (srcValue) / 4095f * (10);
					cp.setSimulatorValue(IntelDevHelper.scale(simulator1));
					simulatorHandler(srcValue, cp, msgId, simulator1);
					break;
				case DIGIT:
					digitHandler(srcValue, cp, msgId);
					break;
				case SWITCH:
					switchHandler(srcValue, cp, msgId);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void simulatorHandler(float srcValue, CollectProperty cp, String msgId, float simulator) {

		switch (msgId) {
		case "8":
			// f(srcValue) = least + (srcValue - 0) / (0x3fff - 0) * (crest - least))
			// float simulator = cp.getLeastValue() + (srcValue) / 16383f *
			// (cp.getCrestValue() - cp.getLeastValue());
			// cp.setSimulatorValue(IntelDevHelper.scale(simulator));

			// float simulator = (srcValue) / 4095f * (cp.getCrestValue());
			// cp.setSimulatorValue(IntelDevHelper.scale(simulator));

			// f(A) = Aa + (A - a) / (b - a) * (Ab - Aa)
			// a is min voltage, b is max voltage
			// Aa is min voltage refer used value
			// Ab is max voltage refer used value
			if (cp.getCrestValue() - cp.getLeastValue() == 0) {
				return;
			}

			float currentValue = cp.getLeastReferValue() + (simulator - cp.getLeastValue())
					/ (cp.getCrestValue() - cp.getLeastValue()) * (cp.getCrestReferValue() - cp.getLeastReferValue());

			if (cp.getCollectSrc() == CollectSignalSource.VOLTAGE) {
				currentValue += 1;
			}
			currentValue = IntelDevHelper.scale(currentValue);


			//float percent = computePercentByCurrentValue(currentValue, cp);
//			if (cp.getUnitSymbol().equals("%")) {
//				cp.setCurrentValue(IntelDevHelper.scale(percent));
//			}else {
//				cp.setPercent(IntelDevHelper.scale(percent));
//				cp.setCurrentValue(currentValue);
//			}
			cp.setCurrentValue(currentValue);
			break;
		case "p":
			cp.setPercent(srcValue);
			setRatio();
			break;
		}
	}

	private void digitHandler(float srcValue, CollectProperty cp, String msgId) {
		cp.setSimulatorValue(srcValue);
		switch (msgId) {
		case "8":
			if (cp.getCrestValue() - cp.getLeastValue() == 0) {
				return;
			}
			// percent = (A - Aa) * 100 / (Ab - Aa)
			float percent = computePercentByCurrentValue(srcValue, cp);
			cp.setPercent(IntelDevHelper.scale(percent));
			cp.setCurrentValue(IntelDevHelper.scale(srcValue));
			break;
		case "p":
			cp.setPercent(srcValue);
			setRatio();
			break;
		}
	}

	private void switchHandler(float srcValue, CollectProperty cp, String msgId) {
		cp.setSimulatorValue(srcValue);
		switch (msgId) {
		case "8":
			if (srcValue == 0f) {
				cp.setCurrentValue(1f);
			} else {
				cp.setCurrentValue(0f);
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

	// percent = (A - Aa) * 100 / (Ab - Aa)
	private float computePercentByCurrentValue(float currentValue, CollectProperty cp) {
		if (cp.getCrestValue() - cp.getLeastValue() == 0) {
			return 0;
		}
		float percent = (currentValue - cp.getLeastReferValue()) * 100
				/ (cp.getCrestReferValue() - cp.getLeastReferValue());
		return percent;
	}
}
