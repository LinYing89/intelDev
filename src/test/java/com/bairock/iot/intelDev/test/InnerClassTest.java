package com.bairock.iot.intelDev.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;

import com.bairock.iot.intelDev.linkage.timing.ZTimer;

@Ignore
public class InnerClassTest {
	public static void main(String[] args) {
		String t = "22:02:45";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			Date d = sdf.parse(t);
			System.out.println(d);
			ZTimer zt = new ZTimer();
			zt.getOnTime().setTimeStr(t);
			System.out.println(zt.getOnTime());
			zt.getOffTime().setTimeStr(t);
			System.out.println(zt.getOffTime());
//			Calendar c = Calendar.getInstance();
//			c.setTime(d);
//			System.out.println(c);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
