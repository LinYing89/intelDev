package com.bairock.iot.intelDev.device;

import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeRoad;
import com.bairock.iot.intelDev.device.devswitch.SubDev;

import junit.framework.TestCase;

public class DevSwitchThreeRoadTest extends TestCase {

	DevSwitchThreeRoad dsor = new DevSwitchThreeRoad(MainCodeHelper.KG_3LU_2TAI, "0001");
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetListSubDev() {
		assertEquals(3, dsor.getListDev().size());
	}
	
	public void testTurnOn() {
		SubDev dev = (SubDev)dsor.getListDev().get(0);
		String msg = "B30001:31";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "B30001:41";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
		
		dev = (SubDev)dsor.getListDev().get(1);
		msg = "B30001:32";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "B30001:42";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
		
		dev = (SubDev)dsor.getListDev().get(2);
		msg = "B30001:33";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "B30001:43";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
	}

	public void testHandle(){
		SubDev dev1 = (SubDev)dsor.getListDev().get(0);
		SubDev dev2 = (SubDev)dsor.getListDev().get(1);
		SubDev dev3 = (SubDev)dsor.getListDev().get(2);
		
		dsor.handle("705");
		assertEquals(DevStateHelper.DS_GUAN, dev1.getDevStateId());
		assertEquals(DevStateHelper.DS_KAI, dev2.getDevStateId());
		assertEquals(DevStateHelper.DS_GUAN, dev3.getDevStateId());
		
		dsor.handle("711");
		assertEquals(DevStateHelper.DS_KAI, dev1.getDevStateId());
		assertEquals(Gear.KAI, dev1.getGear());
		assertEquals(DevStateHelper.DS_KAI, dev2.getDevStateId());
		assertEquals(Gear.ZIDONG, dev2.getGear());
		assertEquals(DevStateHelper.DS_GUAN, dev3.getDevStateId());
		assertEquals(Gear.ZIDONG, dev3.getGear());
		
		dsor.handle("720");
		assertEquals(DevStateHelper.DS_KAI, dev1.getDevStateId());
		assertEquals(Gear.KAI, dev1.getGear());
		assertEquals(DevStateHelper.DS_GUAN, dev2.getDevStateId());
		assertEquals(Gear.GUAN, dev2.getGear());
		assertEquals(DevStateHelper.DS_GUAN, dev3.getDevStateId());
		assertEquals(Gear.ZIDONG, dev3.getGear());
	}
}
