package com.bairock.iot.intelDev.communication;

import java.util.ArrayList;
import java.util.List;

import com.bairock.iot.intelDev.device.Coordinator;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.device.devswitch.SubDev;
import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * 
 * @author LinQiang
 *
 */
public class SearchDeviceHelper {

	private static SearchDeviceHelper ins;
	
	public static boolean isAsk = false;
    public static boolean searched = false;
    
    private List<Device> listDevice;
	/**
	 * list message when search device
	 */
	private List<String> listReceiveMsg = new ArrayList<String>();
	
	private OnSearchListener onSearchListener;
	
	private SearchDeviceThread searchDevThread;

	private SearchDeviceHelper(){}
	
	/**
	 * 
	 * @return
	 */
	public static SearchDeviceHelper getIns(){
		if(null == ins){
			ins = new SearchDeviceHelper();
		}
		return ins;
	}
	
	/**
	 * 
	 * @param msg
	 */
	public synchronized void addToMsg(String msg) {
		if(null != onSearchListener){
			onSearchListener.searchedMsg(msg);
		}
		
		boolean have = false;
		for (String str : listReceiveMsg) {
			if (str.equals(msg)) {
				have = true;
				break;
			}
		}
		if (!have) {
			listReceiveMsg.add(msg);
		}
	}
	
	/**
	 * 
	 * @param onSearchListener
	 */
	public void setOnSearchListener(OnSearchListener onSearchListener){
		this.onSearchListener = onSearchListener;
	}
	
	/**
	 * 
	 * @param listDev
	 */
	private List<Device> updateDevMaster(List<Device> listDev){
		List<Device> listNewDevs = new ArrayList<>();
		if(null == listDev){
			return listNewDevs;
		}
		if(null == this.listDevice){
			this.listDevice = new ArrayList<>();
			return listDev;
		}
		
		
//		boolean haved = false;
		for(Device device : listDev){
			addNewDevice(listNewDevs, listDevice, device);
//			haved = false;
//			for(Device dev : this.listDevice){
//				if(device.equals(dev)){
//					haved = true;
//					dev.setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
//				}
//			}
//			if(!haved){
//				//this.listDevice.add(device);
//				listNewDevs.add(device);
//			}
		}
		return listNewDevs;
	}
	
	private void addNewDevice(List<Device> listNewDev, List<Device> listOldDevs, Device dev) {
		if(dev instanceof SubDev) {
			return;
		}
		Device haved = null;
		for(Device dev1 : listOldDevs) {
			if(dev1.equals(dev)) {
				haved = dev1;
				break;
			}
		}
		if(null == haved) {
			listNewDev.add(dev);
			return;
		}
		
		if(haved instanceof Coordinator) {
			Coordinator c1 = (Coordinator)haved;
			Coordinator c2 = (Coordinator)dev;
			
			for(Device dev1 : c2.getListDev()) {
				boolean haved2 = false;
				for(Device dev2 : c1.getListDev()) {
					if(dev1.equals(dev2)) {
						haved2 = true;
						break;
					}
				}
				if(!haved2) {
					c1.addChildDev(dev1);
					listNewDev.add(dev1);
				}
			}
		}
		
//		if(haved instanceof DevHaveChild) {
//			for(Device dev1 : ((DevHaveChild)dev).getListDev()) {
//				addNewDevice(listNewDev, ((DevHaveChild)haved).getListDev(), dev1);
//			}
//		}
	}
	
	/**
	 * 
	 * @param listDevice
	 */
	public void startSearchDevThread(List<Device> listDevice){
		if(null != searchDevThread && searchDevThread.isAlive()){
			return;
		}
		
		this.listDevice = listDevice;
//		if(null != listDevice){
//			for(Device dev : listDevice){
//				dev.setNormal(false);
//			}
//		}
		searchDevThread = new SearchDeviceThread();
		IntelDevHelper.executeThread(searchDevThread);
	}
	
	/**
	 * 
	 */
	public void stopSearchDevThread(){
		searchDevThread.interrupt();
		searchDevThread = null;
	}
	
	/**
	 * 
	 * @return
	 */
	private List<Device> createDevMaster(){
		List<String> listMsg = new ArrayList<>();
		listMsg.addAll(listReceiveMsg);
		
		StringBuilder sb = new StringBuilder();
		for (String str : listMsg) {
			sb.append(str);
		}
		String content = sb.toString();
		if (!content.contains("$!:")) {
			return null;
		}
		
		// sub string from '$!:' to '#'
		//StringBuilder strB = new StringBuilder();
		List<String> listContent = new ArrayList<>();
		int head = content.indexOf("!");
		int end = content.indexOf("#");
		while (head != -1 && head + 1 < end && head + 1 < content.length()) {
			String str = content.substring(head + 1, end);
			listContent.add(str);
			//strB.append(str);
			if (end + 1 < content.length()) {
				content = content.substring(end + 1);
				head = content.indexOf("!");
				end = content.indexOf("#");
			} else {
				break;
			}
		}
		
		List<Device> listDevice = new ArrayList<>();
		for(String str : listContent){
			String[] arryDevice = str.split(":");
			Coordinator cd = null;
			for (String strMsg : arryDevice) {
				Device dev = DeviceAssistent.createDeviceByCoding(strMsg);
				if(null == dev){
					continue;
				}
				dev.setDevStateId(DevStateHelper.DS_ZHENG_CHANG);
				if(dev instanceof Coordinator){
					cd = (Coordinator)dev;
					listDevice.add(cd);
				}else if(null != cd){
					cd.addChildDev(dev);
				}else{
					listDevice.add(dev);
				}
			}
		}
		
		listReceiveMsg.clear();
		return listDevice;
	}
	
	/**
	 * 
	 * @author LinQiang
	 *
	 */
	public interface OnSearchListener{
		
		/**
		 * 
		 */
		void searchStart();
		
		/**
		 * 
		 * @param msg
		 */
		void searchedMsg(String msg);
		
		/**
		 * 
		 */
		void searchFail();
		
		/**
		 * 
		 * @param listDev
		 */
		void searchedAllDevices(List<Device> listDev);
		
		/**
		 * 
		 * @param listDev
		 */
		void searchedNewDevices(List<Device> listDev);
	}
	
	private String getSearchDevOrder() {
		StringBuilder sb = new StringBuilder("?:");
		sb.append(IntelDevHelper.getLocalIp());
		sb.append(OrderHelper.SEPARATOR);
		sb.append(DevServer.PORT);
		return OrderHelper.getOrderMsg(sb.toString());
	}
	
	/**
	 * 
	 * @author LinQiang
	 *
	 */
	private class SearchDeviceThread extends Thread {

		public void run() {
	        isAsk = true;
	        searched = false;
	        
	        if(null != onSearchListener) {
	        	onSearchListener.searchStart();
	        }
	        
	        String searchOrder = getSearchDevOrder();
	        try {
	        	FindDevHelper.getIns().send(searchOrder);
	        	sleep(2000);
	        	FindDevHelper.getIns().send(searchOrder);
	        	sleep(2000);
	        	FindDevHelper.getIns().send(searchOrder);
	            sleep(10000);
	            isAsk = false;
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	            if(null != onSearchListener) {
	            	onSearchListener.searchFail();
	            }
	            return;
	        }
	        if (searched == false) {
	        	if(null != onSearchListener) {
	        		onSearchListener.searchFail();
	        	}
	        } else {
	        	List<Device> listDev = createDevMaster();
	        	List<Device> listNewDevs = updateDevMaster(listDev);
	        	if(null != onSearchListener) {
	        		onSearchListener.searchedAllDevices(listDev);
	        		onSearchListener.searchedNewDevices(listNewDevs);
	        	}
	        }
	        System.out.println("SearchDeviceThread close");
	    }

	}
	
	public static void main(String[] args) {
		SearchDeviceHelper.getIns().startSearchDevThread(null);
	}
}
