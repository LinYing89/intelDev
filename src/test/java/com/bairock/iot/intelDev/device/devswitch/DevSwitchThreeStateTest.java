package com.bairock.iot.intelDev.device.devswitch;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeState;

import junit.framework.TestCase;

public class DevSwitchThreeStateTest extends TestCase {

	DevSwitchThreeState device = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		device = (DevSwitchThreeState)DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_3TAI, "0001");
	}
	
	public void testGetTurnOnOrder(){
		String order = ((SubDevThreeState)(device.getListDev().get(0))).getTurnOnOrder();
		String o = OrderHelper.getOrderMsg("C10001:31");
		assertEquals(o, order);
	}
	
	public void testGetTurnOffOrder(){
		String order = ((SubDevThreeState)(device.getListDev().get(0))).getTurnOffOrder();
		String o = OrderHelper.getOrderMsg("C10001:41");
		assertEquals(o, order);
	}
	
	public void testGetTurnStopOrder(){
		SubDevThreeState dev = (SubDevThreeState) device.getListDev().get(0);
		String order = dev.getStopOrder();
		String o = OrderHelper.getOrderMsg("C10001:51");
		assertEquals(o, order);
	}

}
