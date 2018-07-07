package com.bairock.iot.intelDev.device.alarm;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 报警信息
 * @author 44489
 *
 */
@Entity
public class AlarmInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String info;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date alarmTime;

	/**
	 * 报警对象的名称
	 */
	private String sourceName = "";
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		if(null != sourceName) {
			this.sourceName = sourceName;
		}
	}
	
}
