package com.bairock.iot.intelDev.device;


/**
 * alarm style
 * @author LinQiang
 *
 */
public class AlarmStyle {

	private long id;
	private String as;
	private String asInfo;
	
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
	 * get alarm style
	 * @return
	 */
	public String getAs() {
		return as;
	}
	
	/**
	 * set alarm style
	 * @param as
	 */
	public void setAs(String as) {
		this.as = as;
	}
	
	/**
	 * get alarm style info
	 * @return
	 */
	public String getAsInfo() {
		return asInfo;
	}
	
	/**
	 * set alarm style info
	 * @param asInfo
	 */
	public void setAsInfo(String asInfo) {
		this.asInfo = asInfo;
	}
	
	
}
