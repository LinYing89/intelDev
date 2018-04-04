package com.bairock.iot.intelDev.device.devcollect;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CollectProperty {

	@Id
	@Column(nullable = false)
	private String id;
	
	@OneToOne
	@JsonBackReference("devcollect_property")
//	@JoinColumn(name="devCollect_id", insertable=true, unique=true)
	private DevCollect devCollect;
	
	//max value
	private Float crestValue;
	//max value refer to show value
	private Float crestReferValue;
	//min value
	private Float leastValue = 0f;
	//min value refer to show value
	private Float leastReferValue = 0f;
	//current value
	private Float currentValue;
	private Float percent;
	private String unitSymbol;
	private CollectSignalSource collectSrc;
	
	@Transient
	@JsonIgnore
	private OnCurrentValueChangedListener onCurrentValueChanged;
	
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

	public OnCurrentValueChangedListener getOnCurrentValueChanged() {
		return onCurrentValueChanged;
	}

	public void setOnCurrentValueChanged(OnCurrentValueChangedListener onCurrentValueChanged) {
		this.onCurrentValueChanged = onCurrentValueChanged;
	}

	/**
	 * get max value
	 * @return
	 */
	public Float getCrestValue() {
		return crestValue;
	}
	
	/**
	 * get max value
	 * @param crestValue
	 */
	public void setCrestValue(Float crestValue) {
		this.crestValue = crestValue;
	}
	
	public Float getCrestReferValue() {
		return crestReferValue;
	}

	public void setCrestReferValue(Float crestReferValue) {
		this.crestReferValue = crestReferValue;
	}
	
	/**
	 * get min value
	 * @return
	 */
	public Float getLeastValue() {
		return leastValue;
	}
	
	/**
	 * set min value
	 * @param leastValue
	 */
	public void setLeastValue(Float leastValue) {
		this.leastValue = leastValue;
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
		if(this.currentValue != currentValue){
			this.currentValue = currentValue;
			if(null != onCurrentValueChanged){
				onCurrentValueChanged.onCurrentValueChanged(devCollect, currentValue);
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
		return unitSymbol;
	}
	
	/**
	 * 
	 * @param unitSymbol
	 */
	public void setUnitSymbol(String unitSymbol) {
		this.unitSymbol = unitSymbol;
	}
	
	/**
	 * get signal source of this device
	 * @return
	 */
	public CollectSignalSource getCollectSrc() {
		return collectSrc;
	}

	/**
	 * set signal source of this device
	 * @param collectSrc
	 */
	public void setCollectSrc(CollectSignalSource collectSrc) {
		this.collectSrc = collectSrc;
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getValueWithSymbol(){
		if(getCurrentValue() != null){
			if(unitSymbol != null){
				return getCurrentValue() + unitSymbol;
			}else{
				return String.valueOf(getCurrentValue());
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getPercentWithSymbol(){
		if(getPercent() != null){
			return getPercent() + "%";
		}
		return null;
	}
	
	public interface OnCurrentValueChangedListener{
		void onCurrentValueChanged(DevCollect dev, Float value);
	}
	
}
