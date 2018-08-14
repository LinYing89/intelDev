package com.bairock.iot.intelDev.device.remoter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("RemoterContainer")
public class RemoterContainer extends DevHaveChild {

	@Transient
	@JsonIgnore
	private RemoterKey studingKey;
	
	@Transient
	@JsonIgnore
	private OnRemoterOrderSuccessListener onRemoterOrderSuccessListener;
	
	public RemoterContainer() {
		this("", "");
	}

	public RemoterContainer(String mcId, String sc) {
		super(mcId, sc);
	}

	public OnRemoterOrderSuccessListener getOnRemoterOrderSuccessListener() {
		return onRemoterOrderSuccessListener;
	}

	public void setOnRemoterOrderSuccessListener(OnRemoterOrderSuccessListener onRemoterOrderSuccessListener) {
		this.onRemoterOrderSuccessListener = onRemoterOrderSuccessListener;
	}

	public String createSubCode(String mainCode) {
		int subCode = 1;
		boolean haved = false;
		String coding = "";
		for(int i = 1; i < 100; i++) {
			haved = false;
			subCode = i;
			coding = mainCode + subCode;
			for(Device r : getListDev()) {
				if(r.getCoding().equals(coding)) {
					haved = true;
					break;
				}
			}
			if(!haved) {
				break;
			}
		}
		return String.valueOf(subCode);
	}
	
	public Remoter createRemoter(String mainCode) {
		String subCode = createSubCode(mainCode);
		Remoter r = (Remoter) DeviceAssistent.createDeviceByMc(mainCode, subCode);
		return r;
	}
	
	@Override
	public void setDevStateId(String dsId) {
		super.setDevStateId(dsId);
		if (dsId.equals(DevStateHelper.DS_ZHENG_CHANG)) {
			for (Device dev : getListDev()) {
				dev.setDevStateId(dsId);
			}
		}
	}

	@Override
	public void handleSingleMsg(String singleMsg) {
		if(null == singleMsg || singleMsg.length() < 2) {
			return;
		}
		String head = singleMsg.substring(0, 1);
		switch(head) {
		case "3":
			String remoterCoding = singleMsg.substring(1, 3);
			Remoter remoter = (Remoter) findDevByCoding(remoterCoding);
			if(null != remoter) {
				String keyNumber = singleMsg.substring(3);
				studingKey = remoter.findKeyByNumber(keyNumber);
			}
			break;
		case "9":
			if(null != studingKey) {
				if(null != onRemoterOrderSuccessListener) {
					onRemoterOrderSuccessListener.onRemoterOrderSuccess(studingKey);
				}
			}
			break;
		}
	}
	
	/**
	 * 按键命令反馈接口
	 * @author 44489
	 *
	 */
	public interface OnRemoterOrderSuccessListener{
		/**
		 * 按键命令收到反馈
		 * @param studingKey
		 */
		void onRemoterOrderSuccess(RemoterKey studingKey);
	}
}
