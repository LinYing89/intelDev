package com.bairock.iot.intelDev.linkage.timing;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MyTime {

	@Id
	private String id;
	
	private int type;
	
	private int hour;
	private int minute;
	private int second;

	public MyTime() {
		id = UUID.randomUUID().toString();
	}

	public MyTime(String strTime) {
		this();
		setTimeStr(strTime);
	}

	public MyTime(int hour, int minute, int second) {
		this();
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 0 is off, 1 is on
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * 0 is off, 1 is on
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	@JsonIgnore
	public static long getMs(String time) {
		return getSec(time) * 1000;
	}

	@JsonIgnore
	public boolean setTimeStr(String time) {
		if (null != time) {
			String[] str = time.split(":");
			hour = Integer.parseInt(str[0]);
			minute = Integer.parseInt(str[1]);
			second = Integer.parseInt(str[2]);
			return true;
		}
		return false;
	}

	@JsonIgnore
	public static long getSec(String time) {
		String[] str = time.split(":");
		int hour = Integer.parseInt(str[0]);
		int minute = Integer.parseInt(str[1]);
		int second = Integer.parseInt(str[2]);
		return getSec(hour, minute, second);
	}

	@JsonIgnore
	public long getMS() {
		return getMS(hour, minute, second);
	}

	/**
	 * get millisecond
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	@JsonIgnore
	public static long getMS(int hour, int minute, int second) {
		return getSec(hour, minute, second) * 1000;
	}

	@JsonIgnore
	public long getSec() {
		return getSec(hour, minute, second);
	}

	/**
	 * get second
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	@JsonIgnore
	public static long getSec(int hour, int minute, int second) {
		return (((hour * 60) + minute) * 60 + second);
	}

	@Override
	@JsonIgnore
	public String toString() {
		return hour + ":" + minute + ":" + second;
	}
}
