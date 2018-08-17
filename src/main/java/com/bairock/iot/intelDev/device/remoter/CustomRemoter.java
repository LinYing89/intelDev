package com.bairock.iot.intelDev.device.remoter;

/**
 * 自定义遥控器
 * @author 44489
 *
 */
public class CustomRemoter extends Remoter {

	public CustomRemoter() {
		this("", "");
	}

	public CustomRemoter(String mcId, String sc) {
		super(mcId, sc);
	}

}
