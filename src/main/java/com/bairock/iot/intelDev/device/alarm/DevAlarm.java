package com.bairock.iot.intelDev.device.alarm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 报警设备
 * @author 44489
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevAlarm")
public class DevAlarm extends Device {

	@OneToOne(mappedBy = "devAlarm", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("devalarm_trigger")
	private AlarmTrigger trigger;
	
	@Transient
	@JsonIgnore
	private long lastTriggedTime;
	
	@Transient
	@JsonIgnore
	private Set<OnAlarmTriggedListener> setOnAlarmTriggedListener = new HashSet<>();
	
	public DevAlarm() {
	}

	public DevAlarm(String mcId, String sc) {
		super(mcId, sc);
		trigger = new AlarmTrigger();
		trigger.setDevAlarm(this);
	}

	public AlarmTrigger getTrigger() {
		return trigger;
	}

	public void setTrigger(AlarmTrigger trigger) {
		if(null == trigger) {
			return;
		}
		this.trigger = trigger;
		this.trigger.setDevAlarm(this);
	}

	/**
	 * 设置报警触发监听事件
	 * @param onAlarmTriggedListener
	 */
	public void setSetOnAlarmTriggedListener(Set<OnAlarmTriggedListener> setOnAlarmTriggedListener) {
		if(null != setOnAlarmTriggedListener) {
			this.setOnAlarmTriggedListener = setOnAlarmTriggedListener;
		}
	}

	public void addOnAlarmTriggedListener(OnAlarmTriggedListener listener) {
		if(null != listener) {
			setOnAlarmTriggedListener.add(listener);
		}
	}
	
	public void removeOnAlarmTriggedListener(OnAlarmTriggedListener listener) {
		if(null != listener) {
			setOnAlarmTriggedListener.remove(listener);
		}
	}
	
	/**
	 * 最后一次触发到这次触发的时间间隔
	 * @return
	 */
	private long lastTriggedInterval() {
		return System.currentTimeMillis() - lastTriggedTime;
	}
	
	@Override
	public void handleSingleMsg(String singleMsg) {
		if(singleMsg.startsWith("3")) {
			long interval = lastTriggedInterval();
			lastTriggedTime = System.currentTimeMillis();
			if(interval > 5000) {
				for(OnAlarmTriggedListener onAlarmTriggedListener : setOnAlarmTriggedListener) {
					onAlarmTriggedListener.onAlarmTrigged(getTrigger());
				}
			}
		}
	}
	
	public interface OnAlarmTriggedListener {
		void onAlarmTrigged(AlarmTrigger trigger);
	}
	
}
