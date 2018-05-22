package com.bairock.iot.intelDev.device.devswitch;

import com.bairock.iot.intelDev.device.Gear;
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
		String state = "7010";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		
		dsor.turnOff();
		state = "7030";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "7011";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		
		dsor.turnOn();
		state = "7031";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		
		dsor.turnOn();
		state = "7010";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
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
	
	public void testHandler9() {
		dsor.turnOff();
		String state = "9010";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(Gear.KAI, ((SubDev)(dsor.getListDev().get(0))).getGear());
		
		dsor.turnOn();
		state = "9011";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(Gear.GUAN, ((SubDev)(dsor.getListDev().get(0))).getGear());
		
		dsor.turnOff();
		state = "9030";
		dsor.handle(state);
		assertEquals(false, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(true, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		assertEquals(Gear.KAI, ((SubDev)(dsor.getListDev().get(1))).getGear());
		
		dsor.turnOn();
		state = "9031";
		dsor.handle(state);
		assertEquals(true, ((SubDev)(dsor.getListDev().get(0))).isKaiState());
		assertEquals(false, ((SubDev)(dsor.getListDev().get(1))).isKaiState());
		assertEquals(Gear.GUAN, ((SubDev)(dsor.getListDev().get(1))).getGear());
	}
	
}
