package com.bairock.iot.intelDev.device.devcollect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DevCollectSignalTest {

	private DevCollectSignal dev;
	
	@Before
	public void setUp() throws Exception {
		dev = new DevCollectSignal("d1", "9999");
		dev.getCollectProperty().setCollectSrc(CollectSignalSource.DIGIT);
		//dev.getCollectProperty().setCrestValue(100f);
		dev.getCollectProperty().setCrestReferValue(100f);
		//dev.getCollectProperty().setLeastValue(0f);
	}

	@Test
	public void test() {
		dev.handle("85000");
		assertEquals(50f, dev.getCollectProperty().getCurrentValue(), 0.01f);
		assertEquals(50f, dev.getCollectProperty().getPercent(), 0.01f);
	}

}
