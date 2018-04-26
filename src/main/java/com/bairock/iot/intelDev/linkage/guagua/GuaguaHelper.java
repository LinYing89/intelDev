package com.bairock.iot.intelDev.linkage.guagua;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.CtrlModel;
import com.bairock.iot.intelDev.device.GuaguaMouth;
import com.bairock.iot.intelDev.user.IntelDevHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author LinQiang
 *
 */
public class GuaguaHelper {

	private static GuaguaHelper ins;
	private GuaguaHolder guaguaHolder;
	private CheckGuaguaThread checkGuaguaThread;
	
	@Transient
	@JsonIgnore
	private OnOrderSendListener onOrderSendListener;

	/**
	 * 
	 */
	private GuaguaHelper(){
		guaguaHolder = new GuaguaHolder();
	}
	
	/**
	 * 
	 * @return
	 */
	public static GuaguaHelper getIns(){
		if(null == ins){
			ins = new GuaguaHelper();
		}
		return ins;
	}
	
	/**
	 * 
	 * @return
	 */
	public GuaguaHolder getGuaguaHolder() {
		return guaguaHolder;
	}

	/**
	 * 
	 * @param guaguaHolder
	 */
	public void setGuaguaHolder(GuaguaHolder guaguaHolder) {
		this.guaguaHolder = guaguaHolder;
	}

	/**
	 * 
	 */
	public void startCheckGuaguaThread(){
		if(null != checkGuaguaThread && checkGuaguaThread.isRunning){
			return;
		}
		checkGuaguaThread = new CheckGuaguaThread();
		checkGuaguaThread.isRunning = true;
		IntelDevHelper.executeThread(checkGuaguaThread);
	}
	
	/**
	 * 
	 */
	public void stopCheckGuaguaThread(){
		if(null != checkGuaguaThread){
			checkGuaguaThread.isRunning = false;
			checkGuaguaThread.interrupt();
			checkGuaguaThread = null;
		}
	}
	
	
	
	public OnOrderSendListener getOnOrderSendListener() {
		return onOrderSendListener;
	}

	public void setOnOrderSendListener(OnOrderSendListener onOrderSendListener) {
		this.onOrderSendListener = onOrderSendListener;
	}

	protected void stateChangedListener(GuaguaMouth guaguaMouth, String order, CtrlModel ctrlModel){
		if(null != guaguaHolder && null != order && null != onOrderSendListener){
			onOrderSendListener.onChanged(guaguaMouth, order, ctrlModel);
		}
	}
	
	/**
	 * interface definition for a callback to be invoked when an order need send
	 * @author LinQiang
	 *
	 */
	public interface OnOrderSendListener{
		/**
		 * guaguaMouth device order send listener
		 * @param guaguaMouth guagua device
		 * @param order order message
		 * @param ctrlModel
		 */
		void onChanged(GuaguaMouth guaguaMouth, String order, CtrlModel ctrlModel);
	}
	
	/**
	 * 
	 * @author LinQiang
	 *
	 */
	class CheckGuaguaThread extends Thread{

		boolean isRunning = false;
		
		@Override
		public void run() {
			isRunning = true;
			try {
				while (!Thread.interrupted()) {
					guaguaHolder.run();
					TimeUnit.MILLISECONDS.sleep(200);
				}
			} catch (Exception ex) {
				StringWriter sw = new StringWriter();
				ex.printStackTrace(new PrintWriter(sw));
			}
			isRunning = false;
		}
		
	}
}
