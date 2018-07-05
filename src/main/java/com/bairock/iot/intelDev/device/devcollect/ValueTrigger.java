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
	private boolean trigged = false;
	
	@ManyToOne
	@JsonBackReference("collector_trigger")
	private CollectProperty collectProperty;
	
	private float triggerValue;
	private CompareSymbol compareSymbol = CompareSymbol.LESS;
	private String message = "";
	
	@Transient
	@JsonIgnore
	private OnTriggedChangedListener onTriggedChangedListener;
	
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
		if(this.trigged != trigged) {
			this.trigged = trigged;
			if(null != onTriggedChangedListener) {
				onTriggedChangedListener.onTriggedChanged(this, trigged);
			}
		}
	}
	
	public CollectProperty getCollectProperty() {
		return collectProperty;
	}

	public void setCollectProperty(CollectProperty collectProperty) {
		this.collectProperty = collectProperty;
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
		if(message == null || message.isEmpty()) {
			message = getName();
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public void setOnTriggedChangedListener(OnTriggedChangedListener onTriggedChangedListener) {
		this.onTriggedChangedListener = onTriggedChangedListener;
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
				setTrigged(false);
			}
		}else {
			setTrigged(trigging);
		}
		return trigging;
	}
	
	@Override
	public String toString() {
		return "ValueTrigger [device=" + collectProperty.getDevCollect().getLongCoding() + ", triggerValue=" + triggerValue + ", compareSymbol=" + compareSymbol
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
	
	/**
	 * 已触发标志改变事件
	 * 由未触发到触发，或由已触发到未触发
	 * @author 44489
	 *
	 */
	public interface OnTriggedChangedListener {
		/**
		 * 已触发标志改变事件
		 * 由未触发到触发，或由已触发到未触发
		 * @param trigger
		 * @param trigged true为由未触发变为触发，false为由触发变为未触发
		 */
		void onTriggedChanged(ValueTrigger trigger, boolean trigged);
	}
	
}
