package com.bairock.iot.intelDev.device.devcollect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HumidityTest {

	private Humidity humidity;
	
	@Before
	public void setUp() throws Exception {
		humidity = new Humidity("4", "1");
	}

	@Test
	public void test() {
		humidity.handle("8130f");
		assertEquals(48.79f, humidity.getCollectProperty().getCurrentValue(), 0.01);
	}

}
