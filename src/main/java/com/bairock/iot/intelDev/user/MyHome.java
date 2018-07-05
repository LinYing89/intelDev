package com.bairock.iot.intelDev.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.bairock.iot.intelDev.device.alarm.AlarmInfo;

@MappedSuperclass
public abstract class MyHome {

	@Id
	@Column(nullable = false)
	protected String id;

	@OneToMany(cascade=CascadeType.ALL)  
    @JoinColumn(name="myHomeId") 
	@OrderBy(value = "alarmTime desc")
	private List<AlarmInfo> listAlarmInfo = new ArrayList<>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<AlarmInfo> getListAlarmInfo() {
		return listAlarmInfo;
	}

	public void setListAlarmInfo(List<AlarmInfo> listAlarmInfo) {
		if(null != listAlarmInfo) {
			this.listAlarmInfo = listAlarmInfo;
		}
	}

	public void addAlarmInfo(AlarmInfo alarmInfo) {
		listAlarmInfo.add(alarmInfo);
	}
	
	public void removeAlarmInfo(AlarmInfo alarmInfo) {
		listAlarmInfo.remove(alarmInfo);
	}
	
	public void removeAlarmInfo(int index) {
		listAlarmInfo.remove(index);
	}
	
}
