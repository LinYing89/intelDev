package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.CtrlCodeHelper;
import com.bairock.iot.intelDev.device.CtrlModel;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.IStateDev;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.DevGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("SubDev")
public class SubDev extends Device implements IStateDev{
	
	public SubDev() {
		super();
	}

	public SubDev(String mcId, String sc) {
		super(mcId, sc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCoding() {
		return getParent().getCoding() + "_" + MainCodeHelper.getIns().getMc(getMainCodeId()) + "_" + getSubCode();
	}
	
	@Override
	public DevGroup getDevGroup() {
		return getParent().getDevGroup();
	}

	@Override
	public CtrlModel getCtrlModel() {
		return getParent().getCtrlModel();
	}

	@Override
	public boolean isNormal() {
		return getParent().isNormal();
	}

	@Override
	public void turnOn() {
		setDevStateId(DevStateHelper.DS_KAI);
	}

	@Override
	public void turnOff() {
		setDevStateId(DevStateHelper.DS_GUAN);
	}

	@Override
	@JsonIgnore
	public String getTurnOnOrder() {
		return getDevOrder(OrderHelper.CTRL_HEAD, CtrlCodeHelper.DCT_KAIGUAN_KAI);
	}

	@Override
	@JsonIgnore
	public String getTurnOffOrder() {
		return getDevOrder(OrderHelper.CTRL_HEAD, CtrlCodeHelper.DCT_KAIGUAN_GUAN);
	}

	@Override
	@JsonIgnore
	public String getDevOrder(String orderHead, String dctId) {
		if(null == orderHead) {
			orderHead = "";
		}
		int road = Integer.parseInt(getSubCode()) - 1;
		String order = orderHead + getParent().getCoding() + ":" 
				+ CtrlCodeHelper.getIns().getDct(dctId) + Integer.toHexString(road);
		return OrderHelper.getOrderMsg(order);
	}
}
