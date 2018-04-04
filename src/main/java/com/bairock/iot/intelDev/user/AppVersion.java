package com.bairock.iot.intelDev.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private String appPath;
	private String appInfo;
	private int appVc;
	
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
	 * get application path
	 * @return
	 */
	public String getAppPath() {
		return appPath;
	}
	
	/**
	 * set application path
	 * @param appPath
	 */
	public void setAppPath(String appPath) {
		this.appPath = appPath;
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
	
}
