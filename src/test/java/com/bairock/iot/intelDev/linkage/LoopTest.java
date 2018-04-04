package com.bairock.iot.intelDev.linkage;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.linkage.loop.LoopDuration;
import com.bairock.iot.intelDev.linkage.loop.LoopHolder;
import com.bairock.iot.intelDev.linkage.loop.ZLoop;

import junit.framework.TestCase;

public class LoopTest extends TestCase {

	LoopHolder loop = new LoopHolder();
	ZLoop subLoop = new ZLoop();
	Pressure pressure = new Pressure(MainCodeHelper.YE_WEI, "0001");
	DevSwitchOneRoad devSwitch = new DevSwitchOneRoad(MainCodeHelper.KG_1LU_2TAI, "0001");
	
	protected void setUp() throws Exception {
		LinkageCondition condition = new LinkageCondition();
		condition.setLogic(ZLogic.OR);
		condition.setDevice(pressure);
		condition.setCompareSymbol(CompareSymbol.GREAT);
		condition.setCompareValue(70);
		
		subLoop.addCondition(condition);	
		LoopDuration loopDuration = new LoopDuration();
		
		loopDuration.getOnKeepTime().setTimeStr("0:0:2");
		loopDuration.getOffKeepTime().setTimeStr("0:0:8");
		
		LoopDuration loopDuration2 = new LoopDuration();
		
		loopDuration2.getOnKeepTime().setTimeStr("0:0:1");
		loopDuration2.getOffKeepTime().setTimeStr("0:0:1");
		
		subLoop.addLoopDuration(loopDuration);
		subLoop.addLoopDuration(loopDuration2);
		
		subLoop.setLoopCount(-1);
		
		Effect effect = new Effect();
		effect.setDevice(devSwitch.getListDev().get(0));
		effect.setDsId(DevStateHelper.DS_KAI);
		subLoop.addEffect(effect);
		
		loop.addLinkage(subLoop);
		super.setUp();
	}

	public void testRun() {
		pressure.handle("p72.5");
		devSwitch.handle("707");
		loop.setEnable(true);
		Linkage subChain = loop.getListLinkage().get(0);
		subChain.setEnable(true);
		loop.run();
		//loop.run();
		assertEquals(true, subChain.getConditionResult());
		try {
			Thread.sleep(26000);
		} catch (InterruptedException e) {
			System.out.println("err");
			e.printStackTrace();
		}
		System.out.println("end");
	}

}
