package com.bairock.iot.intelDev.communication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.user.IntelDevHelper;

public class RefreshCollectorValueHelper {

	private static RefreshCollectorValueHelper INS = new RefreshCollectorValueHelper();

	//refresh interval, ms
	public static int REFRESH_INTERVAL = 2000;
	private Set<Device> stMsg;
	private TSender tSender;
	private boolean isFinding;

	/**
	 * 
	 */
	private RefreshCollectorValueHelper() {
		stMsg = Collections.synchronizedSet(new HashSet<>());
	}

	/**
	 * 
	 * @return
	 */
	public static RefreshCollectorValueHelper getIns() {
		return INS;
	}

	public void RefreshDev(Device dc) {
		stMsg.add(dc);
		if (!isFinding) {
			isFinding = true;
			tSender = new TSender();
			IntelDevHelper.executeThread(tSender);
		}
	}

	public void endRefresh(Device dc) {
		stMsg.remove(dc);
	}

	class TSender extends Thread {

		String sendMsg = "";

		@Override
		public void run() {
			while (!isInterrupted() && stMsg.size() > 0) {
				try {
					for (Device dev : stMsg) {
						String order = dev.createQueueOrder();
						DevChannelBridgeHelper.getIns().sendDevOrder(dev, order, true);
					}
					TimeUnit.MILLISECONDS.sleep(REFRESH_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isFinding = false;
		}
	}

}
