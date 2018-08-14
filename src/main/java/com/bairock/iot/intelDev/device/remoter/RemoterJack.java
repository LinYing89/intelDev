package com.bairock.iot.intelDev.device.remoter;

/**
 * 遥控插座
 * @author 44489
 *
 */
public class RemoterJack extends Remoter {

	public RemoterJack() {
		this("", "");
	}

	public RemoterJack(String mcId, String sc) {
		super(mcId, sc);
		initRemoteKeys();
	}
	
	/**
	 * 初始化按键集合
	 */
	public void initRemoteKeys() {
		RemoterKey rk = new RemoterKey("1", "开");
		RemoterKey rk2 = new RemoterKey("2", "关");
		
		addRemoterKey(rk);
		addRemoterKey(rk2);
	}

}
