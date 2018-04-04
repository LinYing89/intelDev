package com.bairock.iot.intelDev.linkage.timing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.linkage.Effect;
import com.bairock.iot.intelDev.linkage.Linkage;
import com.bairock.iot.intelDev.linkage.LinkageTab;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Timing")
public class Timing extends Linkage {
	
	@OneToMany(mappedBy="timing", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("timimg_ztimer")
	private List<ZTimer> listZTimer;
	
	public Timing() {
		listZTimer = Collections.synchronizedList(new ArrayList<>());
	}

	public List<ZTimer> getListZTimer() {
		return listZTimer;
	}

	public void setListZTimer(List<ZTimer> listZTimer) {
		this.listZTimer = listZTimer;
		for(ZTimer t : listZTimer) {
			t.setTiming(this);
		}
	}

	/**
	 * 
	 * @param timer
	 */
	public void addZTimer(ZTimer timer){
		if(null != timer 
				&& !listZTimer.contains(timer)){
			timer.setTiming(this);
			listZTimer.add(timer);
		}
	}
	
	/**
	 * 
	 * @param timer
	 * @return
	 */
	public boolean removeZTimer(ZTimer timer){
		if(null == timer){
			return false;
		}
		timer.setTiming(null);
		return listZTimer.remove(timer);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public ZTimer removeZTimer(int index){
		if(index >= 0){
			ZTimer timer = listZTimer.remove(index);
			if(null != timer) {
				timer.setTiming(null);
				return timer;
			}
		}
		return null;
	}
	
	/**
	 * get all condition result
	 * @return true if all condition is pass
	 */
	@Override
	@JsonIgnore
	public boolean getConditionResult(){
		Calendar c = Calendar.getInstance();
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		long ms = MyTime.getSec(hour, minute, second);
		boolean timed = false;
		for(ZTimer timer : listZTimer){
			if(!timer.isEnable()){
				continue;
			}
			if(timer.getWeekHelper().isSelected(week)){
				long ont = timer.getOnTime().getSec();
				long offt = timer.getOffTime().getSec();
				if(ms > ont && ms < offt){
					timed = true;
					break;
				}
			}
		}
		return  timed;
	}
	
	/**
	 * 
	 */
	@Override
	public void run(){
		if(!isEnable() || listZTimer.isEmpty() 
				|| getListEffect().isEmpty()){
			return;
		}
		//Log.e("Chain", "Chain is run");
		if(getConditionResult()){
			for(Effect effect : getListEffect()) {
				effect.setDsId(DevStateHelper.DS_KAI);
			}
		}else {
			for(Effect effect : getListEffect()) {
				effect.setDsId(DevStateHelper.DS_GUAN);
			}
		}
		effectLinkageTab(LinkageTab.TIMING);
	}
	
}
