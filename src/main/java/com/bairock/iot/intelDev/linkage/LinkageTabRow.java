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
	private int chainTem = -1;
	private int timingTem = -1;
	private int chain = -1;
	private int timing = -1;
	private int loop = -1;
	private int result = -1;
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

	public int getChainTem() {
		return chainTem;
	}

	public void setChainTem(int chainTem) {
		this.chainTem = chainTem;
	}

	public int getTimingTem() {
		return timingTem;
	}

	public void setTimingTem(int timingTem) {
		this.timingTem = timingTem;
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
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
		chainTem = -1;
		timingTem = -1;
		result = -1;
	}
	
	public void initIChainTem(){
		chainTem = -1;
		timingTem = -1;
	}

	public void analysisLinkageResult() {
		if(getChain() == -1 && getTiming() == -1 && getLoop() == -1){
			result = -1;
			return;
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
		result = iS;
	}
	/**
	 * 
	 * @return
	 */
	public String createOrder(){
		if(result == -1) {
			return null;
		}
		String state;
		// it always send on first, then judge the state, if this state is equal device state, don't send
		String dctId = "";
		if(result == 1){
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
	
	public boolean haveLinkage() {
		return chain != -1 || timing != -1 || loop != -1;
	}

	@Override
	public String toString() {
		return getDevice().getCoding() + getDevice().getName() + ": " +getChain() + "," + getTiming() + "," + getLoop();
	}
	
	public String getITemString(){
		return getDevice().getCoding() + getDevice().getName() + ": " +chainTem + "," + timingTem + "," + getLoop();
	}
}
