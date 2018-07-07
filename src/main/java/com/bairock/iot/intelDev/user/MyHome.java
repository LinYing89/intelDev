package com.bairock.iot.intelDev.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.alarm.AlarmInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class MyHome {

	@Id
	@Column(nullable = false)
	private String id;

	
	@OneToMany(cascade=CascadeType.ALL)  
    @JoinColumn(name="myHomeId") 
	@OrderBy(value = "alarmTime desc")
	private List<AlarmInfo> listAlarmInfo = new ArrayList<>();
	
	protected String name = "";
	
	@Transient
	@JsonIgnore
	private Set<OnNameChangedListener> stOnNameChangedListener = new HashSet<>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (null != name && !this.name.equals(name)) {
			this.name = name;
			for (OnNameChangedListener listener : stOnNameChangedListener) {
				listener.onNameChanged(this, name);
			}
		}
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
	
	public void addOnNameChangedListener(OnNameChangedListener listener) {
		stOnNameChangedListener.add(listener);
	}

	public void removeOnNameChangedListener(OnNameChangedListener listener) {
		stOnNameChangedListener.remove(listener);
	}
	public interface OnNameChangedListener {
		void onNameChanged(MyHome myHome, String name);
	}
}
