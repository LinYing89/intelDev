package com.bairock.iot.intelDev.device.remoter;

/**
 * 窗帘
 * @author 44489
 *
 */
public class Curtain extends Remoter {

	public Curtain() {
		this("", "");
	}

	public Curtain(String mcId, String sc) {
		super(mcId, sc);
		initRemoteKeys();
	}
	
	/**
	 * 初始化按键集合
	 */
	public void initRemoteKeys() {
		RemoterKey rk = new RemoterKey("1", "开");
		RemoterKey rk2 = new RemoterKey("2", "停");
		RemoterKey rk3 = new RemoterKey("3", "关");
		
		addRemoterKey(rk);
		addRemoterKey(rk2);
		addRemoterKey(rk3);
	}

}
