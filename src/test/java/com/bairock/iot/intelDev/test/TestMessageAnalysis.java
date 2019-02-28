package com.bairock.iot.intelDev.test;

import com.bairock.iot.intelDev.communication.MessageAnalysiser;
import com.bairock.iot.intelDev.device.Device;

public class TestMessageAnalysis {

	public static void main(String[] args) {
		String str = "$IB1B1????:707#00";
		TestMessageAnalysis tm = new TestMessageAnalysis();
		TestMessageAnalysis.MyMessageAna mm = tm.new MyMessageAna();
		mm.putMsg(str, null);
	}

	public class MyMessageAna extends MessageAnalysiser{

		@Override
		public void deviceHandleAfter(Device device, String msg) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unKnowMsg(String msg) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void allMessageEnd() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void singleMessageEnd(Device device, String msg) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configDevice(Device device, String msg) {
			// TODO Auto-generated method stub
			
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
}
