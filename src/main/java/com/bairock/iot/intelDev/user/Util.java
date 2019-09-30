package com.bairock.iot.intelDev.user;

import java.math.BigInteger;
import java.text.DecimalFormat;

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
	
	/**
	 * 固定保留两位小数, 不足两位的后面补0
	 * @param value
	 * @return
	 */
	public static String format2TwoScale(double value) {
	    return formatValue("0.00", value);
	}
	
	public static String formatValue(String format, double value) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }
	
//	public static void main(String[] args) {
////        float v = 0.123f;
////        System.out.println(format2TwoScale(v));
//	    
//	    int var = -1;
//	    String hex = Integer.toHexString(var);
//	    System.out.println(hex);
//	    hex = "-1";
//	    BigInteger bi = new BigInteger(hex, 16);
//	    System.out.println(Integer.parseInt("-a", 16));
//    }
}