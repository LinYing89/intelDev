package com.bairock.iot.intelDev.device;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.devcollect.DevCollect;
import com.bairock.iot.intelDev.user.DevGroup;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "device_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Device")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class")
public class Device implements Comparable<Device>, IDevice {

	@Id
	@Column(nullable = false)
	private String id;

	@ManyToOne
	@JsonBackReference("group_dev")
	private DevGroup devGroup;

	@ManyToOne
	@JsonBackReference("devparent_child")
	private DevHaveChild parent;

	private String mainCodeId = "";
	private String subCode = "";
	private String sn = "";
	private DevCategory devCategory = DevCategory.WU;
	private String place = "";
	private String name = "";
	private String alias = "";
	private Gear gear = Gear.UNKNOW;
	private String devStateId = DevStateHelper.DS_YI_CHANG;
	private CtrlModel ctrlModel = CtrlModel.UNKNOW;
	private int sortIndex;
	private boolean visibility = true;
	private boolean deleted;

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
	private OnStateChangedListener onStateChanged;

	@Transient
	@JsonIgnore
	private OnGearChangedListener onGearChanged;

	@Transient
	@JsonIgnore
	private OnCtrlModelChangedListener onCtrlModelChanged;

	@Transient
	@JsonIgnore
	private Set<OnNameChangedListener> stOnNameChangedListener = new HashSet<>();

	@Transient
	@JsonIgnore
	private Set<OnAliasChangedListener> stOnAliasChangedListener = new HashSet<>();

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
	public String getName() {
		if (null == name || name.isEmpty()) {
			name = getCoding();
		}
		return name;
	}

	/**
	 * set name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		if (null != name && !this.name.equals(name)) {
			this.name = name;
			for (OnNameChangedListener listener : stOnNameChangedListener) {
				listener.onNameChanged(this, name);
			}
		}
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
			if (null != onGearChanged) {
				onGearChanged.onGearChanged(this, gear);
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
			if (oldStateId.equals(DevStateHelper.DS_YI_CHANG)) {
				if (null != onStateChanged) {
					onStateChanged.onAbnormalToNormal(this);
				}
			} else if (dsId.equals(DevStateHelper.DS_YI_CHANG)) {
				// if (this instanceof DevHaveChild) {
				// for (Device dev : ((DevHaveChild) this).getListDev()) {
				// dev.setDevStateId(DevStateHelper.DS_YI_CHANG);
				// }
				// }
				if (null != onStateChanged) {
					onStateChanged.onNormalToAbnormal(this);
				}
			}
			if (null != onStateChanged) {
				onStateChanged.onStateChanged(this, dsId);
			}
		}
	}

	@Override
	public boolean isNormal() {
		if (devStateId.equals(DevStateHelper.DS_YI_CHANG)) {
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
			if (null != onCtrlModelChanged) {
				onCtrlModelChanged.onCtrlModelChanged(this, ctrlModel);
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
		this.sortIndex = sortIndex;
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

	public int getNoResponse() {
		return noResponse;
	}

	public void setNoResponse(int noResponse) {
		this.noResponse = noResponse;
	}

	public void noResponsePlus() {
		noResponse++;
		if (noResponse > 3 && getLastResponseInterval() > 20000) {
			if (null != onStateChanged) {
				onStateChanged.onNoResponse(this);
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

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
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
			return null;
		}

		return mc + getSubCode();
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
	 * 
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
		return true;
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
		if (getCoding().equals(encoding.getCoding())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Device o) {
		if (o == null) {
			return -1;
		}
		return this.sortIndex - o.sortIndex;
	}

	public OnStateChangedListener getOnStateChanged() {
		return onStateChanged;
	}

	public void setOnStateChanged(OnStateChangedListener onStateChanged) {
		this.onStateChanged = onStateChanged;
	}

	public OnGearChangedListener getOnGearChanged() {
		return onGearChanged;
	}

	public void setOnGearChanged(OnGearChangedListener onGearChanged) {
		this.onGearChanged = onGearChanged;
	}

	public OnCtrlModelChangedListener getOnCtrlModelChanged() {
		return onCtrlModelChanged;
	}

	public void setOnCtrlModelChanged(OnCtrlModelChangedListener onCtrlModelChanged) {
		this.onCtrlModelChanged = onCtrlModelChanged;
	}

	public void addOnNameChangedListener(OnNameChangedListener listener) {
		stOnNameChangedListener.add(listener);
	}

	public void removeOnNameChangedListener(OnNameChangedListener listener) {
		stOnNameChangedListener.remove(listener);
	}

	public void addOnAliasChangedListener(OnAliasChangedListener listener) {
		stOnAliasChangedListener.add(listener);
	}

	public void removeOnAliasChangedListener(OnAliasChangedListener listener) {
		stOnAliasChangedListener.remove(listener);
	}

	public interface OnStateChangedListener {
		void onStateChanged(Device dev, String stateId);

		void onNormalToAbnormal(Device dev);

		void onAbnormalToNormal(Device dev);

		void onNoResponse(Device dev);
	}

	public interface OnGearChangedListener {
		void onGearChanged(Device dev, Gear gear);
	}

	public interface OnCtrlModelChangedListener {
		void onCtrlModelChanged(Device dev, CtrlModel ctrlModel);
	}

	public interface OnNameChangedListener {
		void onNameChanged(Device dev, String name);
	}

	public interface OnAliasChangedListener {
		void onAliasChanged(Device dev, String alias);
	}

	@Override
	public String createQueueOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "2");
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
				listNewDevice.add(device2);
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
		dev1.setId(dev2.getId());
		copyDeviceExceptId(dev1, dev2);
		if (dev1 instanceof DevHaveChild) {
			copyChildDevices((DevHaveChild) dev1, (DevHaveChild) dev2, true);
		}
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
				+ DevStateHelper.getIns().getDs(DevStateHelper.DS_YI_CHANG));
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
}
