package com.bairock.iot.intelDev.device.devcollect;

public class Humidity extends DevCollectSignal {

	public Humidity() {
		this("", "");
	}

	public Humidity(String mcId, String sc) {
		super(mcId, sc);
		getCollectProperty().setCollectSrc(CollectSignalSource.DIGIT);
		if(getCollectProperty().getUnitSymbol().equals("")) {
			getCollectProperty().setUnitSymbol("%");
		}
	}

}
