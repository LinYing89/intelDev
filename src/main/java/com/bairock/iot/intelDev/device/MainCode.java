package com.bairock.iot.intelDev.device;

/**
 * main code
 * @author LinQiang
 *
 */
public class MainCode {
	private long id;
	private String mcId;
	private String mc;
	private String info;
	
	/**
	 * 
	 */
	public MainCode(){}
	
	/**
	 * 
	 * @param mcId main code identify
	 * @param mc main code
	 * @param info main code info
	 */
	public MainCode(String mcId, String mc, String info){
		setMcId(mcId);
		setMc(mc);
		setInfo(info);
	}
	
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
	 * get main code identify
	 * @return
	 */
	public String getMcId() {
		return mcId;
	}
	
	/**
	 * set main code identify
	 * @param mcId
	 */
	public void setMcId(String mcId) {
		this.mcId = mcId;
	}
	
	/**
	 * get main code
	 * @return
	 */
	public String getMc() {
		return mc;
	}
	
	/**
	 * set main code
	 * @param mc
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}
	
	/**
	 * get main code info
	 * @return
	 */
	public String getInfo() {
		return info;
	}
	
	/**
	 * set main code info
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}
}
