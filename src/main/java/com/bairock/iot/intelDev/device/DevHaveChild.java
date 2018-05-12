package com.bairock.iot.intelDev.device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevHaveChild")
public class DevHaveChild extends Device {

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("devparent_child")
	private List<Device> listDev = Collections.synchronizedList(new ArrayList<>());

	@Transient
	@JsonIgnore
	private Set<OnDeviceCollectionChangedListener> stOnDeviceCollectionChangedListener = new HashSet<>();
	/**
	 * 
	 */
	public DevHaveChild() {
		super();
	}

	/**
	 * 
	 * @param mcId
	 *            main code identify
	 * @param sc
	 *            sub code
	 */
	public DevHaveChild(String mcId, String sc) {
		super(mcId, sc);
	}

	public List<Device> getListDev() {
		return listDev;
	}

	public void setListDev(List<Device> listDev) {
		if(null == listDev) {
			return;
		}
		this.listDev.clear();
		for(Device dev : listDev) {
			addChildDev(dev);
		}
	}

	@Override
	public void setDevStateId(String dsId) {
		super.setDevStateId(dsId);
		if (dsId.equals(DevStateHelper.DS_YI_CHANG)) {
			for (Device dev : listDev) {
				dev.setDevStateId(dsId);
			}
		}
	}

	@Override
	public void setCtrlModel(CtrlModel ctrlModel) {
		super.setCtrlModel(ctrlModel);
		for (Device dev : listDev) {
			dev.setCtrlModel(ctrlModel);
		}
	}

	@Override
	public void setDeleted(boolean deleted) {
		super.setDeleted(deleted);
		for (Device dev : listDev) {
			dev.setDeleted(deleted);
		}
	}

	@Override
	public void setLinkType(LinkType linkType) {
		for (Device dev : listDev) {
			dev.setLinkType(linkType);
		}
		super.setLinkType(linkType);
	}

	public Device findDevByCoding(String coding){
		if(null == coding){
			return null;
		}
		for(Device dev : listDev){
			if(dev.getCoding().equals(coding)){
				return dev;
			}else if(dev instanceof DevHaveChild) {
				Device dd = ((DevHaveChild)dev).findDevByCoding(coding);
				if(null != dd) {
					return dd;
				}
			}
		}
		return null;
	}
	
	
	public Device findDeviceByMainCodeAndSubCode(String mainCode, String subCode){
		if(null == mainCode || null == subCode) {
			return null;
		}
		String mainCodeId = MainCodeHelper.getIns().getMcId(mainCode);
		return getDeviceByMainCodeIdAndSubCode(mainCodeId, subCode);
	}
	
	public Device getDeviceByMainCodeIdAndSubCode(String mainCodeId, String subCode){
		if(null == mainCodeId || null == subCode) {
			return null;
		}
		for(Device dev : listDev){
			if(dev.getMainCodeId().equals(mainCodeId) && dev.getSubCode().equals(subCode)){
				return dev;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param device
	 */
	public void addChildDev(Device device) {
		if (device != null) {
			device.setParent(this);
			listDev.add(device);
			for(OnDeviceCollectionChangedListener listener : stOnDeviceCollectionChangedListener) {
				listener.onAdded(device);
			}
		}
	}

	/**
	 * 
	 * @param device
	 */
	public void removeChildDev(Device device) {
		listDev.remove(device);
		if (null != device) {
			for(OnDeviceCollectionChangedListener listener : stOnDeviceCollectionChangedListener) {
				listener.onRemoved(device);
			}
			device.setParent(null);
		}
	}
	
	public void addOnDeviceCollectionChangedListener(OnDeviceCollectionChangedListener listener) {
		stOnDeviceCollectionChangedListener.add(listener);
	}
	
	public void removeOnDeviceCollectionChangedListener(OnDeviceCollectionChangedListener listener) {
		stOnDeviceCollectionChangedListener.remove(listener);
	}
	
	public interface OnDeviceCollectionChangedListener{
		void onAdded(Device device);
		void onRemoved(Device device);
	}

//	public static void main(String[] args) {
//		Coordinator coor = (Coordinator)DeviceAssistent.createDeviceByMcId(MainCodeHelper.XIE_TIAO_QI, "1");
//		DevSwitchThreeRoad dstr = (DevSwitchThreeRoad)DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_3LU_2TAI, "2");
//		coor.addChildDev(dstr);
//		System.out.println(coor.getDevByCoding(dstr.getCoding()));
//		System.out.println(dstr.getDevByCoding(dstr.getSubDevBySc("1").getCoding()));
//		System.out.println(coor.getDevByCoding(dstr.getSubDevBySc("1").getCoding()));
//	}
}
