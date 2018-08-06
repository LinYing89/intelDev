package com.bairock.iot.intelDev.linkage;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.linkage.ChainHolder;
import com.bairock.iot.intelDev.linkage.LinkageCondition;
import com.bairock.iot.intelDev.linkage.Effect;
import com.bairock.iot.intelDev.linkage.LinkageTab;
import com.bairock.iot.intelDev.linkage.LinkageTabRow;
import com.bairock.iot.intelDev.linkage.ZLogic;

import junit.framework.TestCase;

public class ChainTest extends TestCase {

	ChainHolder chain = new ChainHolder();
	Pressure pressure = new Pressure(MainCodeHelper.YE_WEI, "0001");
	DevSwitchOneRoad devSwitch = new DevSwitchOneRoad(MainCodeHelper.KG_1LU_2TAI, "0001");
	
	protected void setUp() throws Exception {
		pressure.getCollectProperty().setCrestValue(100f);
		pressure.getCollectProperty().setLeastValue(0f);
		pressure.getCollectProperty().setCrestReferValue(100f);
		pressure.getCollectProperty().setLeastReferValue(0f);
		
		SubChain subChain = new SubChain();
		LinkageCondition condition = new LinkageCondition();
		condition.setLogic(ZLogic.OR);
		condition.setDevice(pressure);
		condition.setCompareSymbol(CompareSymbol.GREAT);
		condition.setCompareValue(70);
		subChain.addCondition(condition);
		
		Effect effect = new Effect();
		effect.setDevice(devSwitch.getListDev().get(0));
		effect.setDsId(DevStateHelper.DS_KAI);
		subChain.addEffect(effect);
		
		chain.addLinkage(subChain);
		
		super.setUp();
	}

	public void testRun() {
		pressure.handle("p72.5");
		devSwitch.handle("87");
		chain.setEnable(true);
		Linkage subChain = chain.getListLinkage().get(0);
		subChain.setEnable(true);
		chain.run();
		//chain.run();
		assertEquals(true, subChain.getConditionResult());
		LinkageTabRow linkTR = LinkageTab.getIns().getEqLinkageTabRow(devSwitch.getListDev().get(0));
		assertEquals(1, linkTR.getiChainTem());
		assertEquals(-1, linkTR.getiTimingTem());
		assertEquals(-1, linkTR.getLoop());
	}

}
