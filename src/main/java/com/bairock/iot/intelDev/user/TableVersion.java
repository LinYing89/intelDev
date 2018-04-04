package com.bairock.iot.intelDev.user;

/**
 * table version
 * @author LinQiang
 *
 */
public class TableVersion {

	private long id;
	private String tabName;
	private int struV;
	private int msgV;
	
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
	 * get table name
	 * @return
	 */
	public String getTabName() {
		return tabName;
	}
	
	/**
	 * set table name
	 * @param tabName
	 */
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	
	/**
	 * get table structure version
	 * @return
	 */
	public int getStruV() {
		return struV;
	}
	
	/**
	 * set structure version
	 * @param struV
	 */
	public void setStruV(int struV) {
		this.struV = struV;
	}
	
	/**
	 * get message version
	 * @return
	 */
	public int getMsgV() {
		return msgV;
	}
	
	/**
	 * set message version
	 * @param msgV
	 */
	public void setMsgV(int msgV) {
		this.msgV = msgV;
	}
	
	
}
