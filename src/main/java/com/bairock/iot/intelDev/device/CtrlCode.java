package com.bairock.iot.intelDev.device;

/**
 * device control code
 * @author LinQiang
 *
 */
public class CtrlCode {

	private long id;
	private String dctId;
	private String dct;
	private String info;
	
	
	/**
	 * 
	 */
	public CtrlCode(){}
	
	/**
	 * 
	 * @param dctId device control code identify
	 * @param dct device control code
	 * @param info device control code info
	 */
	public CtrlCode(String dctId, String dct, String info){
		setDctId(dctId);
		setDct(dct);
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
	 * get device control code identify
	 * @return
	 */
	public String getDctId() {
		return dctId;
	}
	
	/**
	 * set device control code identify
	 * @param dctId
	 */
	public void setDctId(String dctId) {
		this.dctId = dctId;
	}
	
	/**
	 * get device control code
	 * @return
	 */
	public String getDct() {
		return dct;
	}
	
	/**
	 * set device control code
	 * @param dct
	 */
	public void setDct(String dct) {
		this.dct = dct;
	}
	
	/**
	 * get device code info
	 * @return
	 */
	public String getInfo() {
		return info;
	}
	
	/**
	 * set device code info
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
}
