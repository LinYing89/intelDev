package com.bairock.iot.intelDev.linkage;

import java.util.concurrent.TimeUnit;

import com.bairock.iot.intelDev.linkage.loop.LoopHolder;
import com.bairock.iot.intelDev.linkage.timing.TimingHolder;
import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * 
 * @author LinQiang
 *
 */
public class LinkageHelper {
	
	private static LinkageHelper ins;
	private ChainHolder chain;
	private TimingHolder timing;
	private LoopHolder loop;
	
	private CheckLinkageThread checkLinkageThread;
	
	/**
	 * 
	 */
	private LinkageHelper(){
		chain = new ChainHolder();
		timing = new TimingHolder();
		loop = new LoopHolder();
	}
	
	/**
	 * 
	 * @return
	 */
	public static LinkageHelper getIns(){
		if(null == ins){
			ins = new LinkageHelper();
		}
		return ins;
	}
	
	/**
	 * 
	 * @return
	 */
	public ChainHolder getChain() {
		return chain;
	}

	/**
	 * 
	 * @param chain
	 */
	public void setChain(ChainHolder chain) {
		this.chain = chain;
	}

	/**
	 * 
	 * @return
	 */
	public TimingHolder getTiming() {
		return timing;
	}

	/**
	 * 
	 * @param timing
	 */
	public void setTiming(TimingHolder timing) {
		this.timing = timing;
	}

	/**
	 * 
	 * @return
	 */
	public LoopHolder getLoop() {
		return loop;
	}

	/**
	 * 
	 * @param loop
	 */
	public void setLoop(LoopHolder loop) {
		this.loop = loop;
	}

	/**
	 * 
	 */
	public void startCheckLinkageThread(){
		if(null != checkLinkageThread && checkLinkageThread.isRunning){
			return;
		}
		checkLinkageThread = new CheckLinkageThread();
		checkLinkageThread.isRunning = true;
		IntelDevHelper.executeThread(checkLinkageThread);;
	}
	
	/**
	 * 
	 */
	public void stopCheckLinkageThread(){
		if(null != checkLinkageThread){
			checkLinkageThread.isRunning = false;
			checkLinkageThread.interrupt();
			checkLinkageThread = null;
		}
	}
	
	/**
	 * 
	 * @author LinQiang
	 *
	 */
	class CheckLinkageThread extends Thread{

		boolean isRunning = false;
		@Override
		public void run() {
			isRunning = true;
			while (!Thread.interrupted()) {
				try {
					
					loop.run();
					
					//on the first, set chain and timing temporary mark to -1
					for (LinkageTabRow deviceChain : LinkageTab.getIns().getListLinkageTabRow()) {
						deviceChain.initIChainTem();
					}

					chain.run();
					
					timing.run();

					//on the end, set temporary value to the ensure value
					for (LinkageTabRow tabRow : 
						LinkageTab.getIns().getListLinkageTabRow()) {
						//if temporary value is -1, not change the ensure value
						if (tabRow.getiChainTem() != -1) {
							tabRow.setChain(tabRow.getiChainTem());
						}
						tabRow.setTiming(tabRow.getiTimingTem());
						//System.out.println("LinkageHelper run item: " + tabRow.getITemString());
					}

					LinkageTab.getIns().checkTabRows();
					
//					for (LinkageTabRow tabRow : 
//						LinkageTab.getIns().getListLinkageTabRow()) {
//						System.out.println("LinkageHelper run: " + tabRow.getITemString());
//					}
					
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			isRunning = false;
		}
		
	}
}
