package com.bairock.iot.intelDev.device.devcollect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.user.Util;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class CollectProperty {

	@Id
	@Column(nullable = false)
	private String id;

	@OneToOne
	@JsonBackReference("devcollect_property")
	// @JoinColumn(name="devCollect_id", insertable=true, unique=true)
	private DevCollect devCollect;

	// max value
	private Float crestValue = 100f;
	// max value refer to show value
	private Float crestReferValue = 100f;
	// min value
	private Float leastValue = 0f;
	// min value refer to show value
	private Float leastReferValue = 0f;
	// current value
	private Float currentValue = 0f;
	private Float calibrationValue = 0f;
	private Float percent = 0f;

	@Transient
	@JsonIgnore
	private float simulatorValue = 0f;

	private String formula;
	private String unitSymbol;
	private CollectSignalSource collectSrc = CollectSignalSource.DIGIT;
	
	@OneToMany(mappedBy = "collectProperty", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("collector_trigger")
	private List<ValueTrigger> listValueTrigger = new ArrayList<>();

	@Transient
	@JsonIgnore
	private Set<OnCurrentValueChangedListener> setOnCurrentValueChanged = new HashSet<>();
	
	@Transient
	@JsonIgnore
	private OnUnitSymbolChangedListener onUnitSymbolChangedListener;

	@Transient
	@JsonIgnore
	private OnSignalSourceChangedListener onSignalSourceChangedListener;

	@Transient
	@JsonIgnore
	private OnSimulatorChangedListener onSimulatorChangedListener;
	
	@Transient
	@JsonIgnore
	private OnValueTriggedListener onValueTriggedListener;
	
	public CollectProperty() {
		id = UUID.randomUUID().toString();
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public DevCollect getDevCollect() {
		return devCollect;
	}

	public void setDevCollect(DevCollect devCollect) {
		this.devCollect = devCollect;
	}

	public Set<OnCurrentValueChangedListener> getSetOnCurrentValueChanged() {
		return setOnCurrentValueChanged;
	}

	public void setSetOnCurrentValueChanged(Set<OnCurrentValueChangedListener> setOnCurrentValueChanged) {
		if(null != setOnCurrentValueChanged) {
			this.setOnCurrentValueChanged = setOnCurrentValueChanged;
		}
	}
	
	public void addOnCurrentValueChangedListener(OnCurrentValueChangedListener listener) {
		if(null != listener && !setOnCurrentValueChanged.contains(listener)) {
			setOnCurrentValueChanged.add(listener);
		}
	}
	
	public void removeOnCurrentValueChangedListener(OnCurrentValueChangedListener listener) {
		if(null != listener) {
			setOnCurrentValueChanged.remove(listener);
		}
	}

	public OnSignalSourceChangedListener getOnSignalSourceChangedListener() {
		return onSignalSourceChangedListener;
	}

	public void setOnSignalSourceChangedListener(OnSignalSourceChangedListener onSignalSourceChangedListener) {
		this.onSignalSourceChangedListener = onSignalSourceChangedListener;
	}

	public OnSimulatorChangedListener getOnSimulatorChangedListener() {
		return onSimulatorChangedListener;
	}

	public void setOnSimulatorChangedListener(OnSimulatorChangedListener onSimulatorChangedListener) {
		this.onSimulatorChangedListener = onSimulatorChangedListener;
	}

	public void setOnUnitSymbolChangedListener(OnUnitSymbolChangedListener onUnitSymbolChangedListener) {
		this.onUnitSymbolChangedListener = onUnitSymbolChangedListener;
	}
	
	public void setOnValueTriggedListener(OnValueTriggedListener onValueTriggedListener) {
		this.onValueTriggedListener = onValueTriggedListener;
	}

	/**
	 * get max value
	 * 
	 * @return
	 */
	public Float getCrestValue() {
		return crestValue;
	}

	/**
	 * get max value
	 * 
	 * @param crestValue
	 */
	public void setCrestValue(Float crestValue) {
		if (this.crestValue != crestValue) {
			this.crestValue = crestValue;
			if (null != onSignalSourceChangedListener) {
				onSignalSourceChangedListener.onSignalSourceChanged(devCollect);
			}
		}
	}

	public Float getCrestReferValue() {
		return crestReferValue;
	}

	public void setCrestReferValue(Float crestReferValue) {
		this.crestReferValue = crestReferValue;
	}

	/**
	 * get min value
	 * 
	 * @return
	 */
	public Float getLeastValue() {
		return leastValue;
	}

	/**
	 * set min value
	 * 
	 * @param leastValue
	 */
	public void setLeastValue(Float leastValue) {
		if (this.leastValue != leastValue) {
			this.leastValue = leastValue;
			if (null != onSignalSourceChangedListener) {
				onSignalSourceChangedListener.onSignalSourceChanged(devCollect);
			}
		}
	}

	public Float getLeastReferValue() {
		return leastReferValue;
	}

	public void setLeastReferValue(Float leastReferValue) {
		this.leastReferValue = leastReferValue;
	}

	/**
	 * 
	 * @return
	 */
	public Float getCurrentValue() {
		return currentValue;
	}

	/**
	 * 
	 * @param currentValue
	 */
	public void setCurrentValue(Float currentValue) {
		if (this.currentValue == null) {
			this.currentValue = currentValue;
			for(OnCurrentValueChangedListener onCurrentValueChanged : setOnCurrentValueChanged) {
				onCurrentValueChanged.onCurrentValueChanged(devCollect, currentValue);
			}
			trigging(this.currentValue);
		} else {
			if (null == currentValue) {
				this.currentValue = currentValue;
				for(OnCurrentValueChangedListener onCurrentValueChanged : setOnCurrentValueChanged) {
					onCurrentValueChanged.onCurrentValueChanged(devCollect, currentValue);
				}
				trigging(this.currentValue);
			} else if (Math.abs(this.currentValue - currentValue) > 0.01f) {
				this.currentValue = currentValue;
				for(OnCurrentValueChangedListener onCurrentValueChanged : setOnCurrentValueChanged) {
					onCurrentValueChanged.onCurrentValueChanged(devCollect, currentValue);
				}
				trigging(this.currentValue);
			}
		}
	}

	private void trigging(Float currentValue) {
		if(null != currentValue) {
			for(ValueTrigger trigger : listValueTrigger) {
				trigger.triggering(this.currentValue);
			}
		}
	}
	
	/**
	 * 
	 * @param currentValue
	 */
	public void setCurrentValueExceptListener(Float currentValue) {
		if (this.currentValue == null) {
			this.currentValue = currentValue;
		} else {
			if (null == currentValue) {
				this.currentValue = currentValue;
			} else if (Math.abs(this.currentValue - currentValue) > 0.01f) {
				this.currentValue = currentValue;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public Float getPercent() {
		return percent;
	}

	/**
	 * 
	 * @param percent
	 */
	public void setPercent(Float percent) {
		this.percent = percent;
	}

	/**
	 * 
	 * @return
	 */
	public String getUnitSymbol() {
		if(null == unitSymbol) {
			unitSymbol = "";
		}
		return unitSymbol;
	}

	/**
	 * 
	 * @param unitSymbol
	 */
	public void setUnitSymbol(String unitSymbol) {
		if(null == this.unitSymbol || !this.unitSymbol.equals(unitSymbol)) {
			this.unitSymbol = unitSymbol;
			if(null != onUnitSymbolChangedListener) {
				onUnitSymbolChangedListener.onUnitSymbolChanged(devCollect, unitSymbol);
			}
		}
	}

	/**
	 * get signal source of this device
	 * 
	 * @return
	 */
	public CollectSignalSource getCollectSrc() {
		return collectSrc;
	}

	/**
	 * set signal source of this device
	 * 
	 * @param collectSrc
	 */
	public void setCollectSrc(CollectSignalSource collectSrc) {
		if (this.collectSrc != collectSrc) {
			this.collectSrc = collectSrc;
			if (collectSrc == CollectSignalSource.ELECTRIC_CURRENT) {
				setLeastValue(4f);
				setCrestValue(20f);
			}
			if (null != onSignalSourceChangedListener) {
				onSignalSourceChangedListener.onSignalSourceChanged(devCollect);
			}
		}
	}

	public Float getCalibrationValue() {
		return calibrationValue;
	}

	public void setCalibrationValue(Float calibrationValue) {
		this.calibrationValue = calibrationValue;
	}

	public String getFormula() {
		if (formula == null || formula.isEmpty()) {
			switch (collectSrc) {
			case ELECTRIC_CURRENT:
				formula = "A4+(A-4)/(20-4)*(A20-A4)";
				break;
			case VOLTAGE:
				formula = "Aa+(A-a)/(b-a)*(Ab-Aa)";
				break;
			case DIGIT:
			case SWITCH:
				formula = "A";
				break;
			default:
				break;
			}
		}
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public float getSimulatorValue() {
		return simulatorValue;
	}

	public void setSimulatorValue(float simulatorValue) {
		if (this.simulatorValue != simulatorValue) {
			this.simulatorValue = simulatorValue;
			if (null != onSimulatorChangedListener) {
				onSimulatorChangedListener.onSimulatorChanged(devCollect, simulatorValue);
			}
		}
	}

	public List<ValueTrigger> getListValueTrigger() {
		return listValueTrigger;
	}

	public void setListValueTrigger(List<ValueTrigger> listValueTrigger) {
		if(null == listValueTrigger) {
			return;
		}
		this.listValueTrigger.clear();
		for(ValueTrigger trigger : listValueTrigger) {
			addValueTrigger(trigger);
		}
	}

	public void addValueTrigger(ValueTrigger trigger) {
		if(null != trigger) {
			trigger.setCollectProperty(this);
			//添加值变化触发监听器
			trigger.setOnTriggedChangedListener(new ValueTrigger.OnTriggedChangedListener() {
				
				@Override
				public void onTriggedChanged(ValueTrigger trigger, boolean trigged) {
					if(trigged) {
						//触发事件
						if(trigger.isEnable()) {
							if(null != onValueTriggedListener) {
								onValueTriggedListener.onValueTrigged(trigger, currentValue);
							}
						}
					}else {
						//触发事件解除
						if(null != onValueTriggedListener) {
							onValueTriggedListener.onValueTriggedRelieve(trigger, currentValue);
						}
					}
					
				}
			});
			listValueTrigger.add(trigger);
		}
	}
	
	public void removeValueTrigger(ValueTrigger trigger) {
		if(null != trigger) {
			trigger.setCollectProperty(null);
			listValueTrigger.remove(trigger);
		}
	}
	
	public void removeValueTriggerAt(int index) {
		if(index >= 0 && index < listValueTrigger.size()) {
			ValueTrigger trigger = listValueTrigger.get(index);
			removeValueTrigger(trigger);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getValueWithSymbol() {
		if (getCurrentValue() != null) {
			if (unitSymbol != null) {
				return getCurrentValue() + unitSymbol;
			} else {
				return String.valueOf(getCurrentValue());
			}
		}
		return "0";
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getPercentWithSymbol() {
		if (getPercent() != null) {
			return getPercent() + "%";
		}
		return null;
	}
	
	/**
	 * 获取格式化后的字符串值
	 * @return
	 */
	public String createFormatValue() {
	    if(null == currentValue) {
	        currentValue = 0f;
	    }
	    return Util.format2TwoScale(currentValue);
	}

	public interface OnCurrentValueChangedListener {
		void onCurrentValueChanged(DevCollect dev, Float value);
	}
	
	public interface OnUnitSymbolChangedListener {
		void onUnitSymbolChanged(DevCollect dev, String unitSymbol);
	}

	public interface OnSignalSourceChangedListener {
		void onSignalSourceChanged(DevCollect dev);
	}

	public interface OnSimulatorChangedListener {
		void onSimulatorChanged(DevCollect dev, Float simulator);
	}
	
	public interface OnValueTriggedListener {
		/**
		 * 触发事件
		 * @param trigger
		 * @param value
		 */
		void onValueTrigged(ValueTrigger trigger, float value);
		/**
		 * 触发事件解除
		 * @param trigger
		 * @param value
		 */
		void onValueTriggedRelieve(ValueTrigger trigger, float value);
	}

	public void initTriggerListener() {
		for(ValueTrigger vt : getListValueTrigger()) {
			vt.setOnTriggedChangedListener(onTriggedChangedListener);
		}
	}
	
	@Transient
	@JsonIgnore
	public ValueTrigger.OnTriggedChangedListener onTriggedChangedListener = new ValueTrigger.OnTriggedChangedListener() {
		
		@Override
		public void onTriggedChanged(ValueTrigger trigger, boolean trigged) {
			if(trigged) {
				//触发事件
				if(trigger.isEnable()) {
					if(null != onValueTriggedListener) {
						onValueTriggedListener.onValueTrigged(trigger, currentValue);
					}
				}
			}else {
				//触发事件解除
				if(null != onValueTriggedListener) {
					onValueTriggedListener.onValueTriggedRelieve(trigger, currentValue);
				}
			}
			
		}
	};
}
