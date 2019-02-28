package com.bairock.iot.intelDev.test;

import com.bairock.iot.intelDev.communication.MessageAnalysiser;
import com.bairock.iot.intelDev.device.Device;

public class MyMessageA extends MessageAnalysiser {

	@Override
	public void deviceHandleAfter(Device device, String msg) {
//		System.out.println("deviceFeedback: " + msg);
	}

	@Override
	public void unKnowMsg(String msg) {
//		System.out.println("noTheDev: " + msg);
	}

	@Override
	public void allMessageEnd() {
//		System.out.println("allMessageEnd: ");
	}

	@Override
	public void singleMessageEnd(Device device, String msg) {
//		System.out.println("singleMessageEnd: ");
	}

	@Override
	public void configDevice(Device device, String msg) {
		System.out.println("MyMessageA configDevice: " + msg);
		
	}

	@Override
	public void unKnowDev(Device device, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deviceHandleBefore(Device device, String msg) {
		// TODO Auto-generated method stub
		
	}

}
