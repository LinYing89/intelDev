package com.bairock.iot.intelDev.device;

import com.bairock.iot.intelDev.communication.DevChannelBridgeHelper;
import com.bairock.iot.intelDev.communication.DevServer;
import com.bairock.iot.intelDev.order.DeviceOrder;
import com.bairock.iot.intelDev.order.OrderType;
import com.bairock.iot.intelDev.user.IntelDevHelper;
import com.bairock.iot.intelDev.user.Util;

public class SetDevModelTask extends Thread {

	public static boolean setting;
	public DeviceModelHelper deviceModelHelper = new DeviceModelHelper();

	// 设置模式进度
	// 0向服务器发
	// 1向设备发
	public int setModelProgressValue = 0;
	private int count;
	private String username;
	private String devGroupName;

	private OnProgressListener onProgressListener;

	public SetDevModelTask(String username, String devGroupName, Device device, CtrlModel toCtrlModel, String serverName, int serverPort) {
		this.username = username;
		this.devGroupName = devGroupName;
		deviceModelHelper = new DeviceModelHelper();
		deviceModelHelper.setDevToSet(device);
		deviceModelHelper.setCtrlModel(toCtrlModel);
		String order;
		if (toCtrlModel == CtrlModel.LOCAL) {
			order = device.createTurnLocalModelOrder(IntelDevHelper.getLocalIp(), DevServer.PORT);
		} else {
			order = device.createTurnRemoteModelOrder(serverName, serverPort);
		}
		deviceModelHelper.setOrder(order);
	}

	public OnProgressListener getOnProgressListener() {
		return onProgressListener;
	}

	public void setOnProgressListener(OnProgressListener onProgressListener) {
		this.onProgressListener = onProgressListener;
	}

	@Override
	public void run() {
		try {
			setting = true;
			while (setModelProgressValue < 3 && setting) {
				count++;
				if (count > 10) {
					onResult(false);
					return;
				}
				if (setModelProgressValue == 0) {
					// 第一步 向服务器发
					if (deviceModelHelper.getCtrlModel() == CtrlModel.REMOTE) {
						String oldOrder = deviceModelHelper.getOrder().substring(1,
								deviceModelHelper.getOrder().indexOf("#"));
						DeviceOrder deviceOrder = new DeviceOrder(username, devGroupName, OrderType.TO_REMOTE_CTRL_MODEL,
								deviceModelHelper.getDevToSet().getId(),
								deviceModelHelper.getDevToSet().getLongCoding(), OrderHelper.getOrderMsg(oldOrder));
						onSendToServer(Util.orderBaseToString(deviceOrder));
//								PadClient.getIns().send(OrderHelper.getOrderMsg(oldOrder + ":" + jsonOrder));
					} else {
						String oldOrder = deviceModelHelper.getOrder().substring(1,
								deviceModelHelper.getOrder().indexOf("#"));
						oldOrder += ":u" + username + ":g" + devGroupName;
						DeviceOrder deviceOrder = new DeviceOrder(username, devGroupName, OrderType.TO_LOCAL_CTRL_MODEL,
								deviceModelHelper.getDevToSet().getId(),
								deviceModelHelper.getDevToSet().getLongCoding(), OrderHelper.getOrderMsg(oldOrder));
						onSendToServer(Util.orderBaseToString(deviceOrder));
//								PadClient.getIns().send(OrderHelper.getOrderMsg(oldOrder));
					}
				} else if (setModelProgressValue == 1) {
					// 第二步
					// 如果是设为远程模式, 向本地发送报文
					// 如果是设为本地模式, 不需要向本地发送报文, 只需向服务器发, 收到服务器的回应后等待本地设备的心跳
					if (deviceModelHelper.getCtrlModel() == CtrlModel.REMOTE) {
						String oldOrder = deviceModelHelper.getOrder().substring(1,
								deviceModelHelper.getOrder().indexOf("#"));
						oldOrder += ":u" + username + ":g" + devGroupName;
						DevChannelBridgeHelper.getIns().sendDevOrder(deviceModelHelper.getDevToSet(),
								OrderHelper.getOrderMsg(oldOrder), true);
					}
				}
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			onResult(false);
			return;
		}
		onResult(true);
		setting = false;
	}

	private void onResult(boolean result) {
		if (null != onProgressListener) {
			onProgressListener.onResult(deviceModelHelper.getCtrlModel(), result);
		}
		deviceModelHelper = null;
	}

	private void onSendToServer(String order) {
		if (null != onProgressListener) {
			onProgressListener.onSendToServer(order);
		}
	}

	public interface OnProgressListener {
		void onSendToServer(String order);

		void onResult(CtrlModel toCtrlModel, boolean result);
	}
}
