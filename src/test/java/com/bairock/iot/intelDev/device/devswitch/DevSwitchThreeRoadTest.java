package com.bairock.iot.intelDev.device.devswitch;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;

import junit.framework.TestCase;

public class DevSwitchThreeRoadTest extends TestCase {

	DevSwitchThreeRoad dsor = new DevSwitchThreeRoad(MainCodeHelper.KG_3LU_2TAI, "0001");
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testTurnOn() {
		dsor.turnOn();
		for(Device dev : dsor.getListDev()) {
			assertEquals(true, dev.isKaiState());
		}
	}
	
	public void testTurnOff() {
		dsor.turnOff();
		for(Device dev : dsor.getListDev()) {
			assertEquals(false, dev.isKaiState());
		}
	}

	public void testGetListSubDev() {
		assertEquals(3, dsor.getListDev().size());
	}
	
	public void testCreateTurnOnOffOrder() {
		SubDev dev = (SubDev)dsor.getListDev().get(0);
		String msg = "CB30001:31";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "CB30001:41";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
		
		dev = (SubDev)dsor.getListDev().get(1);
		msg = "CB30001:32";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "CB30001:42";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
		
		dev = (SubDev)dsor.getListDev().get(2);
		msg = "CB30001:33";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "CB30001:43";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
	}

//	public void testHandler7() {
//		dsor.turnOff();
//		String state = "7010";
//		dsor.handle(state);
//		assertEquals(true, dsor.getSubDevBySc("1").isKaiState());
//		
//		dsor.turnOn();
//		state = "7021";
//		dsor.handle(state);
//		assertEquals(false, dsor.getSubDevBySc("2").isKaiState());
//		
//		dsor.turnOn();
//		state = "7031";
//		dsor.handle(state);
//		assertEquals(false, dsor.getSubDevBySc("3").isKaiState());
//	}
	
	public void testHandler7() {
		dsor.turnOff();
		String state = "700";
		dsor.handle(state);
		assertEquals(true, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "707";
		dsor.handle(state);
		assertEquals(false, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "705";
		dsor.handle(state);
		assertEquals(false, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("3").isKaiState());
	}
	
	
	public void testHandler8() {
		dsor.turnOff();
		String state = "80";
		dsor.handle(state);
		assertEquals(true, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "87";
		dsor.handle(state);
		assertEquals(false, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "81";
		dsor.handle(state);
		assertEquals(false, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "82";
		dsor.handle(state);
		assertEquals(true, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "83";
		dsor.handle(state);
		assertEquals(false, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "84";
		dsor.handle(state);
		assertEquals(true, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "85";
		dsor.handle(state);
		assertEquals(false, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(true, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("3").isKaiState());
		
		dsor.turnOn();
		state = "86";
		dsor.handle(state);
		assertEquals(true, dsor.getSubDevBySc("1").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("2").isKaiState());
		assertEquals(false, dsor.getSubDevBySc("3").isKaiState());
	}
	
}
