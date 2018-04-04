package com.bairock.iot.intelDev.test;

import java.util.List;

import org.junit.Ignore;

import com.bairock.iot.intelDev.communication.DevChannelBridge;
import com.bairock.iot.intelDev.communication.DevChannelBridgeHelper;
import com.bairock.iot.intelDev.communication.DevServer;
import com.bairock.iot.intelDev.communication.MessageAnalysiser;
import com.bairock.iot.intelDev.communication.SearchDeviceHelper;
import com.bairock.iot.intelDev.communication.SearchDeviceHelper.OnSearchListener;
import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.Device.OnStateChangedListener;
import com.bairock.iot.intelDev.user.DevGroup;
import com.bairock.iot.intelDev.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Ignore
public class SearchDeviceTest {

	public static void main(String[] args) {
		
		User user = new User();
		DevGroup dg = new DevGroup();
		user.addGroup(dg);
		DevChannelBridgeHelper.getIns().setUser(user);
		
		try {
			new DevServer().run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DevChannelBridge.analysiserName = MyMessageA.class.getName();
		
		SearchDeviceHelper.getIns().setOnSearchListener(new OnSearchListener() {

			@Override
			public void searchedMsg(String msg) {
				System.out.println("searchedMsg: " + msg);
			}

			@Override
			public void searchStart() {
				System.out.println("searchStart: ");
			}

			@Override
			public void searchFail() {
				System.out.println("searchFail: ");
				SearchDeviceHelper.getIns().stopSearchDevThread();
			}

			@Override
			public void searchedAllDevices(List<Device> listDev) {
				System.out.println("searchOk: ");
				if (null != listDev) {
					for (Device dev : listDev) {
						ObjectMapper mm = new ObjectMapper();
						try {
							String str = mm.writeValueAsString(dev);
							System.out.println("dev: " + str);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					for(Device dev : listDev){
						setListener(dev);
					}
					
					DevChannelBridgeHelper.getIns().startSeekDeviceOnLineThread();
				}
				user.getListDevGroup().get(0).setListDevice(listDev);
				//IntelDevHelper.shutDown();
				return;
			}

			@Override
			public void searchedNewDevices(List<Device> listDev) {
				// TODO Auto-generated method stub
				
			}
		});

		SearchDeviceHelper.getIns().startSearchDevThread(null);

	}
	
	public static void setListener(Device dev){
		dev.setOnStateChanged(lis);
		if(dev instanceof DevHaveChild){
			for(Device dd : ((DevHaveChild)dev).getListDev()){
				setListener(dd);
			}
		}
	}
	
	public static OnStateChangedListener lis = new OnStateChangedListener(){

		@Override
		public void onStateChanged(Device dev, String stateId) {
			System.out.println("onStateChanged" + dev.getCoding() + stateId);
			
		}

		@Override
		public void onNormalToAbnormal(Device dev) {
			System.out.println("onNormalToAbnormal" + dev.getCoding());
		}

		@Override
		public void onAbnormalToNormal(Device dev) {
			System.out.println("onAbnormalToNormal" + dev.getCoding());
		}

		@Override
		public void onNoResponse(Device dev) {
			System.out.println("onNoResponse" + dev.getCoding());
		}
		
	};

	/**
	 * 
	 * @author LinQiang
	 *
	 */
	public class MyMessageAna extends MessageAnalysiser {

		@Override
		public void allMessageEnd() {
			System.out.println("allMessageEnd: ");
		}

		@Override
		public void singleMessageEnd(Device device, String msg) {
			System.out.println("singleMessageEnd: " + msg);
		}

		@Override
		public void deviceFeedback(Device device, String msg) {
			System.out.println("deviceFeedback: " + msg);
		}

		@Override
		public void noTheDev(String msg) {
			System.out.println("noTheDev: ");
		}

		@Override
		public void configDevice(Device device, String msg) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configDeviceCtrlModel(Device device, String msg) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unKnowDev(Device device, String msg) {
			// TODO Auto-generated method stub
			
		}

	}

}
