package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;

/**
 * two state one road switch
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitchOneRoad")
public class DevSwitchOneRoad extends DevSwitch {

	/**
	 * 
	 */
	public DevSwitchOneRoad(){
		this("","");
	}
	
	/**
	 * 
	 * @param mcId main code identify
	 * @param sc sub code
	 */
	public DevSwitchOneRoad(String mcId, String sc){
		super(mcId, sc);
		addChildDev(new SubDev("smc_w", "2"));
	}
	
	protected void handle7(String[] msgs) {
		if(msgs.length != 3) {
			return;
		}
		int iHexState = Integer.parseInt(msgs[2], 16);
		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf("2"));
		String strState = getEnsureState(iHexState, 1);
		DevStateHelper.getIns().setDsId(sd1, strState);
	}
	
	@Override
	public String createStateStr() {
		int state = 0;
		Device subDev = getListDev().get(0);
		if (!subDev.isKaiState()) {
			state |= 1;
			state <<= 1;
		}
		return String.valueOf(state);
	}
}
