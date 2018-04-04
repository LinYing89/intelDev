package com.bairock.iot.intelDev.linkage;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.device.devswitch.DevSwitch;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchTwoRoad;
import com.bairock.iot.intelDev.linkage.LinkageCondition;

import junit.framework.TestCase;

public class ConditionTest extends TestCase {

	LinkageCondition c = new LinkageCondition();
	LinkageCondition c2 = new LinkageCondition();
	protected void setUp() throws Exception {
		super.setUp();
		DevSwitch ds = new DevSwitchTwoRoad(MainCodeHelper.KG_2LU_2TAI, "0001");
		ds.handle("707");
		c.setDevice(ds.getListDev().get(0));
		c.setCompareSymbol(CompareSymbol.EQUAL);
		c.setCompareValue(0);
		
		Pressure p = new Pressure();
		p.handle("p30");
		c2.setDevice(p);
		c2.setCompareSymbol(CompareSymbol.GREAT);
		c2.setCompareValue(20);
	}

	public void testGetResult() {
		assertEquals(1, (int)c.getResult());
		assertEquals(1, (int)c2.getResult());
	}

}
