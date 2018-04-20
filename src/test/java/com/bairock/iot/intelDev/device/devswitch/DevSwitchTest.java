package com.bairock.iot.intelDev.device.devswitch;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;

public class DevSwitchTest {

	DevSwitch ds = (DevSwitch)DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_XLU_2TAI, "0001");
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTurnOn() {
		ds.turnOn();
		for(Device dev : ds.getListDev()) {
			assertEquals(true, dev.isKaiState());
		}
	}
	
	@Test
	public void testTurnOff() {
		ds.turnOff();
		for(Device dev : ds.getListDev()) {
			assertEquals(false, dev.isKaiState());
		}
	}
	
	@Test
	public void testCreateTurnOnOffOrder() {
		SubDev dev = (SubDev)ds.getSubDevBySc("4");
		String msg = "CBx0001:34";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "CBx0001:44";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
		
		dev = (SubDev)ds.getSubDevBySc("5");
		msg = "CBx0001:35";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "CBx0001:45";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
		
		dev = (SubDev)ds.getSubDevBySc("15");
		msg = "CBx0001:3f";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOnOrder());
		msg = "CBx0001:4f";
		assertEquals(OrderHelper.getOrderMsg(msg), dev.getTurnOffOrder());
	}
	
	@Test
	public void testHandler7() {
		ds.turnOff();
		String state = "7010";
		ds.handle(state);
		assertEquals(true, ds.getSubDevBySc("1").isKaiState());
		
		ds.turnOn();
		state = "7021";
		ds.handle(state);
		assertEquals(false, ds.getSubDevBySc("2").isKaiState());
		
		ds.turnOff();
		state = "7100";
		ds.handle(state);
		assertEquals(true, ds.getSubDevBySc("16").isKaiState());
	}

}
