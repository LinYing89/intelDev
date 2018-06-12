package com.bairock.iot.intelDev.device.devcollect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;

public class DevCollectSignalContainerTest {

	DevCollectSignalContainer dcsc = (DevCollectSignalContainer)DeviceAssistent.createDeviceByMcId(MainCodeHelper.COLLECTOR_SIGNAL_CONTAINER, "0001");
	@Before
	public void setUp() throws Exception {
		DevCollectSignal d1 = (DevCollectSignal)dcsc.findSubDevBySc("1");
		d1.getCollectProperty().setCollectSrc(CollectSignalSource.VOLTAGE);
		d1.getCollectProperty().setLeastValue(0f);
		d1.getCollectProperty().setCrestValue(5f);
		d1.getCollectProperty().setLeastReferValue(0f);
		d1.getCollectProperty().setCrestReferValue(100f);
		
		DevCollectSignal d2 = (DevCollectSignal)dcsc.findSubDevBySc("2");
		d2.getCollectProperty().setCollectSrc(CollectSignalSource.ELECTRIC_CURRENT);
		d2.getCollectProperty().setLeastValue(4f);
		d2.getCollectProperty().setCrestValue(20f);
		d2.getCollectProperty().setLeastReferValue(10f);
		d2.getCollectProperty().setCrestReferValue(100f);
		
		DevCollectSignal d3 = (DevCollectSignal)dcsc.findSubDevBySc("3");
		d3.getCollectProperty().setCollectSrc(CollectSignalSource.DIGIT);
		d3.getCollectProperty().setLeastValue(0f);
		d3.getCollectProperty().setCrestValue(100f);
		d3.getCollectProperty().setLeastReferValue(0f);
		d3.getCollectProperty().setCrestReferValue(100f);
		
		DevCollectSignal d4 = (DevCollectSignal)dcsc.findSubDevBySc("4");
		d4.getCollectProperty().setCollectSrc(CollectSignalSource.SWITCH);
		d4.getCollectProperty().setLeastValue(0f);
		d4.getCollectProperty().setCrestValue(1f);
	}

	@Test
	public void testSrcValue0() {
		dcsc.handle("80,190,0,0");
		DevCollectSignal d1 = (DevCollectSignal)dcsc.findSubDevBySc("1");
		assertEquals(0f, d1.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(0f, d1.getCollectProperty().getCurrentValue(), 0.01);
		
		DevCollectSignal d2 = (DevCollectSignal)dcsc.findSubDevBySc("2");
		assertEquals(4f, d2.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(10f, d2.getCollectProperty().getCurrentValue(), 0.01);
		
		DevCollectSignal d3 = (DevCollectSignal)dcsc.findSubDevBySc("3");
		assertEquals(0f, d3.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(0f, d3.getCollectProperty().getCurrentValue(), 0.01);
		
		DevCollectSignal d4 = (DevCollectSignal)dcsc.findSubDevBySc("4");
		assertEquals(0f, d4.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(1f, d4.getCollectProperty().getCurrentValue(), 0.01);
		
	}
	
	@Test
	public void testSrcValue1() {
		dcsc.handle("81f4,7d0,2710,64");
		DevCollectSignal d1 = (DevCollectSignal)dcsc.findSubDevBySc("1");
		assertEquals(5f, d1.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(100f, d1.getCollectProperty().getCurrentValue(), 0.01);
		
		DevCollectSignal d2 = (DevCollectSignal)dcsc.findSubDevBySc("2");
		assertEquals(20f, d2.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(100f, d2.getCollectProperty().getCurrentValue(), 0.01);
		
		DevCollectSignal d3 = (DevCollectSignal)dcsc.findSubDevBySc("3");
		assertEquals(100f, d3.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(100f, d3.getCollectProperty().getCurrentValue(), 0.01);
		
		DevCollectSignal d4 = (DevCollectSignal)dcsc.findSubDevBySc("4");
		assertEquals(100f, d4.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(0f, d4.getCollectProperty().getCurrentValue(), 0.01);
	}

	@Test
	public void testSrcValue2() {
		//0x3fff - 16383
		//0x3021 - 12321
		//0x0312 - 786
		dcsc.handle("8178,1dd,8b10,0");
		DevCollectSignal d1 = (DevCollectSignal)dcsc.findSubDevBySc("1");
		assertEquals(3.76f, d1.getCollectProperty().getSimulatorValue(), 0.02);
		assertEquals(75.2f, d1.getCollectProperty().getCurrentValue(), 0.02);
		
		DevCollectSignal d2 = (DevCollectSignal)dcsc.findSubDevBySc("2");
		assertEquals(4.77f, d2.getCollectProperty().getSimulatorValue(), 0.02);
		assertEquals(14.33f, d2.getCollectProperty().getCurrentValue(), 0.02);
		
		DevCollectSignal d3 = (DevCollectSignal)dcsc.findSubDevBySc("3");
		assertEquals(356f, d3.getCollectProperty().getSimulatorValue(), 0.02);
		assertEquals(356f, d3.getCollectProperty().getCurrentValue(), 0.02);
		
		DevCollectSignal d4 = (DevCollectSignal)dcsc.findSubDevBySc("4");
		assertEquals(0f, d4.getCollectProperty().getSimulatorValue(), 0.01);
		assertEquals(1f, d4.getCollectProperty().getCurrentValue(), 0.01);
	}
}
