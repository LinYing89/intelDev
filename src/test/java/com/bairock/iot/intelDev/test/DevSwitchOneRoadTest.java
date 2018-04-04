package com.bairock.iot.intelDev.test;

import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.device.devswitch.SubDev;

import junit.framework.TestCase;

public class DevSwitchOneRoadTest extends TestCase {

	DevSwitchOneRoad dsor = new DevSwitchOneRoad(MainCodeHelper.KG_1LU_2TAI, "0001");
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetListSubDev() {
		assertEquals(1, dsor.getListDev().size());
	}
	
	public void testTurnOn() {
		String msg = "B10001:32";
		assertEquals(OrderHelper.getOrderMsg(msg), ((SubDev)(dsor.getListDev().get(0))).getTurnOnOrder());
	}

}
