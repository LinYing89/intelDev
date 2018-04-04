package com.bairock.iot.intelDev.user;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bairock.iot.intelDev.device.Device;

public class IntelDevHelper {

	/**
	 * the device which is be operated
	 */
	public static Device OPERATE_DEVICE;
	
	private static final ExecutorService exec = Executors.newCachedThreadPool();
	
	public static void executeThread(Runnable thread){
		exec.execute(thread);
	}
	
	public static void shutDown(){
		exec.shutdownNow();
	}
	
	public static String getLocalIp() {
		Enumeration<NetworkInterface> interfs;
		try {
			interfs = NetworkInterface.getNetworkInterfaces();
			while (interfs.hasMoreElements()) {
				NetworkInterface interf = interfs.nextElement();
				Enumeration<InetAddress> addres = interf.getInetAddresses();
				while (addres.hasMoreElements()) {
					InetAddress in = addres.nextElement();
					if (in instanceof Inet4Address) {
						//System.out.println(in.getHostName());
						//System.out.println(in.getHostAddress());
						String addr = in.getHostAddress();
						if(!addr.equals("127.0.0.1")) {
							return addr;
						}
						//return in.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * accurate to the second decimal place
	 * @param f
	 * @return
	 */
	public static float scale(float f) {
		BigDecimal b = new BigDecimal(f);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
//	public static void main(String[] args) {
//		System.out.println(getLocalIp());
//	}
}
