package com.bairock.iot.intelDev.communication;

import com.bairock.iot.intelDev.device.Coordinator;
import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.User;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class DevChannelBridge {

	public static String analysiserName = MessageAnalysiser.class.getName();
	public static ChannelGroup channelGroup = new DefaultChannelGroup("client", GlobalEventExecutor.INSTANCE);

	// private String devCoding;
	private Device device;
	private String channelId;
	private Channel channel;
	// the channel have no response count,0 if have response
	private int noReponse;
	//send count
	private int sendCount;
	private int receivedCount;
	private String lastSendMsg;
	private String lastReceivedMsg;
	private MessageAnalysiser messageAnalysiser;

	private OnCommunicationListener onCommunicationListener;
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getNoReponse() {
		return noReponse;
	}

	public void noReponsePlus() {
		noReponse++;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public int getReceivedCount() {
		return receivedCount;
	}

	public void setReceivedCount(int receivedCount) {
		this.receivedCount = receivedCount;
	}

	public String getLastSendMsg() {
		return lastSendMsg;
	}

	public void setLastSendMsg(String lastSendMsg) {
		this.lastSendMsg = lastSendMsg;
	}

	public String getLastReceivedMsg() {
		return lastReceivedMsg;
	}

	public void setLastReceivedMsg(String lastReceivedMsg) {
		this.lastReceivedMsg = lastReceivedMsg;
	}

	public OnCommunicationListener getOnCommunicationListener() {
		return onCommunicationListener;
	}

	public void setOnCommunicationListener(OnCommunicationListener onCommunicationListener) {
		this.onCommunicationListener = onCommunicationListener;
	}

	public void sendCountAnd1() {
		if(sendCount >= Integer.MAX_VALUE) {
			sendCount = 0;
		}
		sendCount++;
	}
	
	public void receivedCountAnd1() {
		if(receivedCount >= Integer.MAX_VALUE) {
			receivedCount = 0;
		}
		receivedCount++;
	}
	
	public void channelReceived(String msg, User user) {
		if(null != onCommunicationListener) {
			onCommunicationListener.onReceived(this, msg);
		}
		noReponse = 0;
		if (getMessageAnalysiser() != null) {
			Device dev = messageAnalysiser.putMsg(msg, user);
			if (dev != null) {
				if(null == device) {
					DevChannelBridgeHelper.getIns().cleanBrigdes(dev.findSuperParent());
				}
				if (dev.findSuperParent() != device) {
					device = dev.findSuperParent();
					device.setNoResponse(0);
				}
			}
		}
	}

	public MessageAnalysiser getMessageAnalysiser() {
		if (null == messageAnalysiser) {
			if (null != analysiserName) {
				try {
					@SuppressWarnings("unchecked")
					Class<MessageAnalysiser> c = (Class<MessageAnalysiser>) Class.forName(analysiserName);
					messageAnalysiser = c.newInstance();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return messageAnalysiser;
	}

	public void setMessageAnalysiser(MessageAnalysiser messageAnalysiser) {
		this.messageAnalysiser = messageAnalysiser;
	}

	public Channel getChannel() {
		if (null == channelId) {
			return null;
		}

		if(null == channel) {
			for (Channel c : channelGroup) {
				if (c.id().asShortText().equals(channelId)) {
					channel = c;
					return channel;
				}
			}
		}
		return channel;
	}

	public String getHeart() {
		String heart;
		//
		if (device == null) {
			heart = OrderHelper.getOrderMsg("h2");
		} else {
			heart = device.getHeartOrder();
		}
		return heart;
	}

	public static final int OK = 0;
	public static final int NO_CHANNEL = 1;
	public static final int NO_REPONSE = 2;
	public static final int HAVE_COMMUNICATION_RECENTLY = 3;
	public static final int ONE_ORDER_COMMUNICATION_OFTEN = 4;

	public int sendHeart() {
		int result = 0;
		if (null == device) {
			result = sendOrder(getHeart(), null);
		} else {
			System.out.println("DevChannelBridge sendHeart " + device + ":" + device.getCommunicationInterval());
			if (device.getCommunicationInterval() > 7000) {
				result = sendOrder(getHeart(), device);
			} else {
				result = HAVE_COMMUNICATION_RECENTLY;
			}

			if (device instanceof Coordinator) {
				for (Device dev : ((Coordinator) device).getListDev()) {
					sendHeart(dev);
				}
			}
		}
		return result;
	}

	public Device findDevice(String devCoding) {
		if(null == devCoding || null == device) {
			return null;
		}
		if(device.getCoding().equals(devCoding)) {
			return device;
		}else if(device instanceof DevHaveChild) {
			return ((DevHaveChild)device).getDevByCoding(devCoding);
		}
		return null;
	}
	
	private void sendHeart(Device dev) {
		if (dev.getCommunicationInterval() > 7000) {
			sendOrder(dev.getHeartOrder(), dev);
		}
	}

	public int sendOrder(String order, Device dev) {
		//System.out.println("sendOrder " + order + ":" + dev);
		Channel ch = getChannel();
		if (ch == null) {
			channelId = null;
			if(null != dev) {
				dev.setDevStateId(DevStateHelper.DS_YI_CHANG);
			}
			return NO_CHANNEL;
		}
		if (null == device) {
			if (noReponse > 2) {
				ch.close();
				DevChannelBridgeHelper.getIns().removeBridge(this);
				//System.out.println("sendOrder remove channel");
				return NO_REPONSE;
			} else {
				noReponsePlus();
				sendOrder(order);
				return OK;
			}
		}

		noReponse = dev.getNoResponse();
//		System.out.println("sendOrder dev noresponse " + noReponse);
		if (noReponse > 3 && dev.getLastResponseInterval() > 20000) {
			// if device no response great than 3 and device's last response interval great than 20000ms, prevent communication fast
			//don't close the channel while this device is coordinator and the parameter device is child device of coordinator
			//only the parameter device is this device,example wifi switch, there should close the channel
			if (dev == device) {
				ch.close();
				DevChannelBridgeHelper.getIns().removeBridge(this);
			} 
			dev.setNoResponse(0);
			dev.setDevStateId(DevStateHelper.DS_YI_CHANG);
			return NO_REPONSE;
		}
//		if (dev.getLastOrder().equals(order)) {
//			if (dev.getCommunicationInterval() < 5000 && noReponse > 0) {
//				return ONE_ORDER_COMMUNICATION_OFTEN;
//			}
//		}
		//boolean cansend = false;
		//if(dev instanceof)
		if(dev.canSend()) {
			dev.setLastOrder(order);
    		dev.noResponsePlus();
    		dev.resetLastCommunicationTime();
    		sendOrder(order);
    		return OK;
		}else {
			return ONE_ORDER_COMMUNICATION_OFTEN;
		}
	}
	
	public void sendOrder(String msg) {
		if(null != getChannel()) {
			getChannel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
			if(null != onCommunicationListener) {
				onCommunicationListener.onSend(this, msg);
			}
		}
	}
	
	public interface OnCommunicationListener {
		void onSend(DevChannelBridge bridge, String msg);
		void onReceived(DevChannelBridge bridge, String msg);
	}
}
