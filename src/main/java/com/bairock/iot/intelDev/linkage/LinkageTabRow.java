package com.bairock.iot.intelDev.linkage;

import com.bairock.iot.intelDev.device.CtrlCodeHelper;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.OrderHelper;

/**
 * 
 * @author LinQiang
 *
 */
public class LinkageTabRow {

	private Device device;
	//chain temporary mark, if this mark is -1, this chain will not participation operation
	private int iChainTem = -1;
	private int iTimingTem = -1;
	private int chain = -1;
	private int timing = -1;
	private int loop = -1;
	private boolean first = true;
	
	/**
	 * 
	 */
	public LinkageTabRow() {
		init();
		first = true;
	}
	
	/**
	 * get device
	 * @return
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * set device
	 * @param device
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * 
	 * @return
	 */
	public int getiChainTem() {
		return iChainTem;
	}

	/**
	 * 
	 * @param iChainTem
	 */
	public void setiChainTem(int iChainTem) {
		this.iChainTem = iChainTem;
	}

	/**
	 * 
	 * @return
	 */
	public int getiTimingTem() {
		return iTimingTem;
	}

	/**
	 * 
	 * @param iTimingTem
	 */
	public void setiTimingTem(int iTimingTem) {
		this.iTimingTem = iTimingTem;
	}

	/**
	 * 
	 * @return
	 */
	public int getChain() {
		return chain;
	}

	/**
	 * 
	 * @param chain
	 */
	public void setChain(int chain) {
		this.chain = chain;
	}

	/**
	 * 
	 * @return
	 */
	public int getTiming() {
		return timing;
	}

	/**
	 * 
	 * @param timing
	 */
	public void setTiming(int timing) {
		this.timing = timing;
	}

	/**
	 * 
	 * @return
	 */
	public int getLoop() {
		return loop;
	}

	/**
	 * 
	 * @param loop
	 */
	public void setLoop(int loop) {
		this.loop = loop;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * 
	 * @param first
	 */
	public void setFirst(boolean first) {
		this.first = first;
	}

	public void init(){
		chain = -1;
		timing = -1;
		loop = -1;
		iChainTem = -1;
		iTimingTem = -1;
	}
	
	public void initIChainTem(){
		iChainTem = -1;
		iTimingTem = -1;
	}

	/**
	 * 
	 * @return
	 */
	public String createOrder(){
		//Log.w("DeviceChain","sendAble");
		String state;
		if(getChain() == -1 && getTiming() == -1 && getLoop() == -1){
			return null;
		}
		//target value
		int iS = 1;
		if(getChain() != -1){
			iS = chain;
		}
		if(getTiming() != -1){
			iS *= timing;
		}
		if(getLoop() != -1){
			iS *= loop;
		}
		/*String log = getDevice().getName() + ": " +getChain() + "," + getTiming() + "," + getLoop();
		WebClient.getInstance().sendMessage(log);*/
		//Log.e("DeviceChain",log);

		// it always send on first, then judge the state, if this state is equal device state, don't send
		String dctId = "";
		if(iS == 1){
			state = DevStateHelper.DS_KAI;
			dctId = CtrlCodeHelper.DCT_KAIGUAN_KAI;
		}else{
			state = DevStateHelper.DS_GUAN;
			dctId = CtrlCodeHelper.DCT_KAIGUAN_GUAN;
		}
		
		if(first){
			first = false;
			return device.getDevOrder(OrderHelper.CTRL_HEAD, dctId);
		}

		if(device.getDevStateId().equals(state)){
			return null;
		}else{
			return device.getDevOrder(OrderHelper.CTRL_HEAD, dctId);
		}
	}

	@Override
	public String toString() {
		return getDevice().getCoding() + getDevice().getName() + ": " +getChain() + "," + getTiming() + "," + getLoop();
	}
	
	public String getITemString(){
		return getDevice().getCoding() + getDevice().getName() + ": " +iChainTem + "," + iTimingTem + "," + getLoop();
	}
}
