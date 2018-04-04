package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * simulate collect device
 * @author 44489
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollectSimulate")
public class DevCollectSimulate extends DevCollect {

	public DevCollectSimulate() {
		// TODO Auto-generated constructor stub
	}

	public DevCollectSimulate(String mcId, String sc) {
		super(mcId, sc);
		// TODO Auto-generated constructor stub
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
					
					CollectProperty cp = getCollectProperty();
					
					//f(A) = Aa + (A - a) / (b - a) * (Ab - Aa)
					// a is min voltage, b is max voltage
					//Aa is min voltage refer used value
					//Ab is max voltage refer used value
					float currentValue = cp.getLeastReferValue() 
							+ (srcValue - cp.getLeastValue()) / (cp.getCrestValue() - cp.getLeastValue()) 
							* (cp.getCrestReferValue() - cp.getLeastReferValue());
					currentValue = IntelDevHelper.scale(currentValue);
					getCollectProperty().setCurrentValue(currentValue);
					
					float percent = currentValue * 100 / cp.getCrestReferValue();
					cp.setPercent(IntelDevHelper.scale(percent));
				}catch(Exception e) {
					
				}
				getCollectProperty().setPercent(Float.valueOf(strState));
			}
		}
		return super.handle(state);
	}

}
