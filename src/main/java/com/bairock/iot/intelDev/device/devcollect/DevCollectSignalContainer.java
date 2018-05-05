package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevContainer;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollectSignalContainer")
public class DevCollectSignalContainer extends DevContainer {

	public DevCollectSignalContainer() {
		this("", "");
	}

	public DevCollectSignalContainer(String mcId, String sc) {
		super(mcId, sc);
		for (int i = 1; i <= 16; i++) {
			DevCollectSignal sd = (DevCollectSignal)DeviceAssistent.createDeviceByMcId(MainCodeHelper.COLLECTOR_SIGNAL, String.valueOf(i));
			sd.setName(this.getCoding() + "_" + sd.getCoding());
			addChildDev(sd);
		}
	}

	@Override
	public String createQueueOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}
	
	@Override
	public void removeChildDev(Device device) {
		return;
	}
	
	@Override
	public boolean handle(String state) {
		super.handle(state);
		boolean result = false;
		if (null == state || state.isEmpty()) {
			return true;
		}
		String[] msgUnits = state.split(":");
		for (String str : msgUnits) {
			analysisMsgUnit(str);
		}

		return result;
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

	private void analysisMsgUnit(String msgUnit) {
		if (null == msgUnit || msgUnit.isEmpty()) {
			return;
		}

		// msgs[0] is message sign
		char[] cMsgs = msgUnit.toCharArray();
		String[] msgs = new String[cMsgs.length];
		for (int i = 0; i < cMsgs.length; i++) {
			msgs[i] = String.valueOf(cMsgs[i]);
		}
		switch (msgs[0]) {
		case "8":
			if(msgs.length < 5) {
				return;
			}
			int subCode = 0;
			String value = "";
			for(int i=1; i< msgs.length; i+=4) {
				subCode++;
				value = msgs[i] + msgs[i + 1] + msgs[i + 2] + msgs[i + 3];
				Device dev = findSubDevBySc(String.valueOf(subCode));
				if(dev != null) {
					dev.handle("8" + value);
				}
			}
			break;
		}
	}

}
