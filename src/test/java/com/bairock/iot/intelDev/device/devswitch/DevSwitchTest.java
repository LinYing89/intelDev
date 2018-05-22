package com.bairock.iot.intelDev.device.devswitch;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.Gear;
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
	
	@Test
	public void testHandler8() {
		ds.turnOff();
		String state = "80";
		ds.handle(state);
		assertEquals(true, ds.getSubDevBySc("1").isKaiState());
		assertEquals(true, ds.getSubDevBySc("2").isKaiState());
		assertEquals(true, ds.getSubDevBySc("3").isKaiState());
		assertEquals(true, ds.getSubDevBySc("4").isKaiState());
		assertEquals(false, ds.getSubDevBySc("5").isKaiState());
		assertEquals(false, ds.getSubDevBySc("9").isKaiState());
		
		ds.turnOff();
		state = "800";
		ds.handle(state);
		assertEquals(true, ds.getSubDevBySc("1").isKaiState());
		assertEquals(true, ds.getSubDevBySc("2").isKaiState());
		assertEquals(true, ds.getSubDevBySc("3").isKaiState());
		assertEquals(true, ds.getSubDevBySc("4").isKaiState());
		assertEquals(true, ds.getSubDevBySc("5").isKaiState());
		assertEquals(false, ds.getSubDevBySc("9").isKaiState());
		assertEquals(false, ds.getSubDevBySc("13").isKaiState());
		ds.turnOff();
		
		ds.turnOff();
		state = "8000";
		ds.handle(state);
		assertEquals(true, ds.getSubDevBySc("1").isKaiState());
		assertEquals(true, ds.getSubDevBySc("2").isKaiState());
		assertEquals(true, ds.getSubDevBySc("3").isKaiState());
		assertEquals(true, ds.getSubDevBySc("4").isKaiState());
		assertEquals(true, ds.getSubDevBySc("5").isKaiState());
		assertEquals(true, ds.getSubDevBySc("9").isKaiState());
		assertEquals(false, ds.getSubDevBySc("13").isKaiState());
		
		ds.turnOn();
		state = "8f";
		ds.handle(state);
		assertEquals(false, ds.getSubDevBySc("1").isKaiState());
		assertEquals(false, ds.getSubDevBySc("2").isKaiState());
		assertEquals(false, ds.getSubDevBySc("3").isKaiState());
		assertEquals(false, ds.getSubDevBySc("4").isKaiState());
		assertEquals(true, ds.getSubDevBySc("5").isKaiState());
		assertEquals(true, ds.getSubDevBySc("9").isKaiState());
		
		ds.turnOn();
		state = "8ff";
		ds.handle(state);
		assertEquals(false, ds.getSubDevBySc("1").isKaiState());
		assertEquals(false, ds.getSubDevBySc("2").isKaiState());
		assertEquals(false, ds.getSubDevBySc("3").isKaiState());
		assertEquals(false, ds.getSubDevBySc("4").isKaiState());
		assertEquals(false, ds.getSubDevBySc("5").isKaiState());
		assertEquals(true, ds.getSubDevBySc("9").isKaiState());
		assertEquals(true, ds.getSubDevBySc("13").isKaiState());
		
		ds.turnOn();
		state = "8fa";
		ds.handle(state);
		assertEquals(false, ds.getSubDevBySc("1").isKaiState());
		assertEquals(false, ds.getSubDevBySc("2").isKaiState());
		assertEquals(false, ds.getSubDevBySc("3").isKaiState());
		assertEquals(false, ds.getSubDevBySc("4").isKaiState());
		assertEquals(false, ds.getSubDevBySc("5").isKaiState());
		assertEquals(true, ds.getSubDevBySc("6").isKaiState());
		assertEquals(false, ds.getSubDevBySc("7").isKaiState());
		assertEquals(true, ds.getSubDevBySc("8").isKaiState());
		assertEquals(true, ds.getSubDevBySc("9").isKaiState());
		assertEquals(true, ds.getSubDevBySc("13").isKaiState());
		
		ds.turnOff();
		state = "8f5";
		ds.handle(state);
		assertEquals(false, ds.getSubDevBySc("1").isKaiState());
		assertEquals(false, ds.getSubDevBySc("2").isKaiState());
		assertEquals(false, ds.getSubDevBySc("3").isKaiState());
		assertEquals(false, ds.getSubDevBySc("4").isKaiState());
		assertEquals(true, ds.getSubDevBySc("5").isKaiState());
		assertEquals(false, ds.getSubDevBySc("6").isKaiState());
		assertEquals(true, ds.getSubDevBySc("7").isKaiState());
		assertEquals(false, ds.getSubDevBySc("8").isKaiState());
		assertEquals(false, ds.getSubDevBySc("9").isKaiState());
		assertEquals(false, ds.getSubDevBySc("13").isKaiState());
		
		ds.turnOn();
		state = "8fff";
		ds.handle(state);
		assertEquals(false, ds.getSubDevBySc("1").isKaiState());
		assertEquals(false, ds.getSubDevBySc("2").isKaiState());
		assertEquals(false, ds.getSubDevBySc("3").isKaiState());
		assertEquals(false, ds.getSubDevBySc("4").isKaiState());
		assertEquals(false, ds.getSubDevBySc("5").isKaiState());
		assertEquals(false, ds.getSubDevBySc("9").isKaiState());
		assertEquals(true, ds.getSubDevBySc("13").isKaiState());
		
		ds.turnOff();
		state = "8a55a";
		ds.handle(state);
		assertEquals(false, ds.getSubDevBySc("1").isKaiState());
		assertEquals(true, ds.getSubDevBySc("2").isKaiState());
		assertEquals(false, ds.getSubDevBySc("3").isKaiState());
		assertEquals(true, ds.getSubDevBySc("4").isKaiState());
		assertEquals(true, ds.getSubDevBySc("5").isKaiState());
		assertEquals(false, ds.getSubDevBySc("6").isKaiState());
		assertEquals(true, ds.getSubDevBySc("7").isKaiState());
		assertEquals(false, ds.getSubDevBySc("8").isKaiState());
		assertEquals(true, ds.getSubDevBySc("9").isKaiState());
		assertEquals(false, ds.getSubDevBySc("10").isKaiState());
		assertEquals(true, ds.getSubDevBySc("11").isKaiState());
		assertEquals(false, ds.getSubDevBySc("12").isKaiState());
		assertEquals(false, ds.getSubDevBySc("13").isKaiState());
		assertEquals(true, ds.getSubDevBySc("14").isKaiState());
		assertEquals(false, ds.getSubDevBySc("15").isKaiState());
		assertEquals(true, ds.getSubDevBySc("16").isKaiState());
	}

	public void testHandler9() {
		ds.turnOff();
		String state = "9010";
		ds.handle(state);
		assertEquals(true, ((SubDev)(ds.getListDev().get(0))).isKaiState());
		assertEquals(Gear.KAI, ((SubDev)(ds.getListDev().get(0))).getGear());
		
		ds.turnOn();
		state = "9021";
		ds.handle(state);
		assertEquals(false, ((SubDev)(ds.getListDev().get(1))).isKaiState());
		assertEquals(Gear.GUAN, ((SubDev)(ds.getListDev().get(1))).getGear());
		
		ds.turnOff();
		state = "9101";
		ds.handle(state);
		assertEquals(false, ((SubDev)(ds.getListDev().get(15))).isKaiState());
		assertEquals(Gear.GUAN, ((SubDev)(ds.getListDev().get(15))).getGear());
		
		//ds.turnOff();
		state = "9100";
		ds.handle(state);
		assertEquals(true, ((SubDev)(ds.getListDev().get(15))).isKaiState());
		assertEquals(Gear.KAI, ((SubDev)(ds.getListDev().get(15))).getGear());
	}
}
