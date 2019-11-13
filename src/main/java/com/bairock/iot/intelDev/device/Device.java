package com.bairock.iot.intelDev.device;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.alarm.DevAlarm;
import com.bairock.iot.intelDev.device.devcollect.DevCollect;
import com.bairock.iot.intelDev.device.devcollect.ValueTrigger;
import com.bairock.iot.intelDev.linkage.device.DeviceLinkage;
import com.bairock.iot.intelDev.user.DevGroup;
import com.bairock.iot.intelDev.user.MyHome;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Device")
@DiscriminatorColumn(name = "myhome_type", discriminatorType = DiscriminatorType.STRING)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class")
public class Device extends MyHome implements Comparable<Device>, IDevice {

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("group_dev")
	private DevGroup devGroup;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("devparent_child")
	private DevHaveChild parent;

	private String mainCodeId = "";
	private String subCode = "";
	private String sn = "";
	private DevCategory devCategory = DevCategory.WU;
	private String place = "";
	private String alias = "";
	private Gear gear = Gear.ZIDONG;
	private String devStateId = DevStateHelper.DS_YI_CHANG;
	private CtrlModel ctrlModel = CtrlModel.UNKNOW;
	private int sortIndex;
	private boolean visibility = true;
	private boolean deleted;
	
	@Transient
    @JsonIgnore
	protected String value = "0";

	@OneToMany(mappedBy = "sourceDevice", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("device_linkage")
	private List<DeviceLinkage> listDeviceLinkage = new ArrayList<>();
	
	@Transient
	@JsonIgnore
	private int noResponse = 1;

	@Transient
	@JsonIgnore
	private String lastOrder = "";

	@Transient
	@JsonIgnore
	private long lastCommunicationTime;

	@Transient
	@JsonIgnore
	private long lastResponseTime;

	@Transient
	@JsonIgnore
	private LinkType linkType = LinkType.NET;
	
	@Transient
	@JsonIgnore
	private boolean gearNeedToAuto = false;
	@Transient
	@JsonIgnore
	private String username = "";
	@Transient
	@JsonIgnore
	private String devGroupName = "";

	@Transient
	@JsonIgnore
	private Set<OnStateChangedListener> stOnStateChangedListener = new HashSet<>();

	@Transient
	@JsonIgnore
	private Set<OnGearChangedListener> stOnGearChangedListener = new HashSet<>();

	@Transient
	@JsonIgnore
	private Set<OnCtrlModelChangedListener> stOnCtrlModelChanged = new HashSet<>();

	@Transient
	@JsonIgnore
	private Set<OnAliasChangedListener> stOnAliasChangedListener = new HashSet<>();

	@Transient
	@JsonIgnore
	private OnSortIndexChangedListener onSortIndexChangedListener;
	
	@Transient
	@JsonIgnore
	private OnGearNeedToAutoListener onGearNeedToAutoListener;

	/**
	 * 
	 */
	public Device() {
		this(MainCodeHelper.SMC_WU, "1");
	}

	/**
	 * 
	 * @param mcId
	 *            main code identify
	 * @param sc
	 *            sub code
	 */
	public Device(String mcId, String sc) {
		setMainCodeId(mcId);
		setSubCode(sc);
		setId(UUID.randomUUID().toString());
	}

	/**
	 * 
	 * @return
	 */
	public DevGroup getDevGroup() {
		// return findSuperParent().getDevGroup();
		return devGroup;
	}

	/**
	 * 
	 * @param devGroup
	 */
	public void setDevGroup(DevGroup devGroup) {
		this.devGroup = devGroup;
	}

	/**
	 * 
	 * @return
	 */
	public DevHaveChild getParent() {
		return parent;
	}

	/**
	 * 
	 * @param parent
	 */
	public void setParent(DevHaveChild parent) {
		this.parent = parent;
	}

	/**
	 * get main code identify
	 * 
	 * @return
	 */
	public String getMainCodeId() {
		return mainCodeId;
	}

	/**
	 * set main code identify
	 * 
	 * @param mcId
	 */
	public void setMainCodeId(String mcId) {
		this.mainCodeId = mcId;
	}

	/**
	 * get sub code
	 * 
	 * @return
	 */
	public String getSubCode() {
		return subCode;
	}

	/**
	 * set sub code
	 * 
	 * @param sc
	 */
	public void setSubCode(String sc) {
		this.subCode = sc;
	}

	/**
	 * get serial number
	 * 
	 * @return
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * set serial number
	 * 
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * get device category
	 * 
	 * @return
	 */
	public DevCategory getDevCategory() {
		return devCategory;
	}

	/**
	 * set device category
	 * 
	 * @param dc
	 */
	public void setDevCategory(DevCategory dc) {
		this.devCategory = dc;
	}

	/**
	 * get place
	 * 
	 * @return
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * set place
	 * 
	 * @param place
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * get name
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		if (null == name || name.isEmpty()) {
			name = getCoding();
		}
		return name;
	}

	/**
	 * get alias
	 * 
	 * @return
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * set alias
	 * 
	 * @param alias
	 */
	public void setAlias(String alias) {
		if (alias != null && !this.alias.equals(alias)) {
			this.alias = alias;
			for (OnAliasChangedListener listener : stOnAliasChangedListener) {
				listener.onAliasChanged(this, alias);
			}
		}
	}

	/**
	 * get device gear
	 * 
	 * @return
	 */
	public Gear getGear() {
		return gear;
	}

	/**
	 * set device gear
	 * 
	 * @param gear
	 */
	public void setGear(Gear gear) {
		if (this.gear != gear) {
			this.gear = gear;
			for(OnGearChangedListener listener : stOnGearChangedListener) {
				listener.onGearChanged(this, gear, false);
			}
		}
	}
	
	public void setGear(Gear gear, boolean touchDev) {
		if (this.gear != gear) {
			this.gear = gear;
			for(OnGearChangedListener listener : stOnGearChangedListener) {
				listener.onGearChanged(this, gear, touchDev);
			}
		}
	}

	/**
	 * get device state identify
	 * 
	 * @return
	 */
	public String getDevStateId() {
		return devStateId;
	}

	/**
	 * set device state identify
	 * 
	 * @param dsId
	 */
	public void setDevStateId(String dsId) {
		if (!this.devStateId.equals(dsId)) {
			String oldStateId = this.devStateId;
			this.devStateId = dsId;
			if (oldStateId.equals(DevStateHelper.DS_YI_CHANG) || oldStateId.equals(DevStateHelper.DS_UNKNOW)) {
				for(OnStateChangedListener listener : stOnStateChangedListener) {
					listener.onAbnormalToNormal(this);
				}
			} else if (dsId.equals(DevStateHelper.DS_YI_CHANG)) {
				// if (this instanceof DevHaveChild) {
				// for (Device dev : ((DevHaveChild) this).getListDev()) {
				// dev.setDevStateId(DevStateHelper.DS_YI_CHANG);
				// }
				// }
				for(OnStateChangedListener listener : stOnStateChangedListener) {
					listener.onNormalToAbnormal(this);
				}
			}
			for(OnStateChangedListener listener : stOnStateChangedListener) {
				listener.onStateChanged(this, dsId);
			}
		}
	}

	@Override
	public boolean isNormal() {
		if (devStateId.equals(DevStateHelper.DS_YI_CHANG) || devStateId.equals(DevStateHelper.DS_UNKNOW)) {
			return false;
		}
		return true;
	}

	/**
	 * get control model
	 * 
	 * @return
	 */
	public CtrlModel getCtrlModel() {
		return ctrlModel;
	}

	/**
	 * set control model
	 * 
	 * @param ctrlModel
	 */
	public void setCtrlModel(CtrlModel ctrlModel) {
		if (this.ctrlModel != ctrlModel) {
			this.ctrlModel = ctrlModel;
			for(OnCtrlModelChangedListener listener : stOnCtrlModelChanged) {
				listener.onCtrlModelChanged(this, ctrlModel);
			}
		}
	}

	/**
	 * get sort index
	 * 
	 * @return
	 */
	public int getSortIndex() {
		return sortIndex;
	}

	/**
	 * set sort index
	 * 
	 * @param sortIndex
	 */
	public void setSortIndex(int sortIndex) {
		if(this.sortIndex != sortIndex) {
			this.sortIndex = sortIndex;
			if(onSortIndexChangedListener != null) {
				onSortIndexChangedListener.onSortIndexChanged(this, sortIndex);
			}
		}
	}

	/**
	 * if or not the device is visibility
	 * 
	 * @return
	 */
	public boolean isVisibility() {
		return visibility;
	}

	/**
	 * if or not the device is visibility
	 * 
	 */
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	/**
	 * is it deleted?
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

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<DeviceLinkage> getListDeviceLinkage() {
		return listDeviceLinkage;
	}

	public void setListDeviceLinkage(List<DeviceLinkage> listDeviceLinkage) {
		if(null != listDeviceLinkage) {
			for(DeviceLinkage dl : listDeviceLinkage) {
				removeDeviceLinkage(dl);
			}
			for(DeviceLinkage dl : listDeviceLinkage) {
				addDeviceLinkage(dl);
			}
		}
	}

	public int getNoResponse() {
		return noResponse;
	}

	public void setNoResponse(int noResponse) {
		this.noResponse = noResponse;
	}

	public void noResponsePlus() {
		noResponse++;
		if (noResponse > 3 && getLastResponseInterval() > 20000) {
			for(OnStateChangedListener listener : stOnStateChangedListener) {
				listener.onNoResponse(this);
			}
		}
	}

	public String getLastOrder() {
		return lastOrder;
	}

	public void setLastOrder(String lastOrder) {
		this.lastOrder = lastOrder;
	}

	public void resetLastCommunicationTime() {
		lastCommunicationTime = System.currentTimeMillis();
	}

	public void addDeviceLinkage(DeviceLinkage deviceLinkage) {
		if(null != deviceLinkage) {
			deviceLinkage.setSourceDevice(this);
			listDeviceLinkage.add(deviceLinkage);
		}
	}
	
	public void removeDeviceLinkage(DeviceLinkage deviceLinkage) {
		deviceLinkage.setSourceDevice(null);
		listDeviceLinkage.remove(deviceLinkage);
	}
	
	public void removeDeviceLinkage(int index) {
		if(index >=0 && index < listDeviceLinkage.size()) {
			removeDeviceLinkage(listDeviceLinkage.get(index));
		}
	}
	
	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	public boolean isGearNeedToAuto() {
		return gearNeedToAuto;
	}

	public void setGearNeedToAuto(boolean gearNeedToAuto) {
		if(this.gearNeedToAuto != gearNeedToAuto) {
			this.gearNeedToAuto = gearNeedToAuto;
			if(null != onGearNeedToAutoListener) {
				onGearNeedToAutoListener.onGearNeedToAuto(this, gearNeedToAuto);
			}
		}
	}
	
	@JsonIgnore
	public long getCommunicationInterval() {
		return System.currentTimeMillis() - lastCommunicationTime;
	}

	@JsonIgnore
	public long getLastResponseInterval() {
		return System.currentTimeMillis() - lastResponseTime;
	}

	@Override
	public String getCoding() {
		if (null == mainCodeId || null == subCode) {
			return null;
		}
		String mc = MainCodeHelper.getIns().getMc(mainCodeId);
		if (null == mc) {
			mc = mainCodeId;
		}

		return mc + getSubCode();
	}
	
	public String createVirtualDeviceCoding() {
		if (null == mainCodeId || null == subCode) {
			return null;
		}
		return mainCodeId + subCode;
	}

	@Override
	public Device findSuperParent() {
		Device dev = this;
		while (dev.getParent() != null) {
			dev = dev.getParent();
		}
		return dev;
	}

	@JsonIgnore
	public String getHeartOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.HEART + getCoding() + OrderHelper.SEPARATOR + "h1");
	}

	@Override
	public void turnGearKai() {
		setGear(Gear.KAI);
	}

	@Override
	public void turnGearGuan() {
		setGear(Gear.GUAN);
	}

	@Override
	public void turnGearAuto() {
		setGear(Gear.ZIDONG);
	}

	@Override
	public String getDevOrder(String orderHead, String dctId) {
		if (null == orderHead) {
			orderHead = "";
		}
		String order = orderHead + getCoding() + ":" + CtrlCodeHelper.getIns().getDct(dctId) + getSubCode();
		return OrderHelper.getOrderMsg(order);
	}

	@Override
	public String getDevState() {
		return DevStateHelper.getIns().getDs(getDevStateId());
	}

	@Override
	public boolean isKaiState() {
		return getDevStateId().equals("ds_k");
	}

	/**
	 * if last communication interval great 5s,can send if last communication
	 * interval lest 5s, but device responded, can send if last communication
	 * interval lest 5s, and device didn't responded, can't send
	 * 针对顶级父设备, 子设备不可判断
	 * @return
	 */
	public boolean canSend() {
		if (getCommunicationInterval() >= 5000 || getNoResponse() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean handle(String state) {
		noResponse = 0;
		// resetLastCommunicationTime();
		lastResponseTime = System.currentTimeMillis();
		setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
		if(null == state || state.isEmpty()) {
			return true;
		}
		
		String[] msgs = state.split(":");
		for(String str : msgs) {
			if(str.startsWith("u") || str.startsWith("g")) {
				continue;
			}else if(str.startsWith("2")) {
				handleNormalState(str);
			}
			else {
				handleSingleMsg(str);
			}
		}
		return true;
	}
	
	public void handleNormalState(String singleMsg) {

	}
	
	@Override
	public void handleSingleMsg(String singleMsg) {

	}

	@Override
	public int hashCode() {
		return getMainCodeId().hashCode() + getSubCode().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof Device)) {
			return false;
		}
		Device encoding = (Device) obj;
		if (getLongCoding().equals(encoding.getLongCoding())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Device o) {
		if (o == null) {
			return -1;
		}else if(this.sortIndex == o.sortIndex) {
			return this.getLongCoding().compareTo(o.getLongCoding());
		}else {
			return this.sortIndex - o.sortIndex;
		}
	}

	public Set<OnStateChangedListener> getStOnStateChangedListener() {
		return stOnStateChangedListener;
	}

	public void setStOnStateChangedListener(Set<OnStateChangedListener> stOnStateChangedListener) {
		this.stOnStateChangedListener = stOnStateChangedListener;
	}

	public Set<OnGearChangedListener> getStOnGearChangedListener() {
		return stOnGearChangedListener;
	}

	public void setStOnGearChangedListener(Set<OnGearChangedListener> stOnGearChangedListener) {
		this.stOnGearChangedListener = stOnGearChangedListener;
	}

	public Set<OnCtrlModelChangedListener> getStOnCtrlModelChanged() {
		return stOnCtrlModelChanged;
	}

	public void setStOnCtrlModelChanged(Set<OnCtrlModelChangedListener> stOnCtrlModelChanged) {
		this.stOnCtrlModelChanged = stOnCtrlModelChanged;
	}

	public Set<OnAliasChangedListener> getStOnAliasChangedListener() {
		return stOnAliasChangedListener;
	}

	public void setStOnAliasChangedListener(Set<OnAliasChangedListener> stOnAliasChangedListener) {
		this.stOnAliasChangedListener = stOnAliasChangedListener;
	}

	public void addOnGearChangedListener(OnGearChangedListener listener) {
		if(null != listener && !stOnGearChangedListener.contains(listener)) {
			stOnGearChangedListener.add(listener);
		}
	}

	public void removeOnGearChangedListener(OnGearChangedListener listener) {
		stOnGearChangedListener.remove(listener);
	}
	
	public void addOnCtrlModelChangedListener(OnCtrlModelChangedListener listener) {
		stOnCtrlModelChanged.add(listener);
	}

	public void removeOnCtrlModelChangedListener(OnCtrlModelChangedListener listener) {
		stOnCtrlModelChanged.remove(listener);
	}

	public void addOnStateChangedListener(OnStateChangedListener listener) {
		if(null != listener && !stOnStateChangedListener.contains(listener)) {
			stOnStateChangedListener.add(listener);
		}
	}

	public void removeOnStateChangedListener(OnStateChangedListener listener) {
		stOnStateChangedListener.remove(listener);
	}
	
	public void addOnAliasChangedListener(OnAliasChangedListener listener) {
		if(null != listener && !stOnAliasChangedListener.contains(listener)) {
			stOnAliasChangedListener.add(listener);
		}
	}

	public void removeOnAliasChangedListener(OnAliasChangedListener listener) {
		stOnAliasChangedListener.remove(listener);
	}
	
	public void setOnSortIndexChangedListener(OnSortIndexChangedListener listener) {
		this.onSortIndexChangedListener = listener;
	}

	public void setOnGearNeedToAutoListener(OnGearNeedToAutoListener onGearNeedToAutoListener) {
		this.onGearNeedToAutoListener = onGearNeedToAutoListener;
	}
	
	public interface OnStateChangedListener {
		void onStateChanged(Device dev, String stateId);

		void onNormalToAbnormal(Device dev);

		void onAbnormalToNormal(Device dev);

		void onNoResponse(Device dev);
	}

	public interface OnGearChangedListener {
		/**
		 * 档位改变事件
		 * @param dev 设备
		 * @param gear 改变后的档位
		 * @param touchDev 是否是触摸设备改变的, 触摸实体设备为true, 点击软件上的按钮改变的为false
		 */
		void onGearChanged(Device dev, Gear gear, boolean touchDev);
	}

	public interface OnCtrlModelChangedListener {
		void onCtrlModelChanged(Device dev, CtrlModel ctrlModel);
	}

	public interface OnAliasChangedListener {
		void onAliasChanged(Device dev, String alias);
	}
	
	public interface OnSortIndexChangedListener {
		void onSortIndexChanged(Device dev, int sortIndex);
	}
	
	public interface OnGearNeedToAutoListener {
		void onGearNeedToAuto(Device dev, boolean gearNeedToAuto);
	}

	/**
	 * 创建设置报文, 默认消息体标识符为8
	 * @return
	 */
	public String createSetOrder(String value) {
        return OrderHelper.getOrderMsg(OrderHelper.SET_HEAD + getCoding() + OrderHelper.SEPARATOR + "8" + value);
    }
	
	/**
	 * 创建设置报文
	 * @param signal, 消息体标识符
	 * @return
	 */
	public String createSetOrder(String signal, String value) {
        return OrderHelper.getOrderMsg(OrderHelper.SET_HEAD + getCoding() + OrderHelper.SEPARATOR + signal + value);
    }
	
	@Override
	public String createQueryOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "2");
	}
	
	public String createQueryOrder(String signal) {
        return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + signal);
    }

	@Override
	public String createTurnLocalModelOrder(String ip, int port) {
		String order = OrderHelper.SET_HEAD + getCoding() + OrderHelper.SEPARATOR + "a2" + OrderHelper.SEPARATOR + "n"
				+ ip + "," + port;
		return OrderHelper.getOrderMsg(order);
	}

	@Override
	public String createTurnRemoteModelOrder(String ip, int port) {
		return OrderHelper.getOrderMsg(OrderHelper.SET_HEAD + getCoding() + OrderHelper.SEPARATOR + "a1"
				+ OrderHelper.SEPARATOR + "n" + ip + "," + port);
	}

	private static void copyChildDevices(DevHaveChild dev1, DevHaveChild dev2, boolean copyId) {
		List<Device> listNewDevice = new ArrayList<>();
		for (Device device2 : dev2.getListDev()) {
			boolean haved = false;
			for (Device device1 : dev1.getListDev()) {
				if (device1.getCoding().equals(device2.getCoding())) {
					if (copyId) {
						copyDevice(device1, device2);
					} else {
						copyDeviceExceptId(device1, device2);
					}
					haved = true;
					break;
				}
			}
			if (!haved) {
				Device device = DeviceAssistent.createDeviceByCoding(device2.getCoding());
				if(null != device) {
					copyDeviceExceptId(device, device2);
					listNewDevice.add(device2);
				}
			}
		}
		for (Device device : listNewDevice) {
			dev1.addChildDev(device);
		}
	}

	/**
	 * replace dev1 attribute with dev2 attribute include id
	 * 
	 * @param dev1
	 *            target device
	 * @param dev2
	 *            source device
	 */
	public static void copyDevice(Device dev1, Device dev2) {
		if(null == dev1) {
			dev1 = new Device();
		}
		dev1.setId(dev2.getId());
		copyDeviceExceptId(dev1, dev2);
//		if (dev1 instanceof DevHaveChild) {
//			copyChildDevices((DevHaveChild) dev1, (DevHaveChild) dev2, true);
//		}
	}

	/**
	 * replace dev1 attribute with dev2 attribute but not replace id
	 * 
	 * @param dev1
	 *            target device
	 * @param dev2
	 *            source device
	 */
	public static void copyDeviceExceptId(Device dev1, Device dev2) {
		dev1.setName(dev2.getName());
		dev1.setMainCodeId(dev2.getMainCodeId());
		dev1.setSubCode(dev2.getSubCode());
		dev1.setSn(dev2.getSn());
		dev1.setDevCategory(dev2.getDevCategory());
		dev1.setPlace(dev2.getPlace());
		dev1.setAlias(dev2.getAlias());
		dev1.setGear(dev2.getGear());
		dev1.setDevStateId(dev2.getDevStateId());
		dev1.setCtrlModel(dev2.getCtrlModel());
		dev1.setSortIndex(dev2.getSortIndex());
		dev1.setVisibility(dev2.isVisibility());
		dev1.setDeleted(dev2.isDeleted());
		if (dev1 instanceof DevHaveChild) {
			copyChildDevices((DevHaveChild) dev1, (DevHaveChild) dev2, false);
		}
		if (dev1 instanceof DevCollect && dev2 instanceof DevCollect) {
			DevCollect dc1 = (DevCollect) dev1;
			DevCollect dc2 = (DevCollect) dev2;
			dc1.getCollectProperty().setCollectSrc(dc2.getCollectProperty().getCollectSrc());
			dc1.getCollectProperty().setCrestValue(dc2.getCollectProperty().getCrestValue());
			dc1.getCollectProperty().setCurrentValue(dc2.getCollectProperty().getCurrentValue());
			dc1.getCollectProperty().setLeastValue(dc2.getCollectProperty().getLeastValue());
			dc1.getCollectProperty().setPercent(dc2.getCollectProperty().getPercent());
			dc1.getCollectProperty().setUnitSymbol(dc2.getCollectProperty().getUnitSymbol());
			for(ValueTrigger vt : dc2.getCollectProperty().getListValueTrigger()) {
				ValueTrigger vt1 = new ValueTrigger();
				vt1.setCompareSymbol(vt.getCompareSymbol());
				vt1.setEnable(vt.isEnable());
				vt1.setMessage(vt.getMessage());
				vt1.setName(vt.getName());
				vt1.setTriggerValue(vt.getTriggerValue());
				dc1.getCollectProperty().addValueTrigger(vt1);
			}
		}else if(dev1 instanceof DevAlarm && dev2 instanceof DevAlarm) {
			DevAlarm da1 = (DevAlarm) dev1;
			DevAlarm da2 = (DevAlarm) dev2;
			da1.getTrigger().setEnable(da2.getTrigger().isEnable());
			da1.getTrigger().setMessage(da2.getTrigger().getMessage());
		}
	}
	// public static void main(String[] args) {
	// Device device = DeviceAssistent.createDeviceByCoding("B10001");
	// System.out.println(device.createTurnLocalModelOrder());
	// System.out.println(device.createTurnRemoteModelOrder("123", 10));
	// }

	@Override
	public String createAbnormalOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.FEEDBACK_HEAD + getCoding() + OrderHelper.SEPARATOR + "2"
				+ DevStateHelper.DS_YI_CHANG);
	}

	@Override
	public void turnOn() {
		devStateId = DevStateHelper.DS_KAI;
	}

	@Override
	public void turnOff() {
		devStateId = DevStateHelper.DS_GUAN;
	}

	@Override
	public String createInitOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "2");
	}

	@Override
	public String getLongCoding() {
		String longCoding = "";
		if(getParent() != null) {
			longCoding = getParent().getLongCoding() + "_";
		}
		longCoding += getCoding();
		return longCoding;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDevGroupName() {
		return devGroupName;
	}

	public void setDevGroupName(String devGroupName) {
		this.devGroupName = devGroupName;
	}
	
}
