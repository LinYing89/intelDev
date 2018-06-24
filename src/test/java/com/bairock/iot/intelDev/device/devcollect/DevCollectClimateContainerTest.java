package com.bairock.iot.intelDev.device.devcollect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;

public class DevCollectClimateContainerTest {

	private DevCollectClimateContainer container;
	
	@Before
	public void setUp() throws Exception {
		container = (DevCollectClimateContainer) DeviceAssistent.createDeviceByMcId(MainCodeHelper.COLLECTOR_CLIMATE_CONTAINER, "0001");
	}

	@Test
	public void test() {
		assertEquals(3, container.getListDev().size());
	}
	
	@Test
	public void testCoding() {
		assertEquals("x10001", container.getCoding());
		assertEquals("x10001_e11", container.findTemperatureDev().getLongCoding());
		assertEquals("x10001_e21", container.findHumidityDev().getLongCoding());
		assertEquals("x10001_e31", container.findFormaldehydeDev().getLongCoding());
		assertEquals(false, container.isNormal());
	}
	
	@Test
	public void testQueneOrder() {
		assertEquals("$Qx10001:3:4:5#05", container.createQueueOrder());
	}
	
	@Test
	public void testHandler() {
		assertEquals(false, container.findTemperatureDev().isNormal());
		assertEquals(false, container.findHumidityDev().isNormal());
		assertEquals(false, container.findFormaldehydeDev().isNormal());
		container.handle("30CE7:4130F:50019");
		assertEquals(true, container.isNormal());
		assertEquals(true, container.findTemperatureDev().isNormal());
		assertEquals(true, container.findHumidityDev().isNormal());
		assertEquals(true, container.findFormaldehydeDev().isNormal());
		assertEquals(33.03f, container.findTemperatureDev().getCollectProperty().getCurrentValue(), 0.01);
		assertEquals(48.79f, container.findHumidityDev().getCollectProperty().getCurrentValue(), 0.01);
		assertEquals(0.03f, container.findFormaldehydeDev().getCollectProperty().getCurrentValue(), 0.01);
	}

}
