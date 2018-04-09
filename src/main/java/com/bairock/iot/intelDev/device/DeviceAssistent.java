package com.bairock.iot.intelDev.device;

import java.util.List;

import com.bairock.iot.intelDev.device.devcollect.DevCollectSignal;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeRoad;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeState;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchTwoRoad;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchXRoad;
import com.bairock.iot.intelDev.device.devswitch.SubDev;

/**
 * 
 * @author LinQiang
 *
 */
public class DeviceAssistent {

	/**
	 * create device by main code
	 * @param mc device main code
	 * @param sc device sub code
	 * @return
	 */
	public static Device createDeviceByMc(String mc, String sc){
		if(null == mc || sc == null){
			return null;
		}
		String mcId = MainCodeHelper.getIns().getMcId(mc);
		if(null == mcId){
			return null;
		}
		Device device = createDeviceByMcId(mcId, sc);
		if(null != device) {
			device.setAlias(mc + sc);
		}
		return device;
	}
	
	/**
	 * create device by main code identify
	 * @param mcId device main code identify
	 * @param sc device sub code 
	 * @return
	 */
	public static Device createDeviceByMcId(String mcId, String sc){
		if(null == mcId || null == sc){
			return null;
		}
		Device device = null;
		switch(mcId){
		case MainCodeHelper.XIE_TIAO_QI:
			device = new Coordinator(mcId, sc);
			break;
		case MainCodeHelper.KG_1LU_2TAI:
			device = new DevSwitchOneRoad(mcId, sc);
			break;
		case MainCodeHelper.KG_2LU_2TAI:
			device = new DevSwitchTwoRoad(mcId, sc);
			break;
		case MainCodeHelper.KG_3LU_2TAI:
			device = new DevSwitchThreeRoad(mcId, sc);
			break;
		case MainCodeHelper.KG_XLU_2TAI:
			device = new DevSwitchXRoad(mcId, sc);
			break;
		case MainCodeHelper.KG_3TAI:
			device = new DevSwitchThreeState(mcId, sc);
			break;
		case MainCodeHelper.YE_WEI:
			device = new Pressure(mcId, sc);
			break;
		case MainCodeHelper.COLLECTOR_SIGNAL:
			device = new DevCollectSignal(mcId, sc);
			break;
		case MainCodeHelper.GUAGUA_MOUTH:
			device = new GuaguaMouth(mcId, sc);
			break;
		}
		if(null == device) {
			if(mcId.startsWith("smc")) {
				device = new SubDev(mcId, sc);
			}
		}
		return device;
	}
	
	/**
	 * create device by device coding
	 * @param coding coding is main code + sub code
	 * @return
	 */
	public static Device createDeviceByCoding(String coding) {
		if (null == coding) {
			return null;
		}
		if (null == coding || coding.length() < 6) {
			return null;
		}
		Device device = null;
		String mc = coding.substring(0, 2);
		String sc = coding.substring(2);
		device = createDeviceByMc(mc, sc);
		return device;
	}
	
	/**
	 * 
	 * @param mcId main code identify
	 * @param sc sub code
	 * @param listDevice device list for scan
	 * @return
	 */
	public static Device getDeviceWithMcId(String mcId, String sc, List<Device> listDevice){
		if(null == mcId || null == sc){
			return null;
		}
		for(Device dev : listDevice){
			if(dev.getMainCodeId().equals(mcId) && dev.getSubCode().equals(sc)){
				return dev;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param mc main code
	 * @param sc sub code
	 * @param listDevice device list for scan
	 * @return
	 */
	public static Device getDeviceWithMc(String mc, String sc, List<Device> listDevice){
		if(null == mc || null == sc){
			return null;
		}
		String mcId = MainCodeHelper.getIns().getMcId(mc);
		return getDeviceWithMcId(mcId, sc, listDevice);
	}
	
	/**
	 * 
	 * @param coding coding is main code + sub code
	 * @param listDevice device list for scan
	 * @return
	 */
	public static Device getDeviceWithCoding(String coding, List<Device> listDevice){
		if(null == coding){
			return null;
		}
		Device dev = null;
		try{
			String mc = coding.substring(0, 2);
			String sc = coding.substring(2);
			dev = getDeviceWithMc(mc, sc, listDevice);
		}catch(Exception e){
			
		}
		return dev;
	}
}
