package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.Device;
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

//	public String createCalibrationOrder(float calibrationValue) {
//		calibrationValue = IntelDevHelper.scale(calibrationValue);
//		calibrationValue = calibrationValue * 100f;
//		return OrderHelper.getOrderMsg(getCoding() + OrderHelper.SEPARATOR + "c" + calibrationValue);
//	}

	@Override
	public void handleSingleMsg(String state) {
		if (null != state) {
			if (state.length() < 2) {
				return;
			}

			super.handleSingleMsg(state);
			//String msgId = state.substring(0, 1);
			int iValue = 0;
			float srcValue = 0;
			try {
				String strState = state.substring(1);
				iValue = Integer.parseInt(strState, 16);
				// srcValue = iValue / 100f;
				// int iHexValue = Integer.parseInt(strState, 16);
				// srcValue = (float) iHexValue;
			} catch (Exception e) {
				return;
			}

			CollectProperty cp = getCollectProperty();
			try {
				switch (getCollectProperty().getCollectSrc()) {
				case ELECTRIC_CURRENT:
					// float simulator = (srcValue) / 4095f * (20);
					srcValue = iValue / 100f;
					cp.setSimulatorValue(IntelDevHelper.scale(srcValue));
					simulatorHandler(cp, srcValue);
					break;
				case VOLTAGE:
					// float simulator1 = (srcValue) / 4095f * (10);
					srcValue = iValue / 100f;
					cp.setSimulatorValue(IntelDevHelper.scale(srcValue));
					simulatorHandler(cp, srcValue);
					break;
				case DIGIT:
					//srcValue = iValue / 100f;
					digitHandler(iValue, cp);
					break;
				case SWITCH:
					srcValue = iValue;
					switchHandler(srcValue, cp);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void simulatorHandler(CollectProperty cp, float simulator) {

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

		currentValue = IntelDevHelper.scale(currentValue);

		// float percent = computePercentByCurrentValue(currentValue, cp);
		// if (cp.getUnitSymbol().equals("%")) {
		// cp.setCurrentValue(IntelDevHelper.scale(percent));
		// }else {
		// cp.setPercent(IntelDevHelper.scale(percent));
		// cp.setCurrentValue(currentValue);
		// }
		cp.setCurrentValue(currentValue);
	}

	protected void digitHandler(int iValue, CollectProperty cp) {
		float srcValue = digitIValueToSrcValue(iValue);
		cp.setSimulatorValue(srcValue);
		cp.setCurrentValue(IntelDevHelper.scale(srcValue));
	}

	private void switchHandler(float srcValue, CollectProperty cp) {
		cp.setSimulatorValue(srcValue);
		if (srcValue == 0f) {
			cp.setCurrentValue(1f);
		} else {
			cp.setCurrentValue(0f);
		}
	}
	
	protected float digitIValueToSrcValue(int iValue) {
		return iValue / 100f;
	}

	// percent = (A - Aa) * 100 / (Ab - Aa)
	@SuppressWarnings("unused")
	private float computePercentByCurrentValue(float currentValue, CollectProperty cp) {
		if (cp.getCrestValue() - cp.getLeastValue() == 0) {
			return 0;
		}
		float percent = (currentValue - cp.getLeastReferValue()) * 100
				/ (cp.getCrestReferValue() - cp.getLeastReferValue());
		return percent;
	}
	
	@Override
    public int compareTo(Device o) {
        if (o == null) {
            return -1;
        }else if(this.getSortIndex() == o.getSortIndex()) {
            try {
                return Integer.parseInt(this.getSubCode()) - Integer.parseInt(o.getSubCode());
            }catch(Exception e) {
                return this.getLongCoding().compareTo(o.getLongCoding());
            }
        }else {
            return this.getSortIndex() - o.getSortIndex();
        }
    }
}
