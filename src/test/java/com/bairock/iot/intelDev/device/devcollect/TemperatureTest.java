package com.bairock.iot.intelDev.device.devcollect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TemperatureTest {

	private Temperature temperature;
	
	@Before
	public void setUp() throws Exception {
		temperature = new Temperature("3", "1");
		//temperature.setCollectProperty(new CollectProperty());
	}

	@Test
	public void test() {
		temperature.handle("80ce7");
		assertEquals(33.03f, temperature.getCollectProperty().getCurrentValue(), 0.01);
	}

}
