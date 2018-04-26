package com.bairock.iot.intelDev.device;

/**
 * device state
 * @author LinQiang
 *
 */
public class DevState {

	private long id;
	private String dsId;
	private String ds;
	private String info;
	
	/**
	 * 
	 */
	public DevState(){}
	
	/**
	 * 
	 * @param dsId device state identify
	 * @param ds device state
	 * @param info device state info
	 */
	public DevState(String dsId, String ds, String info){
		setDsId(dsId);
		setDs(ds);
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
	 * get device state identify
	 * @return
	 */
	public String getDsId() {
		return dsId;
	}
	
	/**
	 * set device state identify
	 * @param dsId
	 */
	public void setDsId(String dsId) {
		this.dsId = dsId;
	}
	
	/**
	 * get device state
	 * @return
	 */
	public String getDs() {
		return ds;
	}
	
	/**
	 * set device state
	 * @param ds
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}
	
	/**
	 * get device state info
	 * @return
	 */
	public String getInfo() {
		return info;
	}
	
	/**
	 * set device info
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
