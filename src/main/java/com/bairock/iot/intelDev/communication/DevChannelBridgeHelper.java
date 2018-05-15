package com.bairock.iot.intelDev.communication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bairock.iot.intelDev.device.Coordinator;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.devswitch.SubDev;
import com.bairock.iot.intelDev.user.IntelDevHelper;
import com.bairock.iot.intelDev.user.User;

public class DevChannelBridgeHelper {

	private User user;

	private int interval = 10 * 1000;
	private DevHeartThread seekDeviceOnlineThread;

	private static DevChannelBridgeHelper ins = new DevChannelBridgeHelper();
	public static String DEV_CHANNELBRIDGE_NAME = DevChannelBridge.class.getName();
//	public static String BRIDGE_COMMUNICATION_LISTENER_NAME = 
//			DevChannelBridge.OnCommunicationListener.class.getName();
	public static String BRIDGE_COMMUNICATION_LISTENER_NAME = null;

	private List<DevChannelBridge> listDevChannelBridge = Collections.synchronizedList(new ArrayList<>());

	private OnBridgesChangedListener onBridgesChangedListener;
	private OnHeartSendListener onHeartSendListener;
	
	public static DevChannelBridgeHelper getIns() {
		return ins;
	}

	private DevChannelBridge getDevChannelBridge(Device dev) {
		for (DevChannelBridge db : listDevChannelBridge) {
			if(devInDevChannelBridge(db, dev)) {
				return db;
			}
		}
		return null;
	}
	
	private DevChannelBridge getDevChannelBridge(Device dev, String userName, String groupName) {
		DevChannelBridge dcb = null;
		for (DevChannelBridge db : listDevChannelBridge) {
			if(db.getDevice() != null){
				if(db.getDevice().getDevGroup().getUser().getName().equals(userName)
						&& db.getDevice().getDevGroup().getName().equals(groupName)) {
					if(devInDevChannelBridge(db, dev)) {
						return db;
					}
				}
			}
		}
		return dcb;
	}
	
	private boolean devInDevChannelBridge(DevChannelBridge db, Device dev) {
		if(null == db.getDevice()) {
			return false;
		}
		if (db.getDevice() == dev) {
			return true;
		}else if(db.getDevice() instanceof Coordinator) {
			for(Device device : ((Coordinator)db.getDevice()).getListDev()) {
				if(device == dev) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean devInDevChannelBridge(DevChannelBridge db, String devCoding) {
		if(null == db.getDevice()) {
			return false;
		}
		if (db.getDevice().getCoding().equals(devCoding)) {
			return true;
		}else if(db.getDevice() instanceof Coordinator) {
			for(Device device : ((Coordinator)db.getDevice()).getListDev()) {
				if(device.getCoding().equals(devCoding)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private DevChannelBridge getDevChannelBridge(String devCoding) {
		for (DevChannelBridge db : listDevChannelBridge) {
			if(devInDevChannelBridge(db, devCoding)) {
				return db;
			}
		}
		return null;
	}
	
	private DevChannelBridge getDevChannelBridge(String devCoding, String userName, String groupName) {
		for (DevChannelBridge db : listDevChannelBridge) {
			if(db.getDevice() != null){
				if(db.getDevice().getDevGroup().getUser().getName().equals(userName)
						&& db.getDevice().getDevGroup().getName().equals(groupName)) {
					if(devInDevChannelBridge(db, devCoding)) {
						return db;
					}
				}
			}
		}
		return null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<DevChannelBridge> getListDevChannelBridge() {
		return listDevChannelBridge;
	}

	public void setListDevChannelBridge(List<DevChannelBridge> listDevChannelBridge) {
		this.listDevChannelBridge = listDevChannelBridge;
	}

	public void setOnBridgesChangedListener(OnBridgesChangedListener onBridgesChangedListener) {
		this.onBridgesChangedListener = onBridgesChangedListener;
	}

	public void setOnHeartSendListener(OnHeartSendListener onHeartSendListener) {
		this.onHeartSendListener = onHeartSendListener;
	}

	public void sendDevOrder(Device dev, String order, boolean immediately) {
		if (dev == null || !dev.isNormal()) {
			return;
		}
		DevChannelBridge db = getDevChannelBridge(dev.findSuperParent());
		sendToDb(db, dev, order, immediately);
	}
	
	public void sendDevOrder(Device dev, String order, String userName, String groupName, boolean immediately) {
		if (dev == null || !dev.isNormal()) {
			return;
		}
		DevChannelBridge db = getDevChannelBridge(dev.findSuperParent(), userName, groupName);
		if(null != db) {
			sendToDb(db, dev, order, immediately);
		}
	}
	
	private void sendToDb(DevChannelBridge db, Device dev, String order, boolean immediately) {
		if (db != null) {
			int result;
			if(dev instanceof SubDev) {
				result = db.sendOrder(order, dev.getParent(), immediately);
			}else {
				result = db.sendOrder(order, dev, immediately);
			}
			switch (result) {
			case DevChannelBridge.NO_CHANNEL:
			case DevChannelBridge.NO_REPONSE:
				// setDeviceState(db.getDevice(), DevStateHelper.DS_YI_CHANG);
				listDevChannelBridge.remove(db);
				break;
			case DevChannelBridge.OK:
				break;
			}
		}
	}
	
	public void sendDevOrder(String devCoding, String order, boolean immediately) {
		if (devCoding == null || devCoding.isEmpty()) {
			return;
		}
		DevChannelBridge db = getDevChannelBridge(devCoding);
		if(null != db) {
			Device dev = db.findDevice(devCoding);
			sendToDb(db, dev, order, immediately);
		}
	}
	
	public void sendDevOrder(String devCoding, String order, String userName, String groupName, boolean immediately) {
		if (devCoding == null || devCoding.isEmpty()) {
			return;
		}
		DevChannelBridge db = getDevChannelBridge(devCoding, userName, groupName);
		if(null != db) {
			Device dev = db.findDevice(devCoding);
			sendToDb(db, dev, order, immediately);
		}
	}

	public void sendToAllClient(String msg) {
		for (DevChannelBridge db : listDevChannelBridge) {
			db.sendOrder(msg);
		}
	}

	public void setChannelId(String channelId) {
		boolean result = false;
		List<DevChannelBridge> list = new ArrayList<>(listDevChannelBridge);
		for (DevChannelBridge db : list) {
			if (null == db || db.getChannelId() == null) {
				removeBridge(db);
				continue;
			}
			if (db.getChannelId().equals(channelId)) {
				result = true;
				break;
			}
		}
		if (!result) {
			addBridge(channelId);
		}
	}

	public void channelReceived(String channelId, String msg) {
		List<DevChannelBridge> list = new ArrayList<>(listDevChannelBridge);
		for (DevChannelBridge db : list) {
			if (null == db || db.getChannelId() == null) {
				removeBridge(db);
				continue;
			}
			if (db.getChannelId().equals(channelId)) {
				db.channelReceived(msg, user);
				break;
			}
		}
	}
	
	/**
	 * the dev is new connect, if collection had an old connect which device's coding is equals the dev,
	 * remove the old connect
	 * @param dev the new connect dev
	 */
	public void cleanBrigdes(Device dev) {
		List<DevChannelBridge> list = new ArrayList<>(listDevChannelBridge);
		for (DevChannelBridge db : list) {
			if (null == db || db.getChannelId() == null) {
				removeBridge(db);
				continue;
			}
			if (db.getDevice() != null && db.getDevice().equals(dev)) {
				db.setDevice(null);
				removeBridge(db);
				break;
			}
		}
	}

	private void addBridge(String channelId) {
		DevChannelBridge db = createDevChannelBridge();
		if (null != db) {
			db.setChannelId(channelId);
			listDevChannelBridge.add(db);
			if(null != onBridgesChangedListener) {
				onBridgesChangedListener.onAdd(db);
			}
			db.sendHeart();
		}
	}

	public DevChannelBridge createDevChannelBridge() {
		DevChannelBridge devChannelBridge = null;
		if (null != DEV_CHANNELBRIDGE_NAME) {
			try {
				@SuppressWarnings("unchecked")
				Class<DevChannelBridge> c = (Class<DevChannelBridge>) Class.forName(DEV_CHANNELBRIDGE_NAME);
				devChannelBridge = c.newInstance();
				if(null != BRIDGE_COMMUNICATION_LISTENER_NAME) {
					@SuppressWarnings("unchecked")
					Class<DevChannelBridge.OnCommunicationListener> cc = 
						(Class<DevChannelBridge.OnCommunicationListener>) Class.forName(BRIDGE_COMMUNICATION_LISTENER_NAME);
					DevChannelBridge.OnCommunicationListener cl = cc.newInstance();
					devChannelBridge.setOnCommunicationListener(cl);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return devChannelBridge;
	}

	public void removeBridge(DevChannelBridge db) {
		if(null != db && db.getChannel() != null) {
			db.getChannel().close();
		}
		listDevChannelBridge.remove(db);
		if(null != onBridgesChangedListener) {
			onBridgesChangedListener.onRemove(db);
		}
	}

	/**
	 * 
	 */
	public void startSeekDeviceOnLineThread() {
		if (null == seekDeviceOnlineThread || !seekDeviceOnlineThread.isRunning) {
			seekDeviceOnlineThread = new DevHeartThread();
			seekDeviceOnlineThread.isRunning = true;
			IntelDevHelper.executeThread(seekDeviceOnlineThread);
		}
	}

	/**
	 * 
	 */
	public void stopSeekDeviceOnLineThread() {
		if (null != seekDeviceOnlineThread) {
			seekDeviceOnlineThread.isRunning = false;
			seekDeviceOnlineThread.interrupt();
			seekDeviceOnlineThread = null;
		}
	}

	public void channelInActive(String channelId) {
		List<DevChannelBridge> list = new ArrayList<>(listDevChannelBridge);
		for (DevChannelBridge db : list) {
			if(db != null && db.getChannelId() != null) {
                if(db.getChannelId().equals(channelId)) {
                    setDeviceState(db.getDevice(), "ds_yc");
                    removeBridge(db);
                }
            }else {
            	removeBridge(db);
            }
		}
	}

	public void setDeviceState(Device dev, String devState) {
		if (null != dev) {
			dev.setDevStateId(devState);
		}
	}
	
	public interface OnBridgesChangedListener {
		void onAdd(DevChannelBridge bridge);
		void onRemove(DevChannelBridge bridge);
	}
	
	/**
	 * 
	 * @author LinQiang
	 *
	 */
	public class DevHeartThread extends Thread {

		boolean isRunning = false;
		@Override
		public void run() {
			isRunning = true;
			while (!isInterrupted()) {
				try {
					sleep(interval);
					// System.out.println("DevHeartThread begin");
					if(null != onHeartSendListener) {
						onHeartSendListener.onHeartSend();
					}
					if (listDevChannelBridge.isEmpty()) {
						continue;
					}
					// System.out.println("DevHeartThread listDevChannelBridge size " +
					// listDevChannelBridge.size());
					// if(null == user || user.getListDevGroup().get(0).getListDevice().size() == 0)
					// {
					// continue;
					// }

					// List<Device> listDev = new
					// ArrayList<>(user.getListDevGroup().get(0).getListDevice());
					List<DevChannelBridge> list = new ArrayList<>(listDevChannelBridge);
					for (DevChannelBridge db : list) {
						db.sendHeart();
						// listDev.remove(db.getDevice());
					}
					
					// if device have no channel, set it abnormal
					// for(Device dev : listDev) {
					// dev.setDevStateId(DevStateHelper.DS_YI_CHANG);
					// }
				} catch (InterruptedException e) {
					//e.printStackTrace();
					break;
				}
			}
			isRunning = false;
			//System.out.println("SeekDeviceOnlineThread close");
		}

	}
	
	public interface OnHeartSendListener{
		void onHeartSend();
	}
}
