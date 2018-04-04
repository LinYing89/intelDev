package com.bairock.iot.intelDev.device.devcollect;

import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.Pressure;

import junit.framework.TestCase;

public class PressureTest extends TestCase {

	Pressure pressure = new Pressure(MainCodeHelper.YE_WEI, "0001");
	
	protected void setUp() throws Exception {
		super.setUp();
		pressure.getCollectProperty().setCrestValue(500f);
		pressure.getCollectProperty().setLeastValue(0f);
		pressure.handle("p36.5");
	}

	public void testGetValue() {
		assertEquals(182.5f, pressure.getCollectProperty().getCurrentValue());
	}

	public void testGetPercent() {
		assertEquals(36.5f, pressure.getCollectProperty().getPercent());
	}

}
