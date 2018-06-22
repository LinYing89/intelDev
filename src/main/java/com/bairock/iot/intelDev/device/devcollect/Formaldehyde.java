package com.bairock.iot.intelDev.device.devcollect;

public class Formaldehyde extends DevCollectSignal {

	public Formaldehyde() {
		this("", "");
	}

	public Formaldehyde(String mcId, String sc) {
		super(mcId, sc);
		getCollectProperty().setCollectSrc(CollectSignalSource.DIGIT);
		if(getCollectProperty().getUnitSymbol().equals("")) {
			getCollectProperty().setUnitSymbol("mg/m3");
		}
	}

	@Override
	protected float digitIValueToSrcValue(int iValue) {
		return iValue * 0.0012f;
	}
}
