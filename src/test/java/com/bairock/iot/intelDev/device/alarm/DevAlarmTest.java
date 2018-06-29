package com.bairock.iot.intelDev.device.alarm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;

public class DevAlarmTest {

	private DevAlarm devAlarm;
	
	@Before
	public void setUp() throws Exception {
		devAlarm = (DevAlarm) DeviceAssistent.createDeviceByMcId(MainCodeHelper.YAN_WU, "0001");
		AlarmTrigger trigger = new AlarmTrigger(true, "报警");
		devAlarm.setTrigger(trigger);
		devAlarm.setOnAlarmTriggedListener(new DevAlarm.OnAlarmTriggedListener() {
			
			@Override
			public void onAlarmTrigged(AlarmTrigger trigger) {
				System.out.println(trigger.getDevAlarm().getCoding() + " " + trigger.getMessage());
			}
		});
	}

	@Test
	public void test() {
		assertEquals("z10001", devAlarm.getCoding());
	}
	
	@Test
	public void testAlarm() {
		devAlarm.handle("3");
		System.out.println("handler 1");
		devAlarm.handle("3");
		System.out.println("handler 2");
		devAlarm.handle("3");
		System.out.println("handler 3");
		try {
			Thread.sleep(5000);
			devAlarm.handle("3");
			System.out.println("handler 4");
			Thread.sleep(3000);
			devAlarm.handle("3");
			System.out.println("handler 5");
			Thread.sleep(6000);
			devAlarm.handle("3");
			System.out.println("handler 6");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
