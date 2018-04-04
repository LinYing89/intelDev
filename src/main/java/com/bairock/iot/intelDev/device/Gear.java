package com.bairock.iot.intelDev.device;

/**
 * gear of device
 * @author LinQiang
 *
 */
public enum Gear {

	GUAN,
	KAI,
	ZIDONG,
	UNKNOW;
	
	/**
	 * 
	 * @param dev
	 * @param state 0 is off, 1 is on, 2 is auto, 3 is nuknow
	 */
	public static void setGearModel(Device dev, int state){
		switch(state){
		case 2:
			dev.setGear(ZIDONG);
			break;
		case 0:
			dev.setGear(GUAN);
			break;
		case 1:
			dev.setGear(KAI);
			break;
		case 3:
			dev.setGear(UNKNOW);
			break;
		}
	}
	
	public static String getPadGearModel(String devGear){
		switch(devGear){
		case "0":
			return "2";
		case "3":
			return "1";
		case "4":
			return "0";
		}
		return "3";
	}
	
	public static String getDevGearModel(Device dev){
		switch(dev.getGear()){
		case ZIDONG:
			return "0";
		case GUAN:
			return "4";
		case KAI:
			return "3";
		default:
			return "0";
		}
	}
}
