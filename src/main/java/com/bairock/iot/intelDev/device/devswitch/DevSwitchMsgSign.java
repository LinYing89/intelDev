package com.bairock.iot.intelDev.device.devswitch;

public class DevSwitchMsgSign {
	
	public final static String ORDER_KAI = "3";
	public final static String ORDER_OFF = "4";
	public final static String ORDER_TING = "5";
	public final static String ON_KEEP_TIME = "6";
	
	/**
	 * feedback because of order control message
	 */
	public final static String ORDER_CTRL_FEEDBACK = "7";
	
	/**
	 * feedback because order query state message
	 */
	public final static String ORDER_QUERY_FEEDBACK = "8";
	
	/**
	 * message that pushed by device self
	 */
	public final static String MSG_DEV_PUSHED = "9";
}
