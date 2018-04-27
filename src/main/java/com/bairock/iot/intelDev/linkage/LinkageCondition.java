package com.bairock.iot.intelDev.linkage;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.IStateDev;
import com.bairock.iot.intelDev.device.devcollect.CollectProperty;
import com.bairock.iot.intelDev.device.devcollect.DevCollect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * condition of chain
 * 
 * @author LinQiang
 *
 */
@Entity
public class LinkageCondition {

	@Id
	@Column(nullable = false)
	private String id;

	@ManyToOne
	@JsonBackReference("subchain_linkagecondition")
	private SubChain subChain;

	private ZLogic logic = ZLogic.OR;
	private CompareSymbol compareSymbol = CompareSymbol.EQUAL;

	@ManyToOne
	@JoinColumn(name = "dev_id", foreignKey = @ForeignKey(name = "DEV_ID_FK"))
	private Device device;

	private float compareValue;
	private boolean deleted;
	private TriggerStyle triggerStyle = TriggerStyle.VALUE;
	
	@Transient
	@JsonIgnore
	private OnCompareResultChangedListener onCompareResultChangedListener;
	
	@Transient
	@JsonIgnore
	private int compareResult = -1;

	public LinkageCondition() {
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

	public SubChain getSubChain() {
		return subChain;
	}

	public void setSubChain(SubChain subChain) {
		this.subChain = subChain;
	}

	/**
	 * get logic, or\and
	 * 
	 * @return
	 */
	public ZLogic getLogic() {
		return logic;
	}

	/**
	 * set logic, or\and
	 * 
	 * @param logic
	 */
	public void setLogic(ZLogic logic) {
		this.logic = logic;
	}

	/**
	 * get compare symbol
	 * 
	 * @return
	 */
	public CompareSymbol getCompareSymbol() {
		return compareSymbol;
	}

	/**
	 * set compare symbol
	 * 
	 * @param compareSymbol
	 */
	public void setCompareSymbol(CompareSymbol compareSymbol) {
		this.compareSymbol = compareSymbol;
	}

	/**
	 * get device of condition
	 * 
	 * @return
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * set device of condition
	 * 
	 * @param device
	 */
	public void setDevice(Device device) {
		this.device = device;
		if (null != device) {
			if (device instanceof DevCollect) {
				((DevCollect) device).getCollectProperty()
						.addOnCurrentValueChanged(new CollectProperty.OnCurrentValueChangedListener() {

							@Override
							public void onCurrentValueChanged(DevCollect dev, Float value) {
								conclutionResult(value);
							}
						});
			} else {
				device.addOnStateChangedListener(new Device.OnStateChangedListener() {

					@Override
					public void onStateChanged(Device dev, String stateId) {
						conclutionResult(Float.parseFloat((dev.getDevState())));
					}

					@Override
					public void onNormalToAbnormal(Device dev) {
						compareResult = -1;
					}

					@Override
					public void onNoResponse(Device dev) {
					}

					@Override
					public void onAbnormalToNormal(Device dev) {
					}
				});
			}
		}
	}

	/**
	 * get compare value
	 * 
	 * @return
	 */
	public float getCompareValue() {
		return compareValue;
	}

	/**
	 * set compare value
	 * 
	 * @param value
	 */
	public void setCompareValue(float value) {
		this.compareValue = value;
	}

	/**
	 * is deleted
	 * 
	 * @return
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * set deleted
	 * 
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public TriggerStyle getTriggerStyle() {
		return triggerStyle;
	}

	public void setTriggerStyle(TriggerStyle triggerStyle) {
		this.triggerStyle = triggerStyle;
	}

	public OnCompareResultChangedListener getOnCompareResultChangedListener() {
		return onCompareResultChangedListener;
	}

	public void setOnCompareResultChangedListener(OnCompareResultChangedListener onCompareResultChangedListener) {
		this.onCompareResultChangedListener = onCompareResultChangedListener;
	}

	
	public int getCompareResult() {
		return compareResult;
	}

	public void setCompareResult(int compareResult) {
		this.compareResult = compareResult;
	}

	private void conclutionResult(Float value) {
		if(null == value) {
			return;
		}
		boolean compara = false;
		switch (compareSymbol) {
		case GREAT:
			compara = value > getCompareValue();
			break;
		case EQUAL:
			compara = Math.abs(value - getCompareValue()) < 0.005;
			break;
		case LESS:
			compara = value < getCompareValue();
			break;
		}
		//1 is established, 0 is not established
		int result = compara ? 1 : 0;
		if(result != compareResult) {
			if(null != onCompareResultChangedListener) {
				onCompareResultChangedListener.onCompareResultChanged(result);
			}
		}
	}
	
	/**
	 * the result of condition
	 * 
	 * @return 1 is success, 0 is fail, null is no result
	 */
	@JsonIgnore
	public Integer getResult() {
		Integer result = null;
		if (null == getDevice()) {
			return result;
		}
		float value = 0;
		if (device instanceof IStateDev) {
			value = device.isKaiState() ? 1 : 0;
		} else if (device instanceof DevCollect) {
			DevCollect cd = (DevCollect) device;
			Float fValue = null;
			// if(getTriggerStyle() == TriggerStyle.PERCENT) {
			// fValue = cd.getCollectProperty().getPercent();
			// }else {
			// fValue = cd.getCollectProperty().getCurrentValue();
			// }
			fValue = cd.getCollectProperty().getCurrentValue();
			if (fValue == null) {
				return null;
			}
			value = fValue;
		}
		boolean compara = false;
		switch (compareSymbol) {
		case GREAT:
			compara = value > getCompareValue();
			break;
		case EQUAL:
			compara = value == getCompareValue();
			break;
		case LESS:
			compara = value < getCompareValue();
			break;
		}
		result = compara ? 1 : 0;
		// System.out.println("resulr:" + result);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof LinkageCondition)) {
			return false;
		}
		LinkageCondition lc = (LinkageCondition) obj;
		if (lc.getCompareSymbol() == getCompareSymbol() && lc.getDevice().equals(getDevice())
				&& lc.getLogic() == getLogic() && lc.getCompareValue() == getCompareValue()) {
			return true;
		}
		return false;
	}
	
	public interface OnCompareResultChangedListener {
		void onCompareResultChanged(int result);
	}
}
