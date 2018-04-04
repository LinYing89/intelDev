package com.bairock.iot.intelDev.communication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * 
 * @author LinQiang
 *
 */
public class FindDevHelper {

	private static FindDevHelper NET_HELPER = new FindDevHelper();

	private OnSendListener onSendListener;
	private Set<String> stMsg;
	private TSender tSender;
	private boolean isFinding;

	/**
	 * 
	 */
	private FindDevHelper() {
		stMsg = Collections.synchronizedSet(new HashSet<>());
		if (onSendListener != null) {
			onSendListener.create();
		}
	}

	/**
	 * 
	 * @return
	 */
	public static FindDevHelper getIns() {
		return NET_HELPER;
	}

	/**
	 * 
	 * @return
	 */
	public OnSendListener getOnSendListener() {
		return onSendListener;
	}

	/**
	 * 
	 * @param onSendListener
	 */
	public void setOnSendListener(OnSendListener onSendListener) {
		this.onSendListener = onSendListener;
	}

	public void findDev(String devCoding) {
		stMsg.add(devCoding);
		if(!isFinding) {
			isFinding = true;
			tSender = new TSender();
			IntelDevHelper.executeThread(tSender);
		}
	}

	public void alreadFind(String devCoding) {
		stMsg.remove(devCoding);
	}

	class TSender extends Thread {

		String sendMsg = "";

		@Override
		public void run() {
			while (!isInterrupted() && stMsg.size() > 0) {
				String localIp = IntelDevHelper.getLocalIp();
				try {
					if (localIp != null) {
						int i = 0;
						sendMsg = "S:" + localIp + "," + DevServer.PORT;
						for (String str : stMsg) {
							//10 device is a group
							sendMsg += (":" + str);
							if(i == stMsg.size() - 1 || (i!=0 && i % 10 == 0)) {
								send(OrderHelper.getOrderMsg(sendMsg));
								TimeUnit.SECONDS.sleep(1);
								sendMsg = "S:" + localIp + "," + DevServer.PORT;
							}
							i++;
						}
					}
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isFinding = false;
		}
	}

	/**
	 * code is GBK
	 * 
	 * @param message
	 */
	public synchronized void send(String message) {
		UdpServer.getIns().send(message);
		if (onSendListener != null) {
			onSendListener.send(message);
		}
	}

	/**
	 * log push
	 * 
	 * @author LinQiang
	 *
	 */
	public interface OnSendListener {

		/**
		 * 
		 */
		void create();

		/**
		 * 
		 * @param message
		 */
		void send(String message);
	}
}
