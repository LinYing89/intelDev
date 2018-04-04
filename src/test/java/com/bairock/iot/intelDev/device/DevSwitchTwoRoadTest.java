package com.bairock.iot.intelDev.device;

import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchTwoRoad;
import com.bairock.iot.intelDev.device.devswitch.SubDev;

import junit.framework.TestCase;

public class DevSwitchTwoRoadTest extends TestCase {

	DevSwitchTwoRoad dsor = new DevSwitchTwoRoad(MainCodeHelper.KG_2LU_2TAI, "0001");
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetListSubDev() {
		assertEquals(2, dsor.getListDev().size());
	}
	
	public void testTurnOn() {
		SubDev dev = (SubDev)dsor.getListDev().get(0);
		String msg = "B20001:31";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "B20001:41";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
		
		dev = (SubDev)dsor.getListDev().get(1);
		msg = "B20001:33";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "B20001:43";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
	}

}
