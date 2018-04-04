package com.bairock.iot.intelDev.linkage.timing;

import com.bairock.iot.intelDev.device.CtrlModel;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.linkage.Effect;
import com.bairock.iot.intelDev.linkage.LinkageTab;
import com.bairock.iot.intelDev.linkage.LinkageTabRow;
import com.bairock.iot.intelDev.linkage.LinkageTab.OnOrderSendListener;

import junit.framework.TestCase;

//@Ignore
public class TimingTest extends TestCase {
	
	TimingHolder timing = new TimingHolder();
	Timing subTiming = new Timing();
	ZTimer timer = new ZTimer();
	DevSwitchOneRoad devSwitch = new DevSwitchOneRoad(MainCodeHelper.KG_1LU_2TAI, "0001");
	
	protected void setUp() throws Exception {
		timer.setEnable(true);
		
		WeekHelper weekHelper = timer.getWeekHelper();
		weekHelper.setWeeks(4);
		
		Effect effect = new Effect();
		effect.setDevice(devSwitch.getListDev().get(0));
		effect.setDsId(DevStateHelper.DS_KAI);
		
		subTiming.addZTimer(timer);
		subTiming.addEffect(effect);
		subTiming.setEnable(true);
		
		timing.addTiming(subTiming);
		timing.setEnable(true);
		
		LinkageTab.getIns().SetOnOrderSendListener(new OnOrderSendListener() {
			
			@Override
			public void onChanged(Device device, String order, CtrlModel ctrlModel) {
				System.out.println(order);
			}
		});
		super.setUp();
	}
	
	public void testBrfore(){
		timer.getWeekHelper().setWeeks(0,1);
		Timing t1 = (Timing)timing.getListLinkage().get(0);
		t1.getListZTimer().get(0).getOnTime().setTimeStr("16:00:00");
		Timing t2 = (Timing)timing.getListLinkage().get(0);
		t2.getListZTimer().get(0).getOffTime().setTimeStr("16:30:00");
		assertEquals(false, timing.getListLinkage().get(0).getConditionResult());
	}
	
	public void testInner(){
		timer.getWeekHelper().setWeeks(0,1,2,3,4,5,6);
		Timing t1 = (Timing)timing.getListLinkage().get(0);
		t1.getListZTimer().get(0).getOnTime().setTimeStr("8:00:00");
		Timing t2 = (Timing)timing.getListLinkage().get(0);
		t2.getListZTimer().get(0).getOffTime().setTimeStr("22:30:00");
		timing.run();
		try {
			LinkageTab.getIns().checkTabRows();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (LinkageTabRow tabRow : 
			LinkageTab.getIns().getListLinkageTabRow()) {
			System.out.println("LinkageHelper run: " + tabRow.getITemString());
		}
		t1.getListZTimer().get(0).getOnTime().setTimeStr("20:00:00");
		t2.getListZTimer().get(0).getOffTime().setTimeStr("22:30:00");
		timing.run();
		try {
			LinkageTab.getIns().checkTabRows();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (LinkageTabRow tabRow : 
			LinkageTab.getIns().getListLinkageTabRow()) {
			System.out.println("LinkageHelper run: " + tabRow.getITemString());
		}
		//assertEquals(true, timing.getListTiming().get(0).getConditionResult());
		
		timer.getWeekHelper().setWeeks(0);
		t1.getListZTimer().get(0).getOnTime().setTimeStr("20:00:00");
		t2.getListZTimer().get(0).getOffTime().setTimeStr("22:30:00");
		assertEquals(false, timing.getListLinkage().get(0).getConditionResult());
	}
	
	public void testAfter(){
		timer.getWeekHelper().setWeeks(5,6);
		Timing t1 = (Timing)timing.getListLinkage().get(0);
		t1.getListZTimer().get(0).getOnTime().setTimeStr("2:00:00");
		Timing t2 = (Timing)timing.getListLinkage().get(0);
		t2.getListZTimer().get(0).getOffTime().setTimeStr("3:30:00");
		assertEquals(false, timing.getListLinkage().get(0).getConditionResult());
	}

}
