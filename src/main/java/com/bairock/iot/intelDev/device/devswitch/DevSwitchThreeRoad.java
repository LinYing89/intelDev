package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevStateHelper;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitchThreeRoad")
public class DevSwitchThreeRoad extends DevSwitch {

	public DevSwitchThreeRoad() {
		this("", "");
	}

	public DevSwitchThreeRoad(String mcId, String sc) {
		super(mcId, sc);
		SubDev sd1 = new SubDev("smc_w", "1");
//		sd1.setSortIndex(1);
		SubDev sd2 = new SubDev("smc_w", "2");
//		sd2.setSortIndex(2);
		SubDev sd3 = new SubDev("smc_w", "3");
//		sd3.setSortIndex(3);
		addChildDev(sd1);
		addChildDev(sd2);
		addChildDev(sd3);
	}

//	@Override
//	protected void handle7(String[] msgs) {
//		if(msgs.length != 3) {
//			return;
//		}
//		byte iHexState = Byte.parseByte(msgs[2], 16);
//		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf("1"));
//		String strState = getEnsureState(iHexState, 0);
//		DevStateHelper.getIns().setDsId(sd1, strState);
//		
//		SubDev sd2 = (SubDev) getSubDevBySc(String.valueOf("2"));
//		strState = getEnsureState(iHexState, 1);
//		DevStateHelper.getIns().setDsId(sd2, strState);
//		
//		SubDev sd3 = (SubDev) getSubDevBySc(String.valueOf("3"));
//		strState = getEnsureState(iHexState, 2);
//		DevStateHelper.getIns().setDsId(sd3, strState);
//	}
	
	@Override
	protected void handle8(String[] msgs) {
		if(msgs.length < 2) {
			return;
		}
		byte iHexState = Byte.parseByte(msgs[1], 16);
		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf("1"));
		String strState = getEnsureState(iHexState, 3);
		DevStateHelper.getIns().setDsId(sd1, strState);
		
		SubDev sd2 = (SubDev) getSubDevBySc(String.valueOf("2"));
		strState = getEnsureState(iHexState, 2);
		DevStateHelper.getIns().setDsId(sd2, strState);
		
		SubDev sd3 = (SubDev) getSubDevBySc(String.valueOf("3"));
		strState = getEnsureState(iHexState, 1);
		DevStateHelper.getIns().setDsId(sd3, strState);
	}
	
}
