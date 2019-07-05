package com.bairock.iot.intelDev.device;

import java.util.List;

import com.bairock.iot.intelDev.device.alarm.DevAlarm;
import com.bairock.iot.intelDev.device.devcollect.DevCollectClimateContainer;
import com.bairock.iot.intelDev.device.devcollect.DevCollectSignal;
import com.bairock.iot.intelDev.device.devcollect.DevCollectSignalContainer;
import com.bairock.iot.intelDev.device.devcollect.Formaldehyde;
import com.bairock.iot.intelDev.device.devcollect.Humidity;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.device.devcollect.Temperature;
import com.bairock.iot.intelDev.device.devswitch.DevSocket;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeRoad;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeState;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchTwoRoad;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchXRoad;
import com.bairock.iot.intelDev.device.devswitch.SubDev;
import com.bairock.iot.intelDev.device.remoter.Curtain;
import com.bairock.iot.intelDev.device.remoter.CustomRemoter;
import com.bairock.iot.intelDev.device.remoter.Remoter;
import com.bairock.iot.intelDev.device.remoter.RemoterContainer;
import com.bairock.iot.intelDev.device.remoter.Television;
import com.bairock.iot.intelDev.user.DevGroup;

/**
 * 
 * @author LinQiang
 *
 */
public class DeviceAssistent {

	/**
	 * 创建设备，并根据主编码描述创建默认名
	 * @param mc
	 * @param sc
	 * @param devGroup
	 * @return
	 */
	public static Device createDeviceByMc(String mc, String sc, DevGroup devGroup){
		Device device = createDeviceByMc(mc, sc);
		devGroup.createDefaultDeviceName(device);
		return device;
	}
	
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
			mcId = mc;
		}
		Device device = createDeviceByMcId(mcId, sc);
		if(null != device) {
			device.setAlias(mc + sc);
		}
		return device;
	}
	
	/**
	 * 创建设备，并根据主编码描述创建默认名
	 * @param mcId
	 * @param sc
	 * @param devGroup
	 * @return
	 */
	public static Device createDeviceByMcId(String mcId, String sc, DevGroup devGroup){
		Device device = createDeviceByMcId(mcId, sc);
		devGroup.createDefaultDeviceName(device);
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
		case MainCodeHelper.PLC:
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
		case MainCodeHelper.CHA_ZUO:
			device = new DevSocket(mcId, sc);
			break;
		case MainCodeHelper.YAO_KONG:
			device = new RemoterContainer(mcId, sc);
			break;
		case MainCodeHelper.YE_WEI:
			device = new Pressure(mcId, sc);
			break;
		case MainCodeHelper.COLLECTOR_SIGNAL_CONTAINER:
			device = new DevCollectSignalContainer(mcId, sc);
			break;
		case MainCodeHelper.COLLECTOR_CLIMATE_CONTAINER:
			device = new DevCollectClimateContainer(mcId, sc);
			break;
		case MainCodeHelper.COLLECTOR_SIGNAL:
			device = new DevCollectSignal(mcId, sc);
			break;
		case MainCodeHelper.GUAGUA_MOUTH:
			device = new GuaguaMouth(mcId, sc);
			break;
		case MainCodeHelper.WEN_DU:
			device = new Temperature(mcId, sc);
			break;
		case MainCodeHelper.SHI_DU:
			device = new Humidity(mcId, sc);
			break;
		case MainCodeHelper.JIA_QUAN:
			device = new Formaldehyde(mcId, sc);
			break;
		case MainCodeHelper.YAN_WU:
		case MainCodeHelper.MEN_JIN:
			device = new DevAlarm(mcId, sc);
			break;
		case MainCodeHelper.SMC_REMOTER_CHUANG_LIAN:
			device = new Curtain(mcId, sc);
			break;
		case MainCodeHelper.SMC_REMOTER_DIAN_SHI:
			device = new Television(mcId, sc);
			break;
		case MainCodeHelper.SMC_REMOTER_ZI_DING_YI:
			device = new CustomRemoter(mcId, sc);
			break;
		}
		if(null == device) {
			if(mcId.startsWith("smc_remoter")) {
				device = new Remoter(mcId, sc);
			}else if(mcId.startsWith("smc")) {
				device = new SubDev(mcId, sc);
			}else {
				
			}
		}
		DevGroup.createDefaultDeviceNameAddSubCode(device);
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
		if (null == coding || coding.length() < 3) {
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
		if(null == coding || coding.length() < 3){
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
