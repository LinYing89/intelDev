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
	public boolean enable = true;

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
		if(!enable) {
			return;
		}
		stMsg.add(devCoding);
		if (!isFinding) {
			isFinding = true;
			tSender = new TSender();
			IntelDevHelper.executeThread(tSender);
		}
	}

	public void alreadyFind(String devCoding) {
		stMsg.remove(devCoding);
	}
	
	public void cleanAll() {
		if(null != stMsg) {
			stMsg.clear();
		}
	}

	class TSender extends Thread {

		String sendMsg = "";

		@Override
		public void run() {
			while (enable && !isInterrupted() && stMsg.size() > 0) {
				String localIp = IntelDevHelper.getLocalIp();
				try {
					Set<String> st = new HashSet<>(stMsg);
					for (String str : st) {
						String order = createSeekOrder(str, localIp);
						send(OrderHelper.getOrderMsg(order));
						TimeUnit.MILLISECONDS.sleep(200);
					}
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isFinding = false;

		}

		private String createSeekOrder(String coding, String localIp) {
			return OrderHelper.SET_HEAD + coding + OrderHelper.SEPARATOR + "n" + localIp + "," + DevServer.PORT + OrderHelper.SEPARATOR + "+";
		}
	}

	@SuppressWarnings("unused")
	private void seekAllDevice(String localIp) {
		try {
			if (localIp != null) {
				int i = 0;
				// sendMsg = "S:" + localIp + "," + DevServer.PORT;
				String codings = "";
				for (String str : stMsg) {
					// 10 device is a group
					if (codings.isEmpty()) {
						codings = str;
					} else {
						codings = codings + "," + str;
					}
					if (i == stMsg.size() - 1 || (i != 0 && i % 10 == 0)) {
						String order = OrderHelper.SET_HEAD + codings + OrderHelper.SEPARATOR + "n" + localIp + ","
								+ DevServer.PORT;
						send(OrderHelper.getOrderMsg(order));
						TimeUnit.SECONDS.sleep(1);
						codings = "";
						// sendMsg = "S:" + localIp + "," + DevServer.PORT;
					}
					i++;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
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
