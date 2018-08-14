package com.bairock.iot.intelDev.device.remoter;

/**
 * 遥控按键接口
 * @author 44489
 *
 */
public interface IRemoterKey {

	/**
	 * 创建按键学习命令
	 * @return 学习命令报文
	 */
	String createStudyKeyOrder();
	
	/**
	 * 创建按键测试命令
	 * @return 测试命令报文
	 */
	String createTestKeyOrder();
	
	/**
	 * 创建按键保存命令
	 * @return 保存命令报文
	 */
	String createSaveKeyOrder();
	
	/**
	 * 创建按键控制命令
	 * @return 控制命令报文
	 */
	String createCtrlKeyOrder();
}
