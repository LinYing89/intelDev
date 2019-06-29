package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.XRoadDevice;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitchXRoad")
public class DevSwitchXRoad extends DevSwitch implements XRoadDevice{

	public DevSwitchXRoad() {
		this("", "");
	}

	public DevSwitchXRoad(String mcId, String sc) {
		super(mcId, sc);
		for(int i = 1; i <= 16; i++) {
			SubDev sd = new SubDev("smc_w", String.valueOf(i));
			if(i <= 4) {
				sd.setVisibility(true);
			}else {
				sd.setVisibility(false);
			}
			addChildDev(sd);
		}
	}
	
	/**
	 * 根据指定路数, 重新构建子设备, 添加或删除, 原有的不变
	 * @param roadNumber 路数
	 */
	@Override
	public void rebuildChildren(int roadNumber) {
	    int oldSize = getListDev().size();
	    if(roadNumber == oldSize) {
	        return;
	    }
	    
	    if(roadNumber > oldSize) {
	        for(int i = oldSize + 1; i <= roadNumber; i++) {
	            SubDev sd = new SubDev("smc_w", String.valueOf(i));
	            sd.setVisibility(true);
	            addChildDev(sd);
	        }
	    }else {
	        for(int i = roadNumber + 1; i <= oldSize; i++) {
	            Device dev = getSubDevBySc(String.valueOf(i));
	            removeChildDev(dev);
	        }
	    }
	}
	
//	@Override
//	protected void handle7(String[] msgs) {
//		if(msgs.length != 4) {
//			return;
//		}
//		
//		int subCode = Integer.parseInt(msgs[1] + msgs[2], 16);
//		String strState = msgs[3].equals("0") ? "1" : "0";
//		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf(subCode));
//		if(null == sd1) {
//			return;
//		}
//		DevStateHelper.getIns().setDsId(sd1, strState);
//	}
	
//	@Override
//	public String createStateStr() {
//		int state = 0;
//		for(int i = 1; i <= 16; i++) {
//			Device sunDev = getSubDevBySc(String.valueOf(i));
//			if (!sunDev.isKaiState()) {
//				state |= 1;
//				state <<= 2;
//			}
//		}
//		return Integer.toHexString(state);
//	}

}
