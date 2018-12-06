package com.bairock.iot.intelDev.linkage.timing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ZTimer {

	@Id
	@Column(nullable = false)
	private String id;

	@ManyToOne
	@JsonBackReference("timimg_ztimer")
	private Timing timing;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	//@JoinColumn(name = "timer_id")
	private List<MyTime> listTimes = new ArrayList<>(2);

	@OneToOne(mappedBy = "zTimer", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("ztimer_weekhelper")
	private WeekHelper weekHelper;

	private boolean enable;
	private boolean deleted;

	/**
	 * 
	 */
	public ZTimer() {
		id = UUID.randomUUID().toString();
		weekHelper = new WeekHelper();
		weekHelper.setzTimer(this);
		MyTime t1 = new MyTime();
		t1.setType(0);
		MyTime t2 = new MyTime();
		t2.setType(1);
		listTimes.add(t1);
		listTimes.add(t2);
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Timing getTiming() {
		return timing;
	}

	public void setTiming(Timing timing) {
		this.timing = timing;
	}

	public List<MyTime> getListTimes() {
		return listTimes;
	}

	public void setListTimes(List<MyTime> listTimes) {
		this.listTimes = listTimes;
	}

	@JsonIgnore
	public MyTime getOnTime() {
		for (MyTime myTime : listTimes) {
			if (myTime.getType() == 1) {
				return myTime;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param onKeepTime
	 */
	public void setOnKeepTime(MyTime onKeepTime) {
		for (MyTime myTime : listTimes) {
			if (myTime.getType() == 1) {
				myTime = onKeepTime;
				return;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public MyTime getOffTime() {
		for (MyTime myTime : listTimes) {
			if (myTime.getType() == 0) {
				return myTime;
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public void setOffKeepTime(MyTime offKeepTime) {
		for (MyTime myTime : listTimes) {
			if (myTime.getType() == 0) {
				myTime = offKeepTime;
				return;
			}
		}
	}

	/**
	 * get week helper
	 * 
	 * @return
	 */
	public WeekHelper getWeekHelper() {
		return weekHelper;
	}

	/**
	 * set week helper
	 * 
	 * @param weekHelper
	 */
	public void setWeekHelper(WeekHelper weekHelper) {
		this.weekHelper = weekHelper;
		this.weekHelper.setzTimer(this);
	}

	/**
	 * is enable
	 * 
	 * @return
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * set enable
	 * 
	 * @param enable
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * is deleted
	 * 
	 * @return
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * set deleted
	 * 
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String onOffTimerStr() {
		MyTime onTime = getOnTime();
		MyTime offTime = getOffTime();
		return String.format("%02d:%02d:%02d - %02d:%02d:%02d\n%s", onTime.getHour(), onTime.getMinute(),
				onTime.getSecond(), offTime.getHour(), offTime.getMinute(), offTime.getSecond(),
				getWeekHelper().getWeeksName());
	}
}
