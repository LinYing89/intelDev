package com.bairock.iot.intelDev.device.devswitch;

import com.bairock.iot.intelDev.device.MainCodeHelper;
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
	
	public void testCreateTurnOnOrder() {
		String msg = "CB20001:31";
		assertEquals(OrderHelper.getOrderMsg(msg), ((SubDev)(dsor.getListDev().get(0))).getTurnOnOrder());
		
		msg = "CB20001:33";
		assertEquals(OrderHelper.getOrderMsg(msg), ((SubDev)(dsor.getListDev().get(1))).getTurnOnOrder());
	}
	
	public void testCreateTurnOffOrder() {
		String msg = "CB20001:41";
		assertEquals(OrderHelper.getOrderMsg(msg), ((SubDev)(dsor.getListDev().get(0))).getTurnOffOrder());
		
		msg = "CB20001:43";
		assertEquals(OrderHelper.getOrderMsg(msg), ((SubDev)(dsor.getListDev().get(1))).getTurnOffOrder());
	}
	
	public void testTurnOn() {
		dsor.turnOn();
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
	}
	
	public void testTurnOff() {
		dsor.turnOff();
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(false, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
	}
	
	public void testHandler7() {
		dsor.turnOff();
		String state = "700";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "70f";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(false, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "703";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "705";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(false, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "706";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(false, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
	}

	public void testHandler8() {
		dsor.turnOff();
		String state = "80";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "84";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "8a";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(false, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOff();
		state = "850";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOff();
		state = "85f";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
	}
	
}
