package com.bairock.iot.intelDev.linkage;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.device.CtrlModel;
import com.bairock.iot.intelDev.device.GuaguaMouth;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.linkage.guagua.GuaguaHelper;
import com.bairock.iot.intelDev.linkage.guagua.GuaguaHolder;

import junit.framework.TestCase;

public class GuaguaTest extends TestCase {

	GuaguaHolder guagua = new GuaguaHolder();
	Pressure pressure = new Pressure(MainCodeHelper.YE_WEI, "0001");
	GuaguaMouth guaguaMouth = new GuaguaMouth(MainCodeHelper.GUAGUA_MOUTH, "0001");
	
	protected void setUp() throws Exception {
		pressure.getCollectProperty().setCrestValue(100f);
		pressure.getCollectProperty().setLeastValue(0f);
		pressure.getCollectProperty().setCrestReferValue(100f);
		pressure.getCollectProperty().setLeastReferValue(0f);
		
		GuaguaHelper.getIns().setOnOrderSendListener(new GuaguaHelper.OnOrderSendListener() {

			@Override
			public void onChanged(GuaguaMouth guaguaMouth, String order, CtrlModel ctrlModel) {
				System.out.println(order);
			}
		});
		
		SubChain subGuagua = new SubChain();
		LinkageCondition condition = new LinkageCondition();
		condition.setLogic(ZLogic.OR);
		condition.setDevice(pressure);
		condition.setCompareSymbol(CompareSymbol.GREAT);
		condition.setCompareValue(70);
		subGuagua.addCondition(condition);
		
		Effect effect = new Effect();
		effect.setDevice(guaguaMouth);
		effect.setEffectContent("ok");
		effect.setEffectCount(3);
		subGuagua.addEffect(effect);
		
		guagua.addLinkage(subGuagua);
		
		guagua.setEnable(true);
		subGuagua.setEnable(true);
		super.setUp();
	}

	public void testRun() {
		pressure.handle("p72.5");
		guagua.run();
		assertEquals(true, guagua.getListLinkage().get(0).getConditionResult());
	}

}
