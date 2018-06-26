package com.bairock.iot.intelDev.device.devcollect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;

public class TemperatureTest {

	private Temperature temperature;
	
	@Before
	public void setUp() throws Exception {
		temperature = (Temperature) DeviceAssistent.createDeviceByMcId(MainCodeHelper.WEN_DU, "1");
		CollectProperty cp = temperature.getCollectProperty();
		cp.setOnValueTriggedListener(new CollectProperty.OnValueTriggedListener() {
			
			@Override
			public void onValueTrigged(ValueTrigger trigger, float value) {
				System.out.println(trigger + " value - " + value);
				
			}
		});
		
		ValueTrigger trigger = new ValueTrigger();
		trigger.setCompareSymbol(CompareSymbol.EQUAL);
		trigger.setEnable(true);
		trigger.setMessage("等于");
		trigger.setTriggerValue(33.03f);
		cp.addValueTrigger(trigger);
		
		ValueTrigger trigger1 = new ValueTrigger();
		trigger1.setCompareSymbol(CompareSymbol.LESS);
		trigger1.setEnable(true);
		trigger1.setMessage("小于");
		trigger1.setTriggerValue(32f);
		cp.addValueTrigger(trigger1);
		
		ValueTrigger trigger2 = new ValueTrigger();
		trigger2.setCompareSymbol(CompareSymbol.GREAT);
		trigger2.setDevice(temperature);
		trigger2.setEnable(true);
		trigger2.setMessage("大于");
		trigger2.setTriggerValue(34f);
		cp.addValueTrigger(trigger2);
	}

	@Test
	public void test() {
		temperature.handle("80ce7");
		assertEquals(33.03f, temperature.getCollectProperty().getCurrentValue(), 0.01);
		
		temperature.handle("80c01");
		assertEquals(30.73f, temperature.getCollectProperty().getCurrentValue(), 0.01);
		temperature.handle("80001");
		temperature.handle("80ce5");
		temperature.handle("80001");
		
		temperature.handle("80fe7");
		assertEquals(40.71f, temperature.getCollectProperty().getCurrentValue(), 0.01);
		
		temperature.handle("80ff7");
		temperature.handle("80ce8");
		temperature.handle("80ff7");
	}

}
