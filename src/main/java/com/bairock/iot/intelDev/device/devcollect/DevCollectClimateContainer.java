package com.bairock.iot.intelDev.device.devcollect;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;

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
		Temperature temperature = new Temperature(MainCodeHelper.WEN_DU, "1");
		temperature.setName(this.getCoding() + "_" + temperature.getCoding());
		addChildDev(temperature);

		// humidity
		Humidity humidity = new Humidity(MainCodeHelper.SHI_DU, "1");
		humidity.setName(this.getCoding() + "_" + humidity.getCoding());
		addChildDev(humidity);

		// formaldehyde
		Formaldehyde formaldehyde = new Formaldehyde(MainCodeHelper.JIA_QUAN, "1");
		formaldehyde.setName(this.getCoding() + "_" + formaldehyde.getCoding());
		addChildDev(formaldehyde);
	}

	public Temperature findTemperatureDev() {
		return (Temperature) getListDev().get(0);
	}

	public Humidity findHumidityDev() {
		return (Humidity) getListDev().get(1);
	}

	public Formaldehyde findFormaldehydeDev() {
		return (Formaldehyde) getListDev().get(2);
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
			findTemperatureDev().handleSingleMsg("8" + msgNoHead);
		} else if (msgUnit.startsWith("4")) {
			findHumidityDev().handleSingleMsg("8" + msgNoHead);
		} else if (msgUnit.startsWith("5")) {
			findFormaldehydeDev().handleSingleMsg("8" + msgNoHead);
		}
	}
}
