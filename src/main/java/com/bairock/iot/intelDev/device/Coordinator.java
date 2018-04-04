package com.bairock.iot.intelDev.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

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
public class Coordinator extends DevHaveChild {

	private String panid;

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
	public boolean handle(String strState) {
		if (null != strState) {
			if (strState.length() < 2) {
				return false;
			}

			String head = strState.substring(0, 1);
			String value = strState.substring(1);
			switch (head) {
			case CtrlCodeHelper.DCT_XIETIAO_PANID:
				setPanid(value);
				break;
			case CtrlCodeHelper.DCT_NORMAL:
				setDevStateId(DevStateHelper.DS_YI_CHANG);
				break;
			}
		}
		return super.handle(strState);
	}
}
