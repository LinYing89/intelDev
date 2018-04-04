package com.bairock.iot.intelDev.communication;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.User;

/**
 * 
 * @author LinQiang
 *
 */
public abstract class MessageAnalysiser{

	private StringBuilder sb = new StringBuilder();
	
	/**
	 * return the first device
	 * @param msg
	 */
	public Device putMsg(String msg, User user) {
		receivedMsg(msg);
		Device dev = null;
		if(null != msg){
			sb.append(msg);
			if(judgeMsgFormate(sb.toString())){
				dev = analysisReceiveMessage(sb.toString(), user);
				sb.setLength(0);
			}
		}
		return dev;
	}

	/**
	 * judge the format of receive message, if the format is error, return
	 * false, else return true, you can over write this method if you need.
	 * 
	 * @param msg
	 *            receive message
	 * @return
	 */
	public boolean judgeMsgFormate(String msg) {
		boolean formatOk = false;
		int len = msg.length();
		if (len < 3 || !(msg.substring(len - 3, len - 2)).equals(OrderHelper.SUFFIX)) {
			formatOk = false;
		} else {
			formatOk = true;
		}
		return formatOk;
	}

	/**
	 * analysis receive message, you can to over write this method
	 * 
	 * @param msg
	 */
	public Device analysisReceiveMessage(String msg, User user) {
		if (SearchDeviceHelper.isAsk) {
			SearchDeviceHelper.getIns().addToMsg(msg);
			SearchDeviceHelper.searched = true;
			return null;
		} else {
			if (null == msg || !(msg.contains(OrderHelper.PREFIX)) || !(msg.contains(OrderHelper.SUFFIX))) {
				// MessageAnalysiser.listErrMsg.add(msg);
				return null;
			}

			Device device = null;
			String[] arryMsg = msg.split("\\$");
			for (int i = 1; i < arryMsg.length; i++) {
				String data = arryMsg[i];
				Device dev = analysisSingleMsg(data, user);
				if(device == null){
					device = dev;
				}
			}
			allMessageEnd();
			return device;
		}
	}

	/**
	 * analysis string which not contains any '$', but contains a '#'.
	 * 
	 * @param msg
	 */
	public Device analysisSingleMsg(String msg, User user) {
		if(!singleMessageStart(msg)) {
			return null;
		}
		String[] codingState = findCodingState(msg);
		if(null == codingState || codingState[0] == null) {
			return null;
		}
		Device device = null;
		if(codingState[1] != null) {
			if(codingState[1].contains("+")) {
				device = DeviceAssistent.createDeviceByCoding(codingState[0]);
				if(null != device) {
					if(codingState[1].equals("+")) {
						device.setDevStateId(DevStateHelper.CONFIGING);
					}else if(codingState[1].equals("+ok")) {
						device.setDevStateId(DevStateHelper.CONFIG_OK);
					}
				}
				configDevice(device, msg);
				return device;
			}else if(codingState[1].startsWith("a")) {
				device = DeviceAssistent.createDeviceByCoding(codingState[0]);
				configDeviceCtrlModel(device, msg);
				return device;
			}
		}
		if(null != user){
			device = user.findDev(codingState[0]);
		}
		if (null == device) {
			device = DeviceAssistent.createDeviceByCoding(codingState[0]);
			if(null != device) {
				unKnowDev(device, msg);
				return device;
			}else {
				noTheDev(msg);
				return null;
			}
		}

		if(codingState[1] != null) {
			String[] states = codingState[1].split(":");
			for(String str : states) {
				if(str.equals("u")) {
					
				}else if(str.equals("g")) {
					
				}else {
					device.handle(str);
				}
			}
			//device.handle(codingState[1]);
		}

		deviceFeedback(device, msg);
		singleMessageEnd(device, msg);
		return device;
	}

	/**
	 * find device's coding and state from message
	 * @param msg
	 * @return String array,index 0 is coding, index 1 is state, or null
	 */
	public String[] findCodingState(String msg) {
		String[] codingState = new String[2];
		String cutMsg = "";
		if(msg.contains(OrderHelper.PREFIX)) {
			cutMsg = msg.substring(msg.indexOf(OrderHelper.PREFIX) + 1);
		}else {
			cutMsg = msg;
		}
		if (cutMsg.contains(OrderHelper.SUFFIX)) {
			cutMsg = msg.substring(0, msg.indexOf(OrderHelper.SUFFIX));
		} else {
			return null;
		}
		// remove back style, like 'I'
		if (cutMsg.startsWith(OrderHelper.FEEDBACK_HEAD)) {
			cutMsg = cutMsg.substring(1);
		}
		int iSeparator = cutMsg.indexOf(OrderHelper.SEPARATOR);
		if (iSeparator != -1) {
			codingState[0] = cutMsg.substring(0, iSeparator);
			codingState[1] = cutMsg.substring(iSeparator + 1);
		}else{
			codingState[0] = cutMsg;
		}
		return codingState;
	}
	
	public boolean singleMessageStart(String msg) {return true;}
	
	public void receivedMsg(String msg) {}
	
	/**
	 * 
	 * @param msg
	 * @param device
	 */
	public abstract void deviceFeedback(Device device, String msg);
	
	public abstract void unKnowDev(Device device, String msg);
	
	public abstract void noTheDev(String msg);
	
	/**
	 * all message is finished
	 */
	public abstract void allMessageEnd();
	
	/**
	 * one message is finished
	 * @param device
	 * @param msg
	 */
	public abstract void singleMessageEnd(Device device, String msg);
	
	public abstract void configDevice(Device device, String msg);
	public abstract void configDeviceCtrlModel(Device device, String msg);
}
