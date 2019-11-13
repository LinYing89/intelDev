package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevContainer;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.device.XRoadDevice;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollectSignalContainer")
public class DevCollectSignalContainer extends DevContainer implements XRoadDevice{

	public DevCollectSignalContainer() {
		this("", "");
	}

	public DevCollectSignalContainer(String mcId, String sc) {
		super(mcId, sc);
		initChildDevices();
	}
	
	protected void initChildDevices() {
		for (int i = 1; i <= 16; i++) {
			DevCollectSignal sd = (DevCollectSignal) DeviceAssistent.createDeviceByMcId(MainCodeHelper.COLLECTOR_SIGNAL,
					String.valueOf(i));
			sd.setName(this.getCoding() + "_" + sd.getCoding());
			addChildDev(sd);
		}
	}

	@Override
	public String createQueryOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}

//	@Override
//	public void removeChildDev(Device device) {
//		return;
//	}
	
	@Override
    public void rebuildChildren(int roadNumber) {
        int oldSize = getListDev().size();
        if(roadNumber == oldSize) {
            return;
        }
        
        if(roadNumber > oldSize) {
            for(int i = oldSize + 1; i <= roadNumber; i++) {
                DevCollectSignal sd = (DevCollectSignal) DeviceAssistent.createDeviceByMcId(MainCodeHelper.COLLECTOR_SIGNAL,
                        String.valueOf(i));
                sd.setName(this.getCoding() + "_" + sd.getCoding());
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

	@Override
	public void handleSingleMsg(String state) {
		if (null == state || state.isEmpty()) {
			return;
		}
		analysisMsgUnit(state);
	}

	/**
	 * 
	 * @param sc
	 *            sub code
	 * @return
	 */
	public Device findSubDevBySc(String sc) {
		for (Device dev : getListDev()) {
			if (dev.getSubCode().equals(sc)) {
				return dev;
			}
		}
		return null;
	}

	protected void analysisMsgUnit(String msgUnit) {
		if (null == msgUnit || msgUnit.isEmpty()) {
			return;
		}

		if (msgUnit.startsWith("8")) {
			String msgNoHead = msgUnit.substring(1);
			String[] msgs = msgNoHead.split(",");
			int subCode = 0;
			String value = "";
			for (int i = 0; i < msgs.length; i++) {
				subCode++;
				value = msgs[i];
				Device dev = findSubDevBySc(String.valueOf(subCode));
				if (dev != null) {
					dev.setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
					dev.handleSingleMsg("8" + value);
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private void analysisMonitorValue(String msgUnit) {
		// msgs[0] is message sign
		char[] cMsgs = msgUnit.toCharArray();
		String[] msgs = new String[cMsgs.length];
		for (int i = 0; i < cMsgs.length; i++) {
			msgs[i] = String.valueOf(cMsgs[i]);
		}
		switch (msgs[0]) {
		case "8":
			if (msgs.length < 5) {
				return;
			}
			int subCode = 0;
			String value = "";
			for (int i = 1; i < msgs.length; i += 4) {
				subCode++;
				value = msgs[i] + msgs[i + 1] + msgs[i + 2] + msgs[i + 3];
				Device dev = findSubDevBySc(String.valueOf(subCode));
				if (dev != null) {
					dev.setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
					dev.handleSingleMsg("8" + value);
				}
			}
			break;
		}
	}

}
