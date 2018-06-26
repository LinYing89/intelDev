package com.bairock.iot.intelDev.device.devcollect;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ValueTrigger {

	@Id
	@Column(nullable = false)
	private String id;
	
	private String name;
	private boolean enable;
	
	@Transient
	@JsonIgnore
	private boolean trigged;
	
	@ManyToOne
	@JsonBackReference("device_trigger")
	private DevCollect device;
	private float triggerValue;
	private CompareSymbol compareSymbol = CompareSymbol.LESS;
	private String message = "";
	
	public ValueTrigger() {
		setId(UUID.randomUUID().toString());
	}
	
	public String getName() {
		if(null == name) {
			name = createDefaultName();
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public boolean isTrigged() {
		return trigged;
	}

	public void setTrigged(boolean trigged) {
		this.trigged = trigged;
	}

	public DevCollect getDevice() {
		return device;
	}
	public void setDevice(DevCollect device) {
		this.device = device;
	}
	public float getTriggerValue() {
		return triggerValue;
	}
	public void setTriggerValue(float triggerValue) {
		this.triggerValue = triggerValue;
	}
	public CompareSymbol getCompareSymbol() {
		return compareSymbol;
	}
	public void setCompareSymbol(CompareSymbol compareSymbol) {
		this.compareSymbol = compareSymbol;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean triggering(float value) {
		boolean trigging = false;
		switch(compareSymbol) {
		case LESS:
			trigging = value < triggerValue;
			break;
		case EQUAL:
			trigging = Math.abs(triggerValue - value) < 0.01;
			break;
		case GREAT:
			trigging = value > triggerValue;
			break;
		}
		if(trigged) {
			// if trigged, don't trigger again until trigging is false to set trigged to false,
			//then can trigger again
			if(trigging) {
				trigging = false;
			}else {
				trigged = false;
			}
		}else {
			trigged = trigging;
		}
		return trigging;
	}
	
	@Override
	public String toString() {
		return "ValueTrigger [device=" + device.getLongCoding() + ", triggerValue=" + triggerValue + ", compareSymbol=" + compareSymbol
				+ ", message=" + message + "]";
	}
	
	
	public String createDefaultName() {
		String symbol = "=";
		switch(compareSymbol) {
		case LESS:
			symbol = "<";
			break;
		case EQUAL:
			symbol = "=";
			break;
		case GREAT:
			symbol = ">";
			break;
		}
		return symbol + " " + triggerValue;
	}
	
}
