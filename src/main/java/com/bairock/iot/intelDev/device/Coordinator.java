package com.bairock.iot.intelDev.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * xie tiao qi
 * 
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Coordinator")
public class Coordinator extends DevContainer {

	private String panid;
	
	@Transient
	@JsonIgnore
	private boolean isConfigingChildDevice = false;

	/**
	 * 
	 */
	public Coordinator() {
		this(MainCodeHelper.SMC_WU, "1");
	}

	/**
	 * 
	 * @param mcId
	 * @param sc
	 */
	public Coordinator(String mcId, String sc) {
		super(mcId, sc);
	}

	/**
	 * 
	 * @return
	 */
	public String getPanid() {
		return panid;
	}

	/**
	 * 
	 * @param panid
	 */
	public void setPanid(String panid) {
		this.panid = panid;
	}

	public boolean isConfigingChildDevice() {
		return isConfigingChildDevice;
	}

	public void setConfigingChildDevice(boolean isConfigingChildDevice) {
		this.isConfigingChildDevice = isConfigingChildDevice;
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getQueryPanidOrder() {
		String order = OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR
				+ CtrlCodeHelper.getIns().getDct(CtrlCodeHelper.DCT_XIETIAO_PANID);
		return order;
	}

	/**
	 * 
	 * @param panid
	 * @return
	 */
	@JsonIgnore
	public String getSetPanidOrder(String panid) {
		if (null == panid) {
			return null;
		}
		String order = OrderHelper.SET_HEAD + getCoding() + OrderHelper.SEPARATOR
				+ CtrlCodeHelper.getIns().getDct(CtrlCodeHelper.DCT_XIETIAO_PANID) + panid;
		return order;
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getResetOrder() {
		String order = OrderHelper.CTRL_HEAD + getCoding() + OrderHelper.SEPARATOR
				+ CtrlCodeHelper.getIns().getDct(CtrlCodeHelper.DCT_XIETIAO_RESET);
		return order;
	}

	@Override
	public void handleSingleMsg(String strState) {
		if (null != strState) {
			if (strState.length() < 2) {
				return;
			}

			String head = strState.substring(0, 1);
			String value = strState.substring(1);
			String dctId = CtrlCodeHelper.getIns().getDctId(head);
			switch (dctId) {
			case CtrlCodeHelper.DCT_XIETIAO_PANID:
				setPanid(value);
				break;
			case CtrlCodeHelper.DCT_NORMAL:
				break;
			}
		}
	}
}
