package com.bairock.iot.intelDev.device;

import java.io.UnsupportedEncodingException;

/**
 * order helper
 * @author LinQiang
 *
 */
public class OrderHelper {

	public static final String PREFIX = "$";
	public static final String SUFFIX = "#";
	
	public static final String SEPARATOR = ":";
	/**
	 * ask net for the device, one char '?'
	 */
	public static final String ASK_ORDER = "?";
	/**
	 * the head of string which query the attribute of device, 'Q'.
	 */
	public static final String QUERY_HEAD = "Q";
	/**
	 * the head of string which control device, 'C'.
	 */
	public static final String CTRL_HEAD = "C";
	/**
	 * the head of string which the device feedback, 'I'.
	 */
	public static final String FEEDBACK_HEAD = "I";
	/**
	 * the head of string which set the attribute of device, 'S'.
	 */
	public static final String SET_HEAD = "S";
	/**
	 * the head of string which is heart 1, 'H'.
	 */
	public static final String HEART = "H";
	
	/**
	 * get the verify of the string 
	 * @param data
	 * @return
	 */
	public static String getVerify(String data) {
		if(data == null){
			return null;
		}
		int chksum = 0;
		String hex = null;
		try {
			byte[] tmpString;
			tmpString = data.getBytes("GBK");
			for (int i = 0; i < tmpString.length; i++) {
				chksum += tmpString[i];
			}
			chksum = (chksum & 0xFF);
			hex = Integer.toHexString(chksum);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(hex == null){
			hex = "00";
		}else{
			if (hex.length() < 2) {
				hex = "0" + hex;
			}
		}
		return hex;
	}
	
	/**
	 * add prefix, suffix and verify to the order 
	 * @param msg
	 * @return
	 */
	public static String getOrderMsg(String msg){
		return PREFIX + msg + SUFFIX + getVerify(msg);
	}
}
