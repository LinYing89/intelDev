package com.bairock.iot.intelDev.communication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * find the offline devices
 * 
 * @author Administrator
 *
 */
public class SeekDeviceHelper {

	private static SeekDeviceHelper ins = new SeekDeviceHelper();

	private List<String> listOfflineDevCoding = Collections.synchronizedList(new ArrayList<>());

	private SeekDeviceThread seekDeviceThread;
	
	public static SeekDeviceHelper getIns() {
		return ins;
	}

	private SeekDeviceHelper() {
	}

	/**
	 * add an offline device coding to collection for seek.
	 * and start seek device thread if it not start.
	 * @param devCoding
	 */
	public void addOfflineDevCoding(String devCoding) {
		if (!listOfflineDevCoding.contains(devCoding)) {
			listOfflineDevCoding.add(devCoding);
			if(null == seekDeviceThread || !seekDeviceThread.isAlive()) {
				seekDeviceThread = new SeekDeviceThread();
				IntelDevHelper.executeThread(seekDeviceThread);
			}
		}
	}

	public void removeOfflineDevCoding(String devCoding) {
		listOfflineDevCoding.remove(devCoding);
	}

	private String getSeekOrder(String ip, int port) {
		StringBuilder sb = new StringBuilder("S:");
		sb.append(ip);
		sb.append(",");
		sb.append(port);
		sb.append(OrderHelper.SEPARATOR);
		for (int i = 0; i < listOfflineDevCoding.size(); i++) {
			sb.append(listOfflineDevCoding.get(i));
			if (i < listOfflineDevCoding.size() - 1) {
				sb.append(OrderHelper.SEPARATOR);
			}
		}
		return OrderHelper.getOrderMsg(sb.toString());
	}

	private class SeekDeviceThread extends Thread {

		public void run() {
			String ip = IntelDevHelper.getLocalIp();
			while (!isInterrupted() && listOfflineDevCoding.size() > 0) {
				String seekOrder = getSeekOrder(ip, DevServer.PORT);
				FindDevHelper.getIns().send(seekOrder);
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		}

	}
}
