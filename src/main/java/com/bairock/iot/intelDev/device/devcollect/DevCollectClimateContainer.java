package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollectClimateContainer")
public class DevCollectClimateContainer extends DevCollectSignalContainer {

	public DevCollectClimateContainer() {
		this("", "");
	}

	public DevCollectClimateContainer(String mcId, String sc) {
		super(mcId, sc);
	}

	@Override
	protected void initChildDevices() {
		// temperature
		Temperature temperature = (Temperature) DeviceAssistent.createDeviceByMcId(MainCodeHelper.WEN_DU, "1");
		//temperature.setName(this.getCoding() + "_" + temperature.getCoding());
		addChildDev(temperature);

		// humidity
		Humidity humidity = (Humidity) DeviceAssistent.createDeviceByMcId(MainCodeHelper.SHI_DU, "1");
		//humidity.setName(this.getCoding() + "_" + humidity.getCoding());
		addChildDev(humidity);

		// formaldehyde
		Formaldehyde formaldehyde = (Formaldehyde) DeviceAssistent.createDeviceByMcId(MainCodeHelper.JIA_QUAN, "1");
		//formaldehyde.setName(this.getCoding() + "_" + formaldehyde.getCoding());
		addChildDev(formaldehyde);
	}

	public Temperature findTemperatureDev() {
		for(Device dev : getListDev()) {
			if(dev instanceof Temperature) {
				return (Temperature)dev;
			}
		}
		return null;
	}

	public Humidity findHumidityDev() {
		for(Device dev : getListDev()) {
			if(dev instanceof Humidity) {
				return (Humidity)dev;
			}
		}
		return null;
	}

	public Formaldehyde findFormaldehydeDev() {
		for(Device dev : getListDev()) {
			if(dev instanceof Formaldehyde) {
				return (Formaldehyde)dev;
			}
		}
		return null;
	}

	@Override
	public String createQueueOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "3:4:5");
	}

	@Override
	public void removeChildDev(Device device) {
		return;
	}

	@Override
	protected void analysisMsgUnit(String msgUnit) {
		if (null == msgUnit || msgUnit.isEmpty()) {
			return;
		}

		String msgNoHead = msgUnit.substring(1);
		if (msgUnit.startsWith("3")) {
			Temperature t = findTemperatureDev();
			t.setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
			t.handleSingleMsg("8" + msgNoHead);
		} else if (msgUnit.startsWith("4")) {
			Humidity h = findHumidityDev();
			h.setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
			findHumidityDev().handleSingleMsg("8" + msgNoHead);
		} else if (msgUnit.startsWith("5")) {
			Formaldehyde f = findFormaldehydeDev();
			f.setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
			f.handleSingleMsg("8" + msgNoHead);
		}
	}
}
