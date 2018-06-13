package com.bairock.iot.intelDev.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * application version
 * @author LinQiang
 *
 */
@Entity
public class AppVersion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String appV;
	private String appName;
	private String appInfo;
	private int appVc;
	private boolean debugVersion;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseTime;
	
	/**
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * get application version name
	 * @return
	 */
	public String getAppV() {
		return appV;
	}
	
	/**
	 * set application version name
	 * @param appV
	 */
	public void setAppV(String appV) {
		this.appV = appV;
	}
	
	/**
	 * get application name
	 * @return
	 */
	public String getAppName() {
		return appName;
	}
	
	/**
	 * set application name
	 * @param appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	/**
	 * get application info
	 * @return
	 */
	public String getAppInfo() {
		return appInfo;
	}
	
	/**
	 * set application info
	 * @param appInfo
	 */
	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}
	
	/**
	 * get application version code
	 * @return
	 */
	public int getAppVc() {
		return appVc;
	}
	
	/**
	 * set application version code
	 * @param appVc
	 */
	public void setAppVc(int appVc) {
		this.appVc = appVc;
	}

	public boolean isDebugVersion() {
		return debugVersion;
	}

	public void setDebugVersion(boolean debugVersion) {
		this.debugVersion = debugVersion;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
}
