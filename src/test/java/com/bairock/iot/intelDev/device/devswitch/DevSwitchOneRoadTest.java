package com.bairock.iot.intelDev.device.devswitch;

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
	
	public void testCreateTurnOnOrder() {
		String msg = "CB10001:32";
		assertEquals(OrderHelper.getOrderMsg(msg), ((SubDev)(dsor.getListDev().get(0))).getTurnOnOrder());
	}
	
	public void testCreateTurnOffOrder() {
		String msg = "CB10001:42";
		assertEquals(OrderHelper.getOrderMsg(msg), ((SubDev)(dsor.getListDev().get(0))).getTurnOffOrder());
	}
	
	public void testTurnOn() {
		dsor.turnOn();
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
	}
	
	public void testTurnOff() {
		dsor.turnOff();
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
	}
	
	public void testHandler8() {
		dsor.turnOff();
		String state = "80";
		dsor.handle(state);
		boolean resutl = ((SubDev)(dsor.getListDev().get(0))).isKaiState();
		System.out.println(((SubDev)(dsor.getListDev().get(0))).isKaiState() + "?");
		assertEquals(true, resutl);
		
		dsor.turnOn();
		state = "82";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
	}
	
	public void testHandler7() {
		dsor.turnOff();
		String state = "70c";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		
		dsor.turnOn();
		state = "702";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
	}
	
	public void testHandler9() {
//		dsor.turnOff();
//		String state = "904";
//		dsor.handle(state);
//		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
//		
//		dsor.turnOn();
//		state = "905";
//		dsor.handle(state);
//		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
	}

}
