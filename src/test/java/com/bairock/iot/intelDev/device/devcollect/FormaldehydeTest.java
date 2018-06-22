package com.bairock.iot.intelDev.device.devcollect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FormaldehydeTest {
	
	private Formaldehyde formaldehyde;
	
	@Before
	public void setUp() throws Exception {
		formaldehyde = new Formaldehyde("5", "1");
		//temperature.setCollectProperty(new CollectProperty());
	}

	@Test
	public void test() {
		formaldehyde.handle("80019");
		assertEquals(0.03f, formaldehyde.getCollectProperty().getCurrentValue(), 0.01);
	}
}
