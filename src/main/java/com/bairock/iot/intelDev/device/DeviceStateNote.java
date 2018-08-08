package com.bairock.iot.intelDev.device;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: DeviceStateNote
 *
 */
@Entity
public class DeviceStateNote implements Serializable {

	@Id
	@Column(nullable = false)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "dev_id")
	private Device device;
	
	private int state;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date noteTime;
	
	private static final long serialVersionUID = 1L;

	public DeviceStateNote() {
		super();
	}   
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}   
	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}   
	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}
	public Date getNoteTime() {
		return noteTime;
	}
	public void setNoteTime(Date noteTime) {
		this.noteTime = noteTime;
	}
   
}
