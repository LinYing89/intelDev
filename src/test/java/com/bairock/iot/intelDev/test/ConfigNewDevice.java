package com.bairock.iot.intelDev.test;

import com.bairock.iot.intelDev.communication.DevChannelBridge;
import com.bairock.iot.intelDev.communication.DevServer;

public class ConfigNewDevice {

	public static void main(String[] args) {
		try {
			new DevServer().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DevChannelBridge.analysiserName = MyMessageA.class.getName();
	}

}
