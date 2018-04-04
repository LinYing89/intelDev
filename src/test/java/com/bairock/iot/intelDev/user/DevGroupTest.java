package com.bairock.iot.intelDev.user;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeRoad;

public class DevGroupTest {

	DevGroup devGroup;
	
	@Before
	public void setUp() throws Exception {
		devGroup = new DevGroup();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDeviceWithCoding() {
		Device dev = DeviceAssistent.createDeviceByMcId(MainCodeHelper.XIE_TIAO_QI, "0001");
		devGroup.addDevice(dev);
		Device dd = devGroup.findDeviceWithCoding(dev.getCoding());
		assertEquals(true, dev == dd);
		
		DevHaveChild dhc = (DevHaveChild)dev;
		DevSwitchThreeRoad devS = new DevSwitchThreeRoad(MainCodeHelper.KG_3LU_2TAI, "0001");
		devS.getListDev().get(0).setMainCodeId(MainCodeHelper.SMC_DENG);
		devS.getListDev().get(0).setId("123456");
		dhc.addChildDev(devS);
		dd = devGroup.findDeviceWithCoding(devS.getListDev().get(0).getCoding());
		assertEquals(true, devS.getListDev().get(0) == dd);
		assertEquals(true, devS == dhc.getListDev().get(0));
		
		DevSwitchThreeRoad devSwitch = new DevSwitchThreeRoad(MainCodeHelper.KG_3LU_2TAI, "0002");
		devSwitch.getListDev().get(1).setMainCodeId(MainCodeHelper.SMC_DENG);
		devSwitch.getListDev().get(1).setId("1234567");
		devGroup.addDevice(devSwitch);
		dd = devGroup.findDeviceWithCoding(devSwitch.getListDev().get(1).getCoding());
		assertEquals("B30002_10_2", dd.getCoding());
		
		Device ed = devGroup.findDeviceByDevId("123456");
		assertEquals(true, devS.getListDev().get(0) == ed);
		
		Device ed2 = devGroup.findDeviceByDevId("1234567");
		assertEquals(true, devSwitch.getListDev().get(1) == ed2);
	}

}
