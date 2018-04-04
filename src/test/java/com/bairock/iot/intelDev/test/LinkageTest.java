package com.bairock.iot.intelDev.test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.device.CtrlModel;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.GuaguaMouth;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.linkage.ChainHolder;
import com.bairock.iot.intelDev.linkage.Effect;
import com.bairock.iot.intelDev.linkage.LinkageCondition;
import com.bairock.iot.intelDev.linkage.SubChain;
import com.bairock.iot.intelDev.linkage.LinkageHelper;
import com.bairock.iot.intelDev.linkage.LinkageTab;
import com.bairock.iot.intelDev.linkage.LinkageTab.OnOrderSendListener;
import com.bairock.iot.intelDev.linkage.ZLogic;
import com.bairock.iot.intelDev.linkage.guagua.GuaguaHelper;
import com.bairock.iot.intelDev.linkage.guagua.GuaguaHolder;
import com.bairock.iot.intelDev.linkage.loop.LoopDuration;
import com.bairock.iot.intelDev.linkage.loop.LoopHolder;
import com.bairock.iot.intelDev.linkage.loop.ZLoop;
import com.bairock.iot.intelDev.linkage.timing.Timing;
import com.bairock.iot.intelDev.linkage.timing.TimingHolder;
import com.bairock.iot.intelDev.linkage.timing.WeekHelper;
import com.bairock.iot.intelDev.linkage.timing.ZTimer;

public class LinkageTest {

	public static float preV = 0;
	public Pressure pressure;
	public DevSwitchOneRoad devSwitch;
	public int state;
	
	public static void main(String[] args) {
		LinkageTest lt = new LinkageTest();
		
		LinkageTab.getIns().SetOnOrderSendListener(new OnOrderSendListener(){

			@Override
			public void onChanged(Device device, String order, CtrlModel ctrlModel) {
				System.out.println("order: " + order + " cm: " + ctrlModel);
			}
			
		});
		
		ChainHolder chain = LinkageHelper.getIns().getChain();
		lt.pressure = new Pressure(MainCodeHelper.YE_WEI, "0001");
		lt.devSwitch = new DevSwitchOneRoad(MainCodeHelper.KG_1LU_2TAI, "0001");
		
		SubChain subChain = new SubChain();
		subChain.setEnable(true);
		LinkageCondition condition = new LinkageCondition();
		condition.setLogic(ZLogic.OR);
		condition.setDevice(lt.pressure);
		condition.setCompareSymbol(CompareSymbol.GREAT);
		condition.setCompareValue(50);
		subChain.addCondition(condition);
		
		Effect effect = new Effect();
		effect.setDevice(lt.devSwitch.getListDev().get(0));
		effect.setDsId(DevStateHelper.DS_KAI);
		subChain.addEffect(effect);
		
		chain.addLinkage(subChain);
		
		lt.pressure.handle("p" + preV);
		lt.devSwitch.handle("70" + lt.state);
		chain.setEnable(false);
		
		//lt.initTiming();
		lt.initLoop();
		//lt.initGuagua();
		
		LinkageTest.PreV pt = lt.new PreV();
		pt.start();
		
		LinkageHelper.getIns().startCheckLinkageThread();
		
		//GuaguaHelper.getIns().startCheckGuaguaThread();
	}
	
	
	private void initTiming(){
		TimingHolder timing = LinkageHelper.getIns().getTiming();
		Timing subTiming = new Timing();
		ZTimer timer = new ZTimer();
		
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.set(0, 0, 0, 16, 47, 0);
		c2.set(0, 0, 0, 16, 49, 0);
		
//		timer.setOnTime(c1);
//		timer.setOffTime(c2);
		timer.setEnable(true);
		
		WeekHelper weekHelper = timer.getWeekHelper();
		weekHelper.setWeeks(2, 3, 4);
		
		Effect effect = new Effect();
		effect.setDevice(devSwitch.getListDev().get(0));
		effect.setDsId(DevStateHelper.DS_KAI);
		
		subTiming.addZTimer(timer);
		subTiming.addEffect(effect);
		subTiming.setEnable(true);
		
		timing.addTiming(subTiming);
		timing.setEnable(true);
	}
	
	private void initLoop(){
		LoopHolder loop = LinkageHelper.getIns().getLoop();
		ZLoop subLoop = new ZLoop();
		LinkageCondition condition = new LinkageCondition();
		condition.setLogic(ZLogic.OR);
		condition.setDevice(pressure);
		condition.setCompareSymbol(CompareSymbol.GREAT);
		condition.setCompareValue(60);
		
		subLoop.addCondition(condition);
		
		LoopDuration loopDuration = new LoopDuration();
		loopDuration.getOnKeepTime().setSecond(5);
		loopDuration.getOffKeepTime().setSecond(5);
		
		//LoopDuration loopDuration2 = new LoopDuration();
		
		subLoop.addLoopDuration(loopDuration);
		//subLoop.addLoopDuration(loopDuration2);
		
		subLoop.setLoopCount(8);
		
		Effect effect = new Effect();
		effect.setDevice(devSwitch.getListDev().get(0));
		//effect.setDsId(DevStateHelper.DS_KAI);
		subLoop.addEffect(effect);
		
		loop.addLinkage(subLoop);
		
		subLoop.setEnable(true);
		loop.setEnable(true);
	}
	
	private void initGuagua(){
		GuaguaHolder guagua = GuaguaHelper.getIns().getGuaguaHolder();
		GuaguaMouth guaguaMouth = new GuaguaMouth(MainCodeHelper.GUAGUA_MOUTH, "0001");
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
	}
	
	public class PreV extends Thread{
		
		@Override
		public void run() {
			while(true){
				preV += 10;
				if(preV >= 100){
					preV = 0;
				}
				//System.out.println("preV: " + preV);
				state += 1;
				if(state > 7){
					state = 0;
				}
				if(preV > 70){
					state = 0;
				}
				devSwitch.handle("70" + state);
				pressure.handle("p" + preV);
				System.out.println("state: " + state + "," + preV);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
