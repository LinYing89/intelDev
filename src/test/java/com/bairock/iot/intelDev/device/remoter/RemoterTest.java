package com.bairock.iot.intelDev.device.remoter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;

public class RemoterTest {

	Remoter r = null;
	@Before
	public void setUp() throws Exception {
		r = (Remoter) DeviceAssistent.createDeviceByMcId(MainCodeHelper.SMC_REMOTER_DIAN_SHI, "1");
	}

	@Test
	public void test() {
		for(int i = 0; i < 12; i++) {
			String num = r.nextNumber();
			RemoterKey rk = new RemoterKey();
			rk.setNumber(num);
			r.addRemoterKey(rk);
			String strI = i > 9 ? String.valueOf(i) : "0" + i;
			assertEquals(strI, rk.getNumber());
		}
		
		r.removeRemoterKeyByNumber("03");
		
		String num = r.nextNumber();
		RemoterKey rk = new RemoterKey();
		rk.setNumber(num);
		assertEquals("03", rk.getNumber());
	}

}
