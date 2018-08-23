package com.bairock.iot.intelDev.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.GuaguaMouth;
import com.bairock.iot.intelDev.device.IStateDev;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.DevCollect;
import com.bairock.iot.intelDev.linkage.ChainHolder;
import com.bairock.iot.intelDev.linkage.LinkageHolder;
import com.bairock.iot.intelDev.linkage.guagua.GuaguaHolder;
import com.bairock.iot.intelDev.linkage.loop.LoopHolder;
import com.bairock.iot.intelDev.linkage.timing.TimingHolder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * group of user
 * 
 * @author LinQiang
 *
 */
@Entity
public class DevGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JsonBackReference("user_group")
	private User user;

	private String name;
	private String psd;
	private String petName;

	@OneToMany(mappedBy = "devGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("group_dev")
	private List<Device> listDevice = Collections.synchronizedList(new ArrayList<>());

	@OneToMany(mappedBy = "devGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("group_linkage_holder")
	private List<LinkageHolder> listLinkageHolder = Collections.synchronizedList(new ArrayList<>());

	@Transient
	@JsonIgnore
	private Set<OnDeviceCollectionChangedListener> stOnDeviceCollectionChangedListener = new HashSet<>();

	/**
	 * 
	 */
	public DevGroup() {
		listLinkageHolder.add(new ChainHolder());
		listLinkageHolder.add(new LoopHolder());
		listLinkageHolder.add(new TimingHolder());
		listLinkageHolder.add(new GuaguaHolder());
		for (LinkageHolder lh : listLinkageHolder) {
			lh.setDevGroup(this);
		}
	}

	/**
	 * 
	 * @param name
	 * @param psd
	 * @param petName
	 */
	public DevGroup(String name, String psd, String petName) {
		this();
		this.name = name;
		this.psd = psd;
		this.petName = petName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * get group name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * set group name
	 * 
	 * @param groupName
	 */
	public void setName(String groupName) {
		this.name = groupName;
	}

	/**
	 * get group password
	 * 
	 * @return
	 */
	public String getPsd() {
		return psd;
	}

	/**
	 * set group password
	 * 
	 * @param groupPsd
	 */
	public void setPsd(String groupPsd) {
		this.psd = groupPsd;
	}

	/**
	 * get pet name
	 * 
	 * @return
	 */
	public String getPetName() {
		return petName;
	}

	/**
	 * set pet name
	 * 
	 * @param petName
	 */
	public void setPetName(String petName) {
		this.petName = petName;
	}

	/**
	 * 
	 * @return
	 */
	public List<Device> getListDevice() {
		return listDevice;
	}

	/**
	 * 
	 * @param listDevice
	 */
	public void setListDevice(List<Device> listDevice) {
		if (null == listDevice) {
			return;
		}
		this.listDevice.clear();
		for (Device dev : listDevice) {
			addDevice(dev);
		}
	}

	public List<LinkageHolder> getListLinkageHolder() {
		return listLinkageHolder;
	}

	public void setListLinkageHolder(List<LinkageHolder> listLinkageHolder) {
		this.listLinkageHolder = listLinkageHolder;
		for (LinkageHolder lh : listLinkageHolder) {
			lh.setDevGroup(this);
		}
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public ChainHolder getChainHolder() {
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder.getClass().getSimpleName().equals(ChainHolder.class.getSimpleName())) {
				return (ChainHolder) holder;
			}
			// if(holder instanceof ChainHolder) {
			// return (ChainHolder)holder;
			// }
		}
		return null;
	}

	@JsonIgnore
	public TimingHolder getTimingHolder() {
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder instanceof TimingHolder) {
				return (TimingHolder) holder;
			}
		}
		return null;
	}

	@JsonIgnore
	public LoopHolder getLoopHolder() {
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder instanceof LoopHolder) {
				return (LoopHolder) holder;
			}
		}
		return null;
	}

	@JsonIgnore
	public GuaguaHolder getGuaguaHolder() {
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder instanceof GuaguaHolder) {
				return (GuaguaHolder) holder;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param chainHolder
	 */
	public void setChainHolder(ChainHolder chainHolder) {
		chainHolder.setDevGroup(this);
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder.getClass().getSimpleName().equals(ChainHolder.class.getSimpleName())) {
				holder = chainHolder;
				return;
			}
			if (holder instanceof ChainHolder) {
				holder = chainHolder;
				return;
			}
		}
	}

	public void setTimingHolder(TimingHolder timingHolder) {
		timingHolder.setDevGroup(this);
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder instanceof TimingHolder) {
				holder = timingHolder;
				return;
			}
		}
	}

	public void setLoopHolder(LoopHolder loopHolder) {
		loopHolder.setDevGroup(this);
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder instanceof LoopHolder) {
				holder = loopHolder;
				return;
			}
		}
	}

	public void setGuaguaHolder(GuaguaHolder guaguaHolder) {
		guaguaHolder.setDevGroup(this);
		for (LinkageHolder holder : listLinkageHolder) {
			if (holder instanceof GuaguaHolder) {
				holder = guaguaHolder;
				return;
			}
		}
	}

	/**
	 * 
	 * @param device
	 */
	public void addDevice(Device device) {
		if (null == device) {
			return;
		}
		for (Device dev : listDevice) {
			if (dev.equals(device)) {
				return;
			}
		}

		device.setDevGroup(this);
		listDevice.add(device);
		for (OnDeviceCollectionChangedListener listener : stOnDeviceCollectionChangedListener) {
			listener.onAdded(device);
		}
	}

	public void removeDeletedDevice() {
		List<Device> listDeleted = new ArrayList<>();
		for (Device dev : listDevice) {
			if (dev.isDeleted()) {
				listDeleted.add(dev);
			}
		}
		for (Device dev : listDeleted) {
			listDevice.remove(dev);
		}
	}

	/**
	 * 
	 * @param device
	 */
	public boolean removeDevice(Device device) {
		return removeDevice(listDevice, device);
	}

	private boolean removeDevice(List<Device> list, Device device) {
		boolean res = false;
		List<Device> newList = new ArrayList<>(list);
		for (Device dev : newList) {
			if (dev == device) {
				for (OnDeviceCollectionChangedListener listener : stOnDeviceCollectionChangedListener) {
					listener.onRemoved(device);
				}
				dev.setDevGroup(null);
				list.remove(dev);
				res = true;
				break;
			} else {
				if (dev instanceof DevHaveChild) {
					res = removeDevice(((DevHaveChild) dev).getListDev(), device);
					if(res) {
						break;
					}
				}
			}
		}
		return res;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Device removeDevice(int index) {
		Device dev = listDevice.remove(index);
		if (null != dev) {
			dev.setDevGroup(null);
		}
		return dev;
	}

	/**
	 * 
	 * @param mcId
	 *            main code identify
	 * @param sc
	 *            sub code
	 * @return
	 */
	public Device findDeviceWithMcId(String mcId, String sc) {
		if (null == mcId || null == sc) {
			return null;
		}
		for (Device dev : listDevice) {
			Device dd = findDevice(dev, mcId, sc);
			if (null != dd) {
				return dd;
			}
		}
		return null;
	}

	private Device findDevice(Device dev, String mcId, String sc) {
		if (dev.getMainCodeId().equals(mcId) && dev.getSubCode().equals(sc)) {
			return dev;
		} else if (dev instanceof DevHaveChild) {
			for (Device dd : ((DevHaveChild) dev).getListDev()) {
				Device d1 = findDevice(dd, mcId, sc);
				if (d1 != null) {
					return d1;
				}
			}
		}
		return null;
	}

	public Device findDeviceWithLongCoding(String longCoding) {
		if (null == longCoding) {
			return null;
		}
		for (Device dev : listDevice) {
			Device dd = findDevice(dev, longCoding);
			if (null != dd) {
				return dd;
			}
		}
		return null;
	}

	private Device findDevice(Device dev, String longCoding) {
		if (dev.getLongCoding().equals(longCoding)) {
			return dev;
		} else if (dev instanceof DevHaveChild) {
			for (Device dd : ((DevHaveChild) dev).getListDev()) {
				Device d1 = findDevice(dd, longCoding);
				if (d1 != null) {
					return d1;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param mc
	 *            main code
	 * @param sc
	 *            sub code
	 * @return
	 */
	public Device findDeviceWithMc(String mc, String sc) {
		if (null == mc || null == sc) {
			return null;
		}
		String mcId = MainCodeHelper.getIns().getMcId(mc);
		return findDeviceWithMcId(mcId, sc);
	}

	/**
	 * 
	 * @param coding
	 *            coding is main code + sub code of default device, like B10001, or
	 *            parent code + sub code + _ + sub device main code + _ + sub device
	 *            sub code of sub device, like B10001_10_1
	 * @return
	 */
	public Device findDeviceWithCoding(String coding) {
		if (null == coding) {
			return null;
		}
		Device dev = null;
		if (coding.contains("_")) {
			// it maybe sub device of the device that have child, coding like B10001_10_1
			dev = findDeviceWithLongCoding(coding);
//			String[] codes = coding.split("_");
//			if (codes.length != 3) {
//				return null;
//			}
//			DevHaveChild parentDev = (DevHaveChild) findDeviceWithCoding(codes[0]);
//			if (null == parentDev) {
//				return null;
//			}
//			dev = parentDev.findDeviceByMainCodeAndSubCode(codes[1], codes[2]);
		} else {
			// it maybe not the sub device
			if (coding.length() < 6) {
				return null;
			}
			String mc = coding.substring(0, 2);
			String sc = coding.substring(2);
			dev = findDeviceWithMc(mc, sc);
		}
		return dev;
	}

	/**
	 * get all the device which is IStateDev
	 * 
	 * @return
	 */
	public List<Device> findListIStateDev(boolean visibility) {
		return findListIStateDev(getListDevice(), visibility);
	}
	
	/**
	 * 从给定集合中获取所有可控设备
	 * @param listSourceDev 源设备集合
	 * @param visibility 获取的设备是否可见，为true表示只获取可见的设备
	 * @return
	 */
	public static List<Device> findListIStateDev(List<Device> listSourceDev, boolean visibility) {
		List<Device> listDev = new ArrayList<>();
		for (Device dev : listSourceDev) {
			listDev.addAll(findListIStateDev(dev, visibility));
		}
		Collections.sort(listDev);
		return listDev;
	}

	/**
	 * 从给定设备中获取该设备及其子设备集合中的所有可控设备
	 * @param dev 源设备
	 * @param visibility 获取的设备是否可见，为true表示只获取可见的设备
	 * @return
	 */
	public static List<Device> findListIStateDev(Device dev, boolean visibility) {
		List<Device> list = new ArrayList<>();
		if(visibility) {
			if(!dev.isVisibility()) {
				return list;
			}
		}
		if (dev instanceof DevHaveChild) {
			for (Device childDev : ((DevHaveChild) dev).getListDev()) {
				list.addAll(findListIStateDev(childDev, visibility));
			}
		} else if (dev instanceof IStateDev) {
			if (visibility) {
				if (dev.isVisibility()) {
					list.add(dev);
				}
			} else {
				list.add(dev);
			}
		}
		return list;
	}

	/**
	 * get all the device which is collect
	 * 
	 * @return
	 */
	public List<DevCollect> findListCollectDev(boolean visibility) {
		List<DevCollect> listDev = new ArrayList<>();
		for (Device dev : getListDevice()) {
			findListCollectDev(listDev, dev, visibility);
		}
		Collections.sort(listDev);
		return listDev;
	}

	private void findListCollectDev(List<DevCollect> listDev, Device dev, boolean visibility) {
		if(visibility) {
			if(!dev.isVisibility()) {
				return;
			}
		}
		
		if (dev instanceof DevHaveChild) {
			for (Device childDev : ((DevHaveChild) dev).getListDev()) {
				findListCollectDev(listDev, childDev, visibility);
			}
		} else if (dev instanceof DevCollect) {
			if (visibility) {
				if (dev.isVisibility()) {
					listDev.add((DevCollect) dev);
				}
			} else {
				listDev.add((DevCollect) dev);
			}
		}
	}

	/**
	 * get all the device which is guagua mouth
	 * 
	 * @return
	 */
	public List<GuaguaMouth> findListGuaguaMouth(boolean visibility) {
		List<GuaguaMouth> listDev = new ArrayList<>();
		for (Device dev : getListDevice()) {
			findListGuaguaMouth(listDev, dev, visibility);
		}
		Collections.sort(listDev);
		return listDev;
	}

	private void findListGuaguaMouth(List<GuaguaMouth> listDev, Device dev, boolean visibility) {
		if(visibility) {
			if(!dev.isVisibility()) {
				return;
			}
		}
		if (dev instanceof DevHaveChild) {
			for (Device childDev : ((DevHaveChild) dev).getListDev()) {
				findListGuaguaMouth(listDev, childDev, visibility);
			}
		} else if (dev instanceof GuaguaMouth) {
			if (visibility) {
				if (dev.isVisibility()) {
					listDev.add((GuaguaMouth) dev);
				}
			} else {
				listDev.add((GuaguaMouth) dev);
			}
		}
	}

	public Device findDeviceByDevId(String devId) {
		if (null == devId) {
			return null;
		}
		return findDeviceByDevId(listDevice, devId);
	}

	public static Device findDeviceByDevId(List<Device> listDev, String devId) {
		Device device = null;
		for (Device dev : listDev) {
			if (dev instanceof DevHaveChild) {
				device = findDeviceByDevId(((DevHaveChild) dev).getListDev(), devId);
				if (device != null) {
					return device;
				}
			} else if (dev.getId().equals(devId)) {
				device = dev;
				break;
			}
		}
		return device;
	}

	/**
	 * rename the device's name
	 * 
	 * @param device
	 * @param devName
	 *            new device name
	 * @return
	 */
	public int renameDevice(Device device, String devName) {
		if (null == device || null == devName) {
			return ErrorCodes.NULL_POINT;
		}

		if (deviceNameIsHaved(devName)) {
			return ErrorCodes.DEV_NAME_IS_EXISTS;
		}
		device.setName(devName);
		return ErrorCodes.OK;
	}

	/**
	 * if the give name is already exists in the device group
	 * 
	 * @param devName
	 *            the new name of device
	 * @return
	 */
	public boolean deviceNameIsHaved(String devName) {
		if (null == devName) {
			return false;
		}
		for (Device dev : listDevice) {
			if (deviceNameIsHaved(dev, devName)) {
				return true;
			}
		}
		return false;
	}

	private boolean deviceNameIsHaved(Device dev, String devName) {
		// if(null == dev || null == devName) {
		// return false;
		// }
		if (dev.getName().equals(devName)) {
			return true;
		}
		if (dev instanceof DevHaveChild) {
			for (Device childDev : ((DevHaveChild) dev).getListDev()) {
				deviceNameIsHaved(childDev, devName);
			}
		}
		return false;
	}
	
	/**
	 * 创建默认名称，组内所有设备不允许重名，第一个设备名为主编码描述，
	 * 其后相同设备的默认名为主编吗描述+序号，序号从1开始到99
	 * @param device
	 */
	public void createDefaultDeviceName(Device device) {
		if(device.getMainCodeId().equals(MainCodeHelper.SMC_WU)) {
			return;
		}
		String name = MainCodeHelper.getIns().getMainCodeInfo(device.getMainCodeId());
		if(!deviceNameIsHaved(name)) {
			device.setName(name);
			return;
		}
		for(int i = 1; i < 100; i++) {
			name += i;
			if(!deviceNameIsHaved(name)) {
				device.setName(name);
				return;
			}
		}
		
	}
	
	/**
	 * 为设备创建默认名称，根据MainCodeHelper中的主编码描述创建，名称加上设备次编码
	 * 如果次编码为1, 则不加次编码
	 * @param device
	 */
	public static void createDefaultDeviceNameAddSubCode(Device device) {
		if(device.getMainCodeId().equals(MainCodeHelper.SMC_WU)) {
			return;
		}
		String name = MainCodeHelper.getIns().getMainCodeInfo(device.getMainCodeId());
		if(!device.getSubCode().equals("1")) {
			name += device.getSubCode();
		}
		device.setName(name);
		
	}

	public void addOnDeviceCollectionChangedListener(OnDeviceCollectionChangedListener listener) {
		stOnDeviceCollectionChangedListener.add(listener);
	}

	public void removeOnDeviceCollectionChangedListener(OnDeviceCollectionChangedListener listener) {
		stOnDeviceCollectionChangedListener.remove(listener);
	}

	public void sendOnDeviceCollectionChangedListenerAdd(Device device) {
		for (OnDeviceCollectionChangedListener listener : stOnDeviceCollectionChangedListener) {
			listener.onAdded(device);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((psd == null) ? 0 : psd.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((petName == null) ? 0 : petName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevGroup other = (DevGroup) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (psd == null) {
			if (other.psd != null)
				return false;
		} else if (!psd.equals(other.psd))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (petName == null) {
			if (other.petName != null)
				return false;
		} else if (!petName.equals(other.petName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String str = String.format("DevGroup(user:%s, id:%s, Name:%s, psd:%s, petName:%s)", user.getName(), id, name,
				psd, petName);
		return str;
	}

	public interface OnDeviceCollectionChangedListener {
		void onAdded(Device device);

		void onRemoved(Device device);
	}

}
