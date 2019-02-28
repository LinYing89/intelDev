package com.bairock.iot.intelDev.user;

import com.bairock.iot.intelDev.device.CtrlModel;
import com.bairock.iot.intelDev.order.OrderBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	
	/**
	 * 是否是用户信息登录, 否则为本地登录
	 */
	public static boolean USER_ADMIN;

	public static String getCtrlModelName(CtrlModel ctrlModel) {
		if(ctrlModel == CtrlModel.REMOTE) {
			return "远程";
		}else {
			return "本地";
		}
	}
	
	public static String orderBaseToString(OrderBase ob) {
		ObjectMapper om = new ObjectMapper();
		String order = "";
		try {
			order = om.writeValueAsString(ob);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}
}